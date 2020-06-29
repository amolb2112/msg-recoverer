package com.msgrecoverer.patch.tasks;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.msgrecoverer.common.client.S3Wrapper;
import com.msgrecoverer.common.model.MessageObject;
import com.msgrecoverer.patch.model.MessageModel;
import com.msgrecoverer.patch.model.api.PatchTaskRequest;
import com.msgrecoverer.patch.model.api.PatchTaskResponse;
import com.google.gson.Gson;

import java.net.URL;

/**
 * Created by amolb2112 on 5/4/17.
 */
public class PatchTask {
    private LambdaLogger LOGGER;
    private Gson gson;
    private S3Wrapper s3Wrapper;

    public PatchTask(LambdaLogger LOGGER, S3Wrapper s3Wrapper) {
        this.LOGGER = LOGGER;
        this.s3Wrapper = s3Wrapper;
        this.gson = new Gson();
    }

    public PatchTaskResponse handleMessages(String bucketName) throws Exception {
        int sucessCount = 0;
        int failedCount = 0;
        PatchTaskRequest patchTaskRequest = new PatchTaskRequest();
        patchTaskRequest.setBucketName(bucketName);

        PatchTaskResponse patchTaskResponse = new PatchTaskResponse();
        S3Wrapper s3Wrapper = new S3Wrapper(LOGGER);
        MessageObject messageObject = null;
        MessageModel model = null;

        String baseBucketName = "msgrecoverer-bucket"; // TODO - read this from config
        LOGGER.log("Reaching out for S3 Bucket for original messages. Bucket Name - "+baseBucketName);
        ObjectListing objectListing = s3Wrapper.getObjectListing(baseBucketName,bucketName+"/original/");
        int totalMessageCount = objectListing.getObjectSummaries().size();
        LOGGER.log("S3 Bucket with original message located... Total messages available in bucket -" + totalMessageCount);

        for(S3ObjectSummary s3ObjectSummary: objectListing.getObjectSummaries()){
            try{
                messageObject = (MessageObject) s3Wrapper.downloadFileFromS3AsObject(baseBucketName,s3ObjectSummary.getKey(), MessageObject.class);
                LOGGER.log("Processing message with identifier - " + messageObject.getMessageIdentifier());

                model = gson.fromJson(messageObject.getMessageContent(), MessageModel.class);
                model.setTransformed(true);
                messageObject.setMessageContent(gson.toJson(model));

                String[] pathNames = s3ObjectSummary.getKey().split("/");
                String resourcePath = bucketName+"/transformed/"+pathNames[pathNames.length-1];
                LOGGER.log("Uploading message at path - " + resourcePath);
                uploadToS3(baseBucketName,resourcePath, messageObject);
                LOGGER.log("Uploaded message at path - " + resourcePath);
            } catch (Exception e){
                LOGGER.log("Message failed processing. Id - " + messageObject.getMessageIdentifier());
            }
        }

        patchTaskResponse.setSuccessfullyProcessedCount(sucessCount);
        patchTaskResponse.setFailedProcessingCount(failedCount);
        patchTaskResponse.setTotalCount(totalMessageCount);

        return patchTaskResponse;
    }

    private URL uploadToS3(String bucketName, String resourcePath, MessageObject messageContent) {
        Gson gson = new Gson();
        return s3Wrapper.uploadFileToS3(bucketName,resourcePath, gson.toJson(messageContent));
    }
}