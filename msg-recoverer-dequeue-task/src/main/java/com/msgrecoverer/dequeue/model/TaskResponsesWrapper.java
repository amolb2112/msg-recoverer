package com.msgrecoverer.dequeue.model;

import com.msgrecoverer.dequeue.model.response.DequeueTaskResponse;

/**
 * Wrapper for the responses of dequeue job and patch job responses
 *
 * Created by amolb2112 on 5/3/17.
 */
public class TaskResponsesWrapper {
    DequeueTaskResponse dequeueTaskResponse;
    //PatchTaskResult patchTaskResult;

    public TaskResponsesWrapper(DequeueTaskResponse dequeueTaskResponse) {
        this.dequeueTaskResponse = dequeueTaskResponse;
//        this.patchTaskResult = patchTaskResult;
    }

    public DequeueTaskResponse getDequeueTaskResponse() {
        return dequeueTaskResponse;
    }

    public void setDequeueTaskResponse(DequeueTaskResponse dequeueTaskResponse) {
        this.dequeueTaskResponse = dequeueTaskResponse;
    }

    @Override
    public String toString() {
        return "TaskResponsesWrapper{" +
                "dequeueTaskResponse=" + dequeueTaskResponse +
                '}';
    }
}
