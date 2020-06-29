package com.msgrecoverer.patch.model.config;

/**
 * Created by amolb2112 on 5/3/17.
 */
public class PatchConfiguration {
    private String baseBucketName;
    public String getBaseBucketName() {
        return baseBucketName;
    }

    public void setBaseBucketName(String baseBucketName) {
        this.baseBucketName = baseBucketName;
    }

    @Override
    public String toString() {
        return "PatchConfiguration{" +
                ", baseBucketName='" + baseBucketName + '\'' +
                '}';
    }
}
