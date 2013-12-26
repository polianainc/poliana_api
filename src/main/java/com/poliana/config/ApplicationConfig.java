package com.poliana.config;

import org.springframework.context.annotation.*;


@Configuration
@Import({ImpalaConfig.class, HiveConfig.class, MongoConfig.class})
@ComponentScan("com.poliana.core")
public class ApplicationConfig {}