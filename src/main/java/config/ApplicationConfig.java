package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.shell.plugin.BannerProvider;
import org.springframework.shell.plugin.PromptProvider;

@Configuration
@Import({HiveConfig.class, MongoConfig.class})
@ComponentScan("poliana.data")
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