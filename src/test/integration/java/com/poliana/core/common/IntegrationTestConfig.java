package com.poliana.core.common;

import com.poliana.config.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author David Gilmore
 * @date 4/15/14
 */
@Configuration
@ComponentScan("com.poliana.core")
@Import({
        ImpalaConfig.class
        , RedisConfig.class
        , HiveConfig.class
        , MongoConfig.class
        , AWSConfig.class
})
public class IntegrationTestConfig {}
