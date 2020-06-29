package com.msgrecoverer.dequeue.model.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which holds the result of the dequeue job process executing
 *
 * Created by amolb2112 on 4/23/17.
 */
public class DequeueTaskResponse {
    private int successCount;
    private int failureCount;
    private int totalCount;

//    private List<String> s3Paths;
    private List<MessageContext> messageContexts;
//    private List<ErrorContext> errorContext;

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

/*    public List<String> getS3Paths() {

        if (s3Paths == null) {
            s3Paths = new ArrayList<>();
        }
        return s3Paths;
    }*/

/*    public void setS3Paths(List<String> s3Paths) {
        this.s3Paths = s3Paths;
    }*/

    public List<MessageContext> getMessageContexts() {

        if (messageContexts == null) {
            messageContexts = new ArrayList<>();
        }
        return messageContexts;
    }

    public void setMessageContexts(List<MessageContext> messageContexts) {
        this.messageContexts = messageContexts;
    }

    @Override
    public String toString() {
        return "DequeueTaskResponse{" +
                "successCount=" + successCount +
                ", failureCount=" + failureCount +
                ", totalCount=" + totalCount +
                //", s3Paths=" + s3Paths +
                ", messageContexts=" + messageContexts +
                '}';
    }
}
