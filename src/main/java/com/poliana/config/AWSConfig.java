package com.poliana.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @author graysoncarroll
 * @date 2/2/14
 */
@ComponentScan(basePackages = "com.poliana.core")
@PropertySource(value={"classpath:aws.properties"})
public class AWSConfig {

    @Autowired
    Environment env;

    @Bean
    public AWSCredentials credentials() {

        String key, secret;
        key = env.getProperty("awsAccessKeyId");
        secret = env.getProperty("awsSecretAccessKey");

        return new BasicAWSCredentials(key, secret);
    }

    @Bean
    public AmazonS3 s3Connection() {
        return new AmazonS3Client(this.credentials());
    }
}


