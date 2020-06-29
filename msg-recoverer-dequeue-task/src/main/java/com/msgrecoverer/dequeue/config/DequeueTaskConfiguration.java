package com.msgrecoverer.dequeue.config;

import com.msgrecoverer.common.utils.YamlUtils;
import com.msgrecoverer.dequeue.model.config.DequeueConfiguration;

import java.io.IOException;

/**
 * Created by amolb2112 on 5/4/17.
 */
public class DequeueTaskConfiguration {
    /**
     * Get the config params for the respective environment
     *
     * @param env
     * @return
     * @throws IOException
     */
    public static DequeueConfiguration getConfiguration(String env) throws IOException{
        DequeueConfiguration dequeueConfiguration
                = YamlUtils.load("configuration-"+env+".yml", DequeueConfiguration.class);
        return dequeueConfiguration;
    }


}
