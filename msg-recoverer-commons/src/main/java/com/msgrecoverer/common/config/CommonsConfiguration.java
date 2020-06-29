package com.msgrecoverer.common.config;

import com.msgrecoverer.common.model.BrokerConfig;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by amolb2112 on 6/1/17.
 */
public class CommonsConfiguration {

    /**
     * Initialise RabbitMQ connection factory
     *
     * @param brokerConfigParams
     * @return
     */
    public static ConnectionFactory connectionFactory(BrokerConfig brokerConfigParams) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        if(Boolean.valueOf(brokerConfigParams.getSslEnabled())) {
            connectionFactory.useSslProtocol();
        }

        connectionFactory.setHost(brokerConfigParams.getBrokerHost());
        connectionFactory.setUsername(brokerConfigParams.getBrokerUsername());
        connectionFactory.setPassword(brokerConfigParams.getBrokerPassword());

        connectionFactory.setConnectionTimeout(200000);

        return connectionFactory;
    }
}
