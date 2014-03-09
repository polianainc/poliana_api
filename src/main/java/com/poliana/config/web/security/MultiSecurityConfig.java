package com.poliana.config.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.filter.GenericFilterBean;

/**
* @author David Gilmore
* @date 2/3/14
*/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class MultiSecurityConfig {

    @Order(1)
    @Configuration
    @PropertySource(value={"classpath:api.properties"})
    public static class GlobalSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        Environment env;

        @Override
        @Bean(name="myAuthenticationManager")
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {

            auth
                    .inMemoryAuthentication()

                    .withUser("user")
                        .password("password")
                        .roles("USER")

                    .and()

                    .withUser(env.getProperty("api.user"))
                        .password(env.getProperty("api.pass"))
                        .roles("USER", "ADMIN");
        }
    }

    @Order(2)
    @Configuration
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        public GenericFilterBean restSecurityFilter() throws Exception {

            return new AuthenticationTokenProcessingFilter(authenticationManagerBean());
        }

        protected void configure(HttpSecurity http) throws Exception {

            http
                    .addFilterBefore(restSecurityFilter(), AbstractPreAuthenticatedProcessingFilter.class);

            http
//                    .antMatcher("/**")
                    .httpBasic();
        }
    }

    @Order(3)
    @Configuration
    public static class FormWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        public void configure(WebSecurity web) throws Exception {

            web
                    .ignoring()
                    .antMatchers("/industries/*", "/pacs/*", "/endpoints", "/politicians/**");
        }

        protected void configure(HttpSecurity http) throws Exception {

            http
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated();

            http
                    .exceptionHandling();

            http
                    .httpBasic();

            http
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }
}