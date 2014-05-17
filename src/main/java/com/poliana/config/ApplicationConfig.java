package com.poliana.config;

import com.poliana.config.web.SecurityConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.poliana.core")
@Import({
          LockeConfig.class
        , ImpalaConfig.class
        , RedisConfig.class
        , HiveConfig.class
        , MongoConfig.class
        , AWSConfig.class
        , SecurityConfig.class
})
public class ApplicationConfig {}