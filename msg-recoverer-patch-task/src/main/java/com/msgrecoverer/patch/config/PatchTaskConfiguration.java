package com.msgrecoverer.patch.config;

import com.msgrecoverer.common.utils.YamlUtils;
import com.msgrecoverer.patch.model.config.PatchConfiguration;

import java.io.IOException;

/**
 * Created by amolb2112 on 5/4/17.
 */
public class PatchTaskConfiguration {
    /**
     * Get the config params for the respective environment
     *
     * @param env
     * @return
     * @throws IOException
     */
    public static PatchConfiguration getConfiguration(String env) throws IOException{
        PatchConfiguration patchConfiguration
                = YamlUtils.load("configuration-"+env+".yml", PatchConfiguration.class);
        return patchConfiguration;
    }

}
