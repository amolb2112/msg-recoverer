package com.msgrecoverer.dequeue.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.msgrecoverer.common.client.S3Wrapper;
import com.msgrecoverer.common.config.CommonsConfiguration;
import com.msgrecoverer.common.model.api.LambdaRequestWrapper;
import com.msgrecoverer.common.model.api.LambdaResponseWrapper;
import com.msgrecoverer.common.utils.MsgRecovererUtils;
import com.msgrecoverer.dequeue.config.DequeueTaskConfiguration;
import com.msgrecoverer.dequeue.model.TaskResponsesWrapper;
import com.msgrecoverer.dequeue.model.config.DequeueConfiguration;
import com.msgrecoverer.dequeue.model.request.DequeueTaskRequest;
import com.msgrecoverer.dequeue.model.response.DequeueTaskResponse;
import com.msgrecoverer.dequeue.task.DequeueTask;
import com.google.gson.Gson;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;

/**
 * Class which handles the Recovery Request. Does the following:
 * 1. Dequeues the messages and adds them to a private bucket
 * 2. Invoke the patching job in sync mode
 *
 * Created by amolb2112 on 4/22/17.
 */
public class DequeueLambdaHandler implements RequestHandler<LambdaRequestWrapper, LambdaResponseWrapper> {
    Gson gson = new Gson();

    /**
     * Handler for the Request
     *
     * @param request
     * @param context
     * @return
     */
    public LambdaResponseWrapper handleRequest(LambdaRequestWrapper request, Context context) {
        LambdaLogger logger = context.getLogger();
        DequeueTask dequeueTask;
        DequeueTaskResponse dequeueTaskResponse = null;

        int httpStatusCode = 200;

        try {
            //TODO - add input param validations
            // initialization
            DequeueTaskRequest dequeueTaskRequest = gson.fromJson(request.getBody(), DequeueTaskRequest.class);
            logger.log("Request received. Input Params - " + dequeueTaskRequest.toString());

            DequeueConfiguration config = DequeueTaskConfiguration.getConfiguration(dequeueTaskRequest.getEnvironment());
            ConnectionFactory factory = CommonsConfiguration.connectionFactory(config.getBrokerConfig());

            // initializing connectors
            S3Wrapper s3Wrapper = new S3Wrapper(logger);

            logger.log("Invoking dequeue job...");

            dequeueTask = new DequeueTask(config,logger,factory,s3Wrapper, dequeueTaskRequest);
            dequeueTaskResponse = dequeueTask.handleMessages();

            logger.log("Dequeue Task completed. Message count to process - "+ dequeueTaskResponse.getSuccessCount());
        } catch (Exception e) {
            logger.log("Caught Exception " + MsgRecovererUtils.getStackTrace(e));
            httpStatusCode = 500;
        }

        if(dequeueTaskResponse == null) {
            dequeueTaskResponse = new DequeueTaskResponse();
        }

        return new LambdaResponseWrapper(
                gson.toJson(new TaskResponsesWrapper(dequeueTaskResponse)),new HashMap<>(), httpStatusCode);
    }



}
