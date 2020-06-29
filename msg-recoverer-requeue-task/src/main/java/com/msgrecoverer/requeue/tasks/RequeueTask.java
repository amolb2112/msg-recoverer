package com.msgrecoverer.requeue.tasks;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.msgrecoverer.common.client.S3Wrapper;
import com.msgrecoverer.common.model.MessageObject;
import com.msgrecoverer.requeue.model.api.RequeueTaskRequest;
import com.msgrecoverer.requeue.model.api.RequeueTaskResponse;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by amolb2112 on 5/4/17.
 */
public class RequeueTask {
    private LambdaLogger LOGGER;
    private ConnectionFactory factory;
    private S3Wrapper s3Wrapper;

    public RequeueTask(LambdaLogger LOGGER, ConnectionFactory factory, S3Wrapper s3Wrapper) {
        this.LOGGER = LOGGER;
        this.factory = factory;
        this.s3Wrapper = s3Wrapper;
    }

    public RequeueTaskResponse handleMessages(String bucketName, String queueName) throws Exception {
        String requeueTaskResponsePayload = "";

        RequeueTaskRequest requeueTaskRequest = new RequeueTaskRequest();
        requeueTaskRequest.setBucketName(bucketName);
        RequeueTaskResponse requeueTaskResponse = new RequeueTaskResponse();

        String baseBucketName = "msgrecoverer-bucket"; // TODO - read this from config
        LOGGER.log("Reaching out for S3 Bucket for original messages. Bucket Name - "+baseBucketName);
        ObjectListing objectListing = s3Wrapper.getObjectListing(baseBucketName,bucketName+"/transformed/");

        LOGGER.log("S3 Bucket with transformed message located... Total messages available in bucket -" + objectListing.getObjectSummaries().size());
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        for(S3ObjectSummary s3ObjectSummary: objectListing.getObjectSummaries()){
            MessageObject messageObject = (MessageObject) s3Wrapper.downloadFileFromS3AsObject(baseBucketName,s3ObjectSummary.getKey(), MessageObject.class);
            channel.basicPublish("", queueName, messageObject.getProperties(), messageObject.getMessageContent().getBytes());
            LOGGER.log("Published transformed message to queue");
        }

        //TODO - form Requeue Task Response
        requeueTaskResponse.setBody(requeueTaskResponsePayload);
        return requeueTaskResponse;
    }
}