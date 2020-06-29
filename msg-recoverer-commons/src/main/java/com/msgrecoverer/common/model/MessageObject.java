package com.msgrecoverer.common.model;

import com.rabbitmq.client.AMQP;

import java.io.Serializable;

/**
 * Wrapper for the message from RabbitMQ (contains all the info required to reinject the messagae back into the queue)
 *
 * Created by amolb2112 on 5/3/17.
 */
public class MessageObject implements Serializable {
    private String messageContent;
    private String messageIdentifier;
    private AMQP.BasicProperties properties;

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageIdentifier() {
        return messageIdentifier;
    }

    public void setMessageIdentifier(String messageIdentifier) {
        this.messageIdentifier = messageIdentifier;
    }

    public AMQP.BasicProperties getProperties() {
        return properties;
    }

    public void setProperties(AMQP.BasicProperties properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "MessageObject{" +
                "messageContent='" + messageContent + '\'' +
                ", messageIdentifier='" + messageIdentifier + '\'' +
                ", properties=" + properties +
                '}';
    }
}
