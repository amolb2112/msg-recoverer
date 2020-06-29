package com.msgrecoverer.patch.model;

/**
 * Created by amolb2112 on 6/2/17.
 */
public class MessageModel {
    private String messageId;
    private String version;
    private String subversion;
    private String text;
    private boolean transformed;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSubversion() {
        return subversion;
    }

    public void setSubversion(String subversion) {
        this.subversion = subversion;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isTransformed() {
        return transformed;
    }

    public void setTransformed(boolean transformed) {
        this.transformed = transformed;
    }
}

