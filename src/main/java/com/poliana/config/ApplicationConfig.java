package com.poliana.config;

import com.poliana.config.web.security.MultiSecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;


@Configuration
@ComponentScan("com.poliana.core")
@Import({ImpalaConfig.class, RedisConfig.class, HiveConfig.class, MongoConfig.class, AWSConfig.class, MultiSecurityConfig.class})
public class ApplicationConfig {

    @Bean
    public MailSender mailSender() {

        JavaMailSenderImpl sender =  new JavaMailSenderImpl();

        return sender;
    }

    @Bean
    public SimpleMailMessage simpleMailMessage() {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("spring@poliana.com");

        return message;
    }
}