package com.msgrecoverer.common.model;

import java.io.Serializable;

public class BrokerConfig implements Serializable {
    private String brokerHost;
    private String brokerUsername;
    private String brokerPassword;
    private String sslEnabled;

    public String getBrokerHost() {
        return brokerHost;
    }

    public void setBrokerHost(String brokerHost) {
        this.brokerHost = brokerHost;
    }

    public String getBrokerUsername() {
        return brokerUsername;
    }

    public void setBrokerUsername(String brokerUserName) {
        this.brokerUsername = brokerUserName;
    }

    public String getBrokerPassword() {
        return brokerPassword;
    }

    public void setBrokerPassword(String brokerPassword) {
        this.brokerPassword = brokerPassword;
    }

    public String getSslEnabled() {
        return sslEnabled;
    }

    public void setSslEnabled(String sslEnabled) {
        this.sslEnabled = sslEnabled;
    }

    @Override
    public String toString() {
        return "BrokerConfig{" +
                "brokerHost='" + brokerHost + '\'' +
                ", brokerUsername='" + brokerUsername + '\'' +
                ", brokerPassword='" + brokerPassword + '\'' +
                ", sslEnabled='" + sslEnabled + '\'' +
                '}';
    }
}
