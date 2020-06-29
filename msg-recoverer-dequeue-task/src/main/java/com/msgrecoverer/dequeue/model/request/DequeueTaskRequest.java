package com.msgrecoverer.dequeue.model.request;

import java.io.Serializable;
import java.util.List;

/**
 * Created by amolb2112 on 4/22/17.
 */
public class DequeueTaskRequest implements Serializable {
    private String bucketName;
    private String messageIdentifierPath;
    private String environment;
    private String dlQueueName;
    private List<MessageSelectionParams> messageSelectionParams;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getDlQueueName() {
        return dlQueueName;
    }

    public void setDlQueueName(String dlQueueName) {
        this.dlQueueName = dlQueueName;
    }

    public List<MessageSelectionParams> getMessageSelectionParams() {
        return messageSelectionParams;
    }

    public void setMessageSelectionParams(List<MessageSelectionParams> messageSelectionParams) {
        this.messageSelectionParams = messageSelectionParams;
    }

    public String getMessageIdentifierPath() {
        return messageIdentifierPath;
    }

    public void setMessageIdentifierPath(String messageIdentifierPath) {
        this.messageIdentifierPath = messageIdentifierPath;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    @Override
    public String toString() {
        return "DequeueTaskRequest{" +
                "bucketName='" + bucketName + '\'' +
                ", messageIdentifierPath='" + messageIdentifierPath + '\'' +
                ", environment='" + environment + '\'' +
                ", dlQueueName='" + dlQueueName + '\'' +
                ", messageSelectionParams=" + messageSelectionParams +
                '}';
    }
}
