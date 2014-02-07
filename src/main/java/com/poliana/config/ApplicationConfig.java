package com.poliana.config;

import com.poliana.config.web.security.MultiSecurityConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@ComponentScan("com.poliana.core")
@Import({ImpalaConfig.class, RedisConfig.class, HiveConfig.class, MongoConfig.class, AWSConfig.class, MultiSecurityConfig.class})
public class ApplicationConfig {}