package com.msgrecoverer.requeue.config;

import com.msgrecoverer.common.utils.YamlUtils;
import com.msgrecoverer.requeue.model.config.RequeueConfiguration;

import java.io.IOException;

/**
 * Created by amolb2112 on 5/4/17.
 */
public class RequeueTaskConfiguration {
    /**
     * Get the config params for the respective environment
     *
     * @param env
     * @return
     * @throws IOException
     */

    public static RequeueConfiguration getConfiguration(String env) throws IOException{
        RequeueConfiguration requeueConfiguration
                = YamlUtils.load("configuration-"+env+".yml", RequeueConfiguration.class);
        return requeueConfiguration;
    }

}
