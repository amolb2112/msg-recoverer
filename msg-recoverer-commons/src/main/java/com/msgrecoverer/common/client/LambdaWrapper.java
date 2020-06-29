package com.msgrecoverer.common.client;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.nio.ByteBuffer;

/**
 * Wrapper for AWS Lambda SDK operations
 *
 * Created by amolb2112 on 5/3/17.
 */
public class LambdaWrapper {
    private AWSLambda lambda;
    private LambdaLogger LOGGER;

    public LambdaWrapper(LambdaLogger logger) {
        lambda = AWSLambdaClientBuilder.defaultClient();
        LOGGER = logger;
    }

    public InvokeResult invokeLambda(String functionName, String payload) {
        LOGGER.log("Invoking lamdba - "+functionName+". Payload - "+payload);
        InvokeRequest req = new InvokeRequest()
                .withFunctionName(functionName)
                .withPayload(ByteBuffer.wrap(payload.getBytes()));
        return lambda.invoke(req);
    }
}