package com.poliana.config.web;

import com.poliana.core.users.UserSecurityRepositoryImpl;
import com.poliana.core.users.UserSecurityService;
import com.poliana.core.users.UserSecurityServiceImpl;
import com.poliana.web.rest.*;
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
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;

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


        @Bean
        public HMacShaPasswordEncoder passwordEncoder() {

            return new HMacShaPasswordEncoder(256, true);
        }

        @Bean
        public UserSecurityService userSecurityService() {

            UserSecurityServiceImpl service = new UserSecurityServiceImpl();
            service.setUserSecurityRepository(new UserSecurityRepositoryImpl());

            return service;
        }

        @Bean
        public RESTDaoAuthenticationProvider daoAuthenticationProvider() {

            RESTDaoAuthenticationProvider provider = new RESTDaoAuthenticationProvider();

            provider.setUserSecurityService(userSecurityService());

            provider.setPasswordEncoder(passwordEncoder());

            return provider;
        }

        @Bean
        public SimpleUrlAuthenticationSuccessHandler successHandler() {

            SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();

            handler.setRedirectStrategy(new NoRedirectStrategy());

            return handler;
        }

        @Bean
        public AuthenticationFailureHandler failureHandler() {

            return new AuthenticationFailure();
        }

        @Bean
        public RESTAuthenticationFilter restAuthenticationFilter() throws Exception {

            RESTAuthenticationFilter filter = new RESTAuthenticationFilter("/") {};

            filter.setAuthenticationManager(authenticationManagerBean());
            filter.setAuthenticationSuccessHandler(successHandler());

            return filter;
        }

        @Override
        @Bean(name="myAuthenticationManager")
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {

            auth.eraseCredentials(true);
            auth.authenticationProvider(daoAuthenticationProvider());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                    .csrf().disable();

            http
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated();

            http
                    .addFilterBefore(restAuthenticationFilter(), DefaultLoginPageGeneratingFilter.class);

            http
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.NEVER);

        }
    }
}
