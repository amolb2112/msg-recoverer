package com.msgrecoverer.requeue.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.msgrecoverer.common.client.S3Wrapper;
import com.msgrecoverer.common.config.CommonsConfiguration;
import com.msgrecoverer.common.model.api.LambdaRequestWrapper;
import com.msgrecoverer.common.model.api.LambdaResponseWrapper;
import com.msgrecoverer.common.utils.MsgRecovererUtils;
import com.msgrecoverer.requeue.config.RequeueTaskConfiguration;
import com.msgrecoverer.requeue.model.api.RequeueTaskRequest;
import com.msgrecoverer.requeue.model.api.RequeueTaskResponse;
import com.msgrecoverer.requeue.model.config.RequeueConfiguration;
import com.msgrecoverer.requeue.tasks.RequeueTask;
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
public class RequeueLambdaHandler implements RequestHandler<LambdaRequestWrapper, LambdaResponseWrapper> {
    static S3Wrapper s3Wrapper;

    static {
        s3Wrapper = new S3Wrapper();
    }
    /**
     * Handler for the Request
     *
     * @param request
     * @param context
     * @return
     */
    public LambdaResponseWrapper handleRequest(LambdaRequestWrapper request, Context context) {
        //TODO - add input param validations
        int httpStatusCode = 200; //TODO - status code
        Gson gson = new Gson();
        LambdaLogger logger = context.getLogger();

        RequeueTaskRequest requeueTaskRequest;
        RequeueTaskResponse requeueTaskResponse = null;
        RequeueConfiguration requeueConfiguration;
        RequeueTask requeueTask;

        ConnectionFactory factory;

        requeueTaskRequest = gson.fromJson(request.getBody(), RequeueTaskRequest.class);
        logger.log("Request received. Input Params - " + requeueTaskRequest.toString());

        try {
            // initialization
            requeueConfiguration = RequeueTaskConfiguration.getConfiguration(requeueTaskRequest.getEnvironment());
            factory = CommonsConfiguration.connectionFactory(requeueConfiguration.getBrokerConfig());
            s3Wrapper.setLOGGER(logger);

            logger.log("Components Initialized. Invoking Requeue task...");
            requeueTask = new RequeueTask(logger,factory,s3Wrapper);
            requeueTaskResponse = requeueTask.handleMessages(requeueTaskRequest.getBucketName(),requeueTaskRequest.getQueueName());
            logger.log("Requeue job execution completed. Payload - "+ requeueTaskResponse);

        } catch (Exception e) {
            logger.log("Caught Exception " + MsgRecovererUtils.getStackTrace(e));
            httpStatusCode = 500;
        }

        if(requeueTaskResponse == null) {
            requeueTaskResponse = new RequeueTaskResponse();
        }
         return new LambdaResponseWrapper(gson.toJson(requeueTaskResponse), new HashMap<>(), httpStatusCode);
    }
}
