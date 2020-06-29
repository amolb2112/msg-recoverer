package com.msgrecoverer.patch.model.api;

/**
 * Class which holds the request params passed to the patch job
 *
 * Created by amolb2112 on 4/23/17.
 */
public class PatchTaskRequest {
    private String bucketName;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    @Override
    public String toString() {
        return "PatchTaskRequest{" +
                "bucketName='" + bucketName + '\'' +
                '}';
    }
}
