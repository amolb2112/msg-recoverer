package com.msgrecoverer.requeue.model.config;

import com.msgrecoverer.common.model.BrokerConfig;

/**
 * Created by amolb2112 on 5/3/17.
 */
public class RequeueConfiguration {
    private String baseBucketName;
    private BrokerConfig brokerConfig;

    public String getBaseBucketName() {
        return baseBucketName;
    }

    public void setBaseBucketName(String baseBucketName) {
        this.baseBucketName = baseBucketName;
    }

    public BrokerConfig getBrokerConfig() {
        return brokerConfig;
    }

    public void setBrokerConfig(BrokerConfig brokerConfig) {
        this.brokerConfig = brokerConfig;
    }

    @Override
    public String toString() {
        return "RequeueConfiguration{" +
                ", baseBucketName='" + baseBucketName + '\'' +
                '}';
    }
}
