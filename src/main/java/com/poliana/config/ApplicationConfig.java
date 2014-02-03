package com.poliana.config;

import org.springframework.context.annotation.*;


@Configuration
@Import({ImpalaConfig.class, RedisConfig.class, HiveConfig.class, MongoConfig.class, AWSConfig.class})
@ComponentScan("com.poliana.core")
public class ApplicationConfig {}