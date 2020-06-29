package com.msgrecoverer.dequeue.model.config;

import com.msgrecoverer.common.model.BrokerConfig;

/**
 * Created by amolb2112 on 5/3/17.
 */
public class DequeueConfiguration {
    private String baseBucketName;
    private BrokerConfig brokerConfig;

    public BrokerConfig getBrokerConfig() {
        return brokerConfig;
    }

    public void setBrokerConfig(BrokerConfig brokerConfig) {
        this.brokerConfig = brokerConfig;
    }

    public String getBaseBucketName() {
        return baseBucketName;
    }

    public void setBaseBucketName(String baseBucketName) {
        this.baseBucketName = baseBucketName;
    }

    @Override
    public String toString() {
        return "DequeueConfiguration{" +
                "brokerConfig=" + brokerConfig +
                ", baseBucketName='" + baseBucketName + '\'' +
                '}';
    }
}
