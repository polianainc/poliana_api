package com.poliana.config.cli;

import com.poliana.config.ApplicationConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.shell.plugin.BannerProvider;
import org.springframework.shell.plugin.PromptProvider;


/**
 * @author David Gilmore
 * @date 12/26/13
 */
@Configuration
@Import(ApplicationConfig.class)
@ComponentScan("com.poliana.cli")
public class CliConfig {

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