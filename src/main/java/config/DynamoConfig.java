package config;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

//@Configuration
public class DynamoConfig {

    @Autowired
    Environment env;

    @Bean
    private AmazonDynamoDBClient amazonDynamoDBClient() throws Exception {
        String ACCESSKEY = env.getProperty("awsAccessKeyId");
        String SECRETKEY = env.getProperty("awsSecretAccessKey");
        BasicAWSCredentials creds = new BasicAWSCredentials(ACCESSKEY,
                SECRETKEY);
        AmazonDynamoDBClient dynamoDB = new AmazonDynamoDBClient(creds);
        dynamoDB.setEndpoint("http://dynamodb.us-east-1.amazonaws.com");
        return dynamoDB;
    }
}
