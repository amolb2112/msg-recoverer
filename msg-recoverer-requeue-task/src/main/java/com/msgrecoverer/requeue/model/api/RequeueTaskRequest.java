package com.msgrecoverer.requeue.model.api;

/**
 * Class which holds the request params passed to the patch job
 *
 * Created by amolb2112 on 4/23/17.
 */
public class RequeueTaskRequest {
    private String bucketName;
    private String environment;
    private String queueName;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    @Override
    public String toString() {
        return "RequeueTaskRequest{" +
                "bucketName='" + bucketName + '\'' +
                ", environment='" + environment + '\'' +
                ", queueName='" + queueName + '\'' +
                '}';
    }
}
