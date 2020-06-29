package com.msgrecoverer.patch.model.api;

/**
 * Class which holds the result of the patch job process executing
 *
 * Created by amolb2112 on 4/23/17.
 */
public class PatchTaskResponse {
    private int successfullyProcessedCount;
    private int failedProcessingCount;
    private int totalCount;

    public int getSuccessfullyProcessedCount() {
        return successfullyProcessedCount;
    }

    public void setSuccessfullyProcessedCount(int successfullyProcessedCount) {
        this.successfullyProcessedCount = successfullyProcessedCount;
    }

    public int getFailedProcessingCount() {
        return failedProcessingCount;
    }

    public void setFailedProcessingCount(int failedProcessingCount) {
        this.failedProcessingCount = failedProcessingCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
