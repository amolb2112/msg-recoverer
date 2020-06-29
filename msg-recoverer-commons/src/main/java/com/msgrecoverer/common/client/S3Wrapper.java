package com.msgrecoverer.common.client;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.msgrecoverer.common.utils.MsgRecovererUtils;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Wrapper class for AWS S3 SDK operations
 *
 * Created by amolb2112 on 5/3/17.
 */
public class S3Wrapper {
    private AmazonS3 s3Client;
    private LambdaLogger LOGGER;

    public S3Wrapper() {
        this.s3Client = AmazonS3ClientBuilder.defaultClient();
    }

    public S3Wrapper(LambdaLogger logger) {
        this.s3Client = AmazonS3ClientBuilder.defaultClient();
        this.LOGGER = logger;
    }

    public AmazonS3 getS3Client() {
        return s3Client;
    }

    public URL uploadFileToS3(String s3Bucket, String s3FileName, String content) throws AmazonServiceException {
        InputStream inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(content.length());
        PutObjectRequest request = new PutObjectRequest(s3Bucket, s3FileName, inputStream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);
        s3Client.putObject(request);
        return s3Client.getUrl(s3Bucket, s3FileName);
    }

    public Object downloadFileFromS3AsObject(String s3Bucket, String s3Key, Class aClass) throws AmazonServiceException, IOException {
        GetObjectRequest getObjectRequest = new GetObjectRequest(s3Bucket, s3Key);
        S3Object s3Object = s3Client.getObject(getObjectRequest);
        InputStream io = s3Object.getObjectContent();
        Gson gson = new Gson();
        return gson.fromJson(MsgRecovererUtils.convertStreamToString(io),aClass);
    }

    public ObjectListing getObjectListing(String s3Bucket,String prefix) throws AmazonServiceException {
        return s3Client.listObjects(s3Bucket, prefix);
    }

    public void setLOGGER(LambdaLogger LOGGER) {
        this.LOGGER = LOGGER;
    }
}