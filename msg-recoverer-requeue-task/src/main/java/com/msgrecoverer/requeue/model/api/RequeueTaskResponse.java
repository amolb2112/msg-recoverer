package com.msgrecoverer.requeue.model.api;

/**
 * Class which holds the result of the patch job process executing
 *
 * Created by amolb2112 on 4/23/17.
 */
public class RequeueTaskResponse {
    private String body;
    private String errors;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }
}
