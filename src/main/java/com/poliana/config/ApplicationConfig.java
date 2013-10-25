package com.poliana.config;

import org.springframework.context.annotation.*;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.shell.plugin.BannerProvider;
import org.springframework.shell.plugin.PromptProvider;

@Configuration
@Import({ImpalaConfig.class, HiveConfig.class, MongoConfig.class})
@Profile("production")
@ComponentScan("com.poliana")
public class ApplicationConfig {
    @Bean
    public FileSystemResourceLoader resourceLoader() {
        return new FileSystemResourceLoader();
    }
    @Bean
    public BannerProvider welcomeMessage() {
        return new BannerConfig();
    }
    @Bean
    public PromptProvider prompt() {
        return new PromptConfig();
    }
}