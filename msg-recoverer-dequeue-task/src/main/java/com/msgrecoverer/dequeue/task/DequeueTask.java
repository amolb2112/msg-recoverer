package com.msgrecoverer.dequeue.task;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.util.StringUtils;
import com.msgrecoverer.common.client.S3Wrapper;
import com.msgrecoverer.common.model.MessageObject;
import com.msgrecoverer.common.utils.MsgRecovererUtils;
import com.msgrecoverer.dequeue.common.DequeueResponseCodes;
import com.msgrecoverer.dequeue.model.config.DequeueConfiguration;
import com.msgrecoverer.dequeue.model.request.DequeueTaskRequest;
import com.msgrecoverer.dequeue.model.request.MessageSelectionParams;
import com.msgrecoverer.dequeue.model.response.DequeueTaskResponse;
import com.msgrecoverer.dequeue.model.response.MessageContext;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Task to Dequene messages from RabbitMQ DLQ and upload them to a private S3 bucket
 *
 * @author amolb2112
 */
public class DequeueTask {
    private LambdaLogger LOGGER;
    private ConnectionFactory factory;
    private S3Wrapper s3Wrapper;
    private DequeueTaskRequest dequeueTaskRequest;
    private DequeueConfiguration config;

    String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
    AtomicInteger counter = new AtomicInteger(0);

    public DequeueTask(DequeueConfiguration config, LambdaLogger LOGGER, ConnectionFactory factory, S3Wrapper s3Wrapper, DequeueTaskRequest dequeueTaskRequest) {
        this.config = config;
        this.LOGGER = LOGGER;
        this.factory = factory;
        this.s3Wrapper = s3Wrapper;
        this.dequeueTaskRequest = dequeueTaskRequest;
    }

    public DequeueTaskResponse handleMessages() {
        boolean currentMesgConsumed;
        int successCount = 0;

        String messageIdentifier = null;
        String messageContent = null;

        GetResponse getResponse;
        DequeueTaskResponse dequeueTaskResponse = new DequeueTaskResponse();
        MessageObject messageObject;
        LOGGER.log("Started DequeueProcess. Connecting to RabbitMQ Broker ...");

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(dequeueTaskRequest.getDlQueueName(), true, false, false, null);
            int queueMessageCount = declareOk.getMessageCount();
            AMQP.BasicProperties props;

            LOGGER.log("Broker connections successful. Total messages available in queue -" + queueMessageCount);

            // process messages in queue
            for (int i = 0; i < queueMessageCount; i++) {
                // reinitialize state
                currentMesgConsumed = false;

                getResponse = channel.basicGet(dequeueTaskRequest.getDlQueueName(), false);
                channel.basicAck(getResponse.getEnvelope().getDeliveryTag(), false); // acknowledge the message now, will republish if it fails criteria

                //TODO - handle exception while processing message, make sure publish it back

                props = getResponse.getProps();
                try {
                    if (getResponse != null) {
                        messageContent = new String(getResponse.getBody());
                        messageIdentifier = JsonPath.read(messageContent, dequeueTaskRequest.getMessageIdentifierPath());

                        LOGGER.log("Received message from Queue. Message Identifier - "+ messageIdentifier);

                        // match the message content against the multiple criteria
                        if (isMessageSelectionCriteriaMatching(messageContent, dequeueTaskRequest.getMessageSelectionParams())) {
                            // all criteria matched, persist to s3
                            messageObject = new MessageObject();
                            messageObject.setMessageIdentifier(messageIdentifier);
                            messageObject.setMessageContent(messageContent);
                            messageObject.setProperties(getResponse.getProps());

                            LOGGER.log("Uploading S3Object - "+ messageObject.toString());

                            URL s3resourcePath = uploadToS3(messageObject,messageIdentifier);

                            // mark successful consumption
                            currentMesgConsumed = true;
                            successCount++;
                            LOGGER.log("Message matched, wrote to amazon S3 resource, path = " + s3resourcePath.toString());
                        } else {
                            LOGGER.log("Message field values not matched, not extracting message.");
                            dequeueTaskResponse.getMessageContexts().add(
                                    new MessageContext(messageIdentifier, DequeueResponseCodes.SELECTION_PARAM_VALUE_NOT_MATCHED));
                        }
                    }
                } catch (PathNotFoundException pnfe) {
                    LOGGER.log("Message field value / identifier value not present, not extracting message.");
                    dequeueTaskResponse.getMessageContexts().add(
                            new MessageContext(messageIdentifier, DequeueResponseCodes.SELECTION_PARAM_PATH_NOT_MATCHED));
                } catch (Exception e) {
                    LOGGER.log("Exception occurred while processing message with identifier - " +messageIdentifier+". Trace - "+ MsgRecovererUtils.getStackTrace(e));
                    dequeueTaskResponse.getMessageContexts().add(
                            new MessageContext(messageIdentifier, DequeueResponseCodes.UNKNOWN_ERROR));
                }

                // republish message if not consumed
                if (!currentMesgConsumed) {
                    channel.basicPublish("", dequeueTaskRequest.getDlQueueName(),props,messageContent.getBytes());
                }
            }

            dequeueTaskResponse.setTotalCount(queueMessageCount);
            dequeueTaskResponse.setSuccessCount(successCount);
            dequeueTaskResponse.setFailureCount(queueMessageCount-successCount);

            channel.close();
            connection.close();
        } catch (Exception e) {
            LOGGER.log("Caught Exception while executing handler - " + MsgRecovererUtils.getStackTrace(e));
        }

        return dequeueTaskResponse;
    }

    private URL uploadToS3(MessageObject messageContent, String messageIdentifier) {
        Gson gson = new Gson();
        StringBuilder resourcePath
                = new StringBuilder(dequeueTaskRequest.getBucketName()).append("/original").append("/message-").append(counter.incrementAndGet()).append("-");
        if(!StringUtils.isNullOrEmpty(messageIdentifier)) {
            resourcePath.append(messageIdentifier).append("-");
        }
        resourcePath.append(currentDate).toString();

        return s3Wrapper.uploadFileToS3(config.getBaseBucketName(),resourcePath.toString(), gson.toJson(messageContent));
    }

    private boolean isMessageSelectionCriteriaMatching(
            String messageContent,
            List<MessageSelectionParams> messageSelectionParams) {

        String messageFieldValue;
        boolean result = true;

        for (MessageSelectionParams messagesSelectionParam : messageSelectionParams) {
            messageFieldValue = JsonPath.read(messageContent, messagesSelectionParam.getMatcherFieldPath());
            if (!messageFieldValue.equals(messagesSelectionParam.getMatcherFieldValue())) {
                result = false;
            }
        }
        return result;
    }
}