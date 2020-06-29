package com.msgrecoverer.dequeue.model.response;

import com.msgrecoverer.dequeue.common.DequeueResponseCodes;

/**
 * This class holds the key information for the unprocessed messages
 *
 * Created by amolb2112 on 4/23/17.
 */
public class MessageContext {
    private String messageIdentifier;
    private DequeueResponseCodes errorCode;
    private String errorMessage;

    public MessageContext(String messageIdentifier, DequeueResponseCodes errorCode) {
        this.messageIdentifier = messageIdentifier;
        this.errorCode = errorCode;
    }

    public MessageContext(DequeueResponseCodes errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessageIdentifier() {
        return messageIdentifier;
    }

    public void setMessageIdentifier(String messageIdentifier) {
        this.messageIdentifier = messageIdentifier;
    }

    public DequeueResponseCodes getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(DequeueResponseCodes errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "MessageContext{" +
                "messageIdentifier='" + messageIdentifier + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}
