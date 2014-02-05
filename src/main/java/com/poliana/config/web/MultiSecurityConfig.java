package com.poliana.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
* @author David Gilmore
* @date 2/3/14
*/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class MultiSecurityConfig {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER").and()
                .withUser("admin").password("password").roles("USER", "ADMIN");
    }

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {


        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/api/**")
                    .httpBasic();
        }
    }

    @Configuration
    public static class FormWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        public void configure(WebSecurity web) throws Exception {

            web
                    .ignoring()
                    .antMatchers("/static/**","/status");
        }

        protected void configure(HttpSecurity http) throws Exception {

            http
                    .authorizeRequests()
                    .antMatchers("/").permitAll() // #4
                    .antMatchers("/admin/**").hasRole("ADMIN") // #6
                    .anyRequest().authenticated();

            http
                    .authorizeRequests()
                    .anyRequest().hasRole("USER");

            http
                    .formLogin();
//                    .loginPage("/login")
//                    .permitAll();
        }
    }
}