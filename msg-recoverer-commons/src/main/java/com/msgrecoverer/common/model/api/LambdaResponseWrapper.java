package com.msgrecoverer.common.model.api;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by amolb2112 on 4/22/17.
 */
public class LambdaResponseWrapper implements Serializable {
    private final int statusCode;
    private final Map<String, String> headers;
    private final String body;

    public LambdaResponseWrapper(String body, Map<String, String> headers, int statusCode) {
        this.body = body;
        this.headers = headers;
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getStatusCode() {
        return statusCode;
    }
}