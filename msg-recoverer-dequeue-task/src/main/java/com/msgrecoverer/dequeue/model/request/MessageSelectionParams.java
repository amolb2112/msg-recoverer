package com.msgrecoverer.dequeue.model.request;

import java.io.Serializable;

public class MessageSelectionParams implements Serializable {
    private String matcherFieldPath;
    private String matcherFieldValue;

    public String getMatcherFieldPath() {
        return matcherFieldPath;
    }

    public void setMatcherFieldPath(String matcherFieldPath) {
        this.matcherFieldPath = matcherFieldPath;
    }

    public String getMatcherFieldValue() {
        return matcherFieldValue;
    }

    public void setMatcherFieldValue(String matcherFieldValue) {
        this.matcherFieldValue = matcherFieldValue;
    }

    @Override
    public String toString() {
        return "MessageSelectionParams{" +
                "matcherFieldPath='" + matcherFieldPath + '\'' +
                ", matcherFieldValue='" + matcherFieldValue + '\'' +
                '}';
    }
}
