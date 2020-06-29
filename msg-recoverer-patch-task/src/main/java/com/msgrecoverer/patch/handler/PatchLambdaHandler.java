package com.msgrecoverer.patch.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.msgrecoverer.common.client.S3Wrapper;
import com.msgrecoverer.common.model.api.LambdaRequestWrapper;
import com.msgrecoverer.common.model.api.LambdaResponseWrapper;
import com.msgrecoverer.common.utils.MsgRecovererUtils;
import com.msgrecoverer.patch.model.api.PatchTaskRequest;
import com.msgrecoverer.patch.model.api.PatchTaskResponse;
import com.msgrecoverer.patch.tasks.PatchTask;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Class which handles the Recovery Request. Does the following:
 * 1. Dequeues the messages and adds them to a private bucket
 * 2. Invoke the patching job in sync mode
 *
 * Created by amolb2112 on 4/22/17.
 */
public class PatchLambdaHandler implements RequestHandler<LambdaRequestWrapper, LambdaResponseWrapper> {

    /**
     * Handler for the Request
     *
     * @param request
     * @param context
     * @return
     */
    public LambdaResponseWrapper handleRequest(LambdaRequestWrapper request, Context context) {
        int httpStatusCode = 200;
        Gson gson = new Gson();
        LambdaLogger logger = context.getLogger();

        PatchTaskResponse patchTaskResponse = null;

        try {
            //TODO - add input param validations
            // initialization
            PatchTaskRequest patchTaskRequest = gson.fromJson(request.getBody(), PatchTaskRequest.class);
            logger.log("Request received. Input Params - " + patchTaskRequest.toString());

            // initializing connectors
            S3Wrapper s3Wrapper = new S3Wrapper(logger);

            logger.log("Invoking patch job...");
            PatchTask patchTask = new PatchTask(logger,s3Wrapper);
            patchTaskResponse = patchTask.handleMessages(patchTaskRequest.getBucketName());
            logger.log("Patch job execution completed...");

        } catch (Exception e) {
            logger.log("Caught Exception " + MsgRecovererUtils.getStackTrace(e));
            httpStatusCode = 500;
        }

        if(patchTaskResponse == null) {
            patchTaskResponse = new PatchTaskResponse();
        }

        return new LambdaResponseWrapper(
                gson.toJson(patchTaskResponse), new HashMap<>(), httpStatusCode);
    }


}
