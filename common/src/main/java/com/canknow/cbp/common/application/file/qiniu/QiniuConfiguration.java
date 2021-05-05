package com.canknow.cbp.common.application.file.qiniu;

import com.qiniu.http.Client;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QiniuConfiguration {
    @Autowired
    private QiniuConfig qiniuConfig;

    @Bean
    public UploadManager uploadManager() {
        return new UploadManager(qiniuConfiguration());
    }

    @Bean
    public Auth auth() {
        return Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
    }

    @Bean
    public BucketManager bucketManager() {
        return new BucketManager(auth(), qiniuConfiguration());
    }

    @Bean
    public com.qiniu.storage.Configuration qiniuConfiguration() {
        return new com.qiniu.storage.Configuration();
    }

    @Bean
    public Client client(com.qiniu.storage.Configuration c){
        return new Client(c);
    }
}
