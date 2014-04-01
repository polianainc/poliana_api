package com.poliana.config.web;

import com.poliana.users.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
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
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
* @author David Gilmore
* @date 2/3/14
*/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Order(1)
    @Configuration
    @Import(UserDBConfig.class)
    @ComponentScan(basePackages = "com.poliana.users")
    public static class GlobalSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        Environment env;

        @Autowired
        private RESTDaoAuthenticationProvider authenticationProvider;

        @Autowired
        UserDBConfig userDBConfig;

        @Autowired
        UserSecurityRepositoryImpl userSecurityRepository;

        @Autowired   // HACK
        public void setRestRepositoryDB() {
            try {
                this.userSecurityRepository.setMongoStore(userDBConfig.userStore());
            }
            catch (Exception e) {}
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

        @Bean
        public RequestMatcher requestMatcher() {

            NegatedRequestMatcher index = new NegatedRequestMatcher(new AntPathRequestMatcher("/"));
            NegatedRequestMatcher users = new NegatedRequestMatcher(new AntPathRequestMatcher("/user"));
            NegatedRequestMatcher userServices = new NegatedRequestMatcher(new AntPathRequestMatcher("/user/**"));
            NegatedRequestMatcher endpoints = new NegatedRequestMatcher(new AntPathRequestMatcher("/endpoints"));
            NegatedRequestMatcher resources = new NegatedRequestMatcher(new AntPathRequestMatcher("/resources/**"));
            NegatedRequestMatcher admin = new NegatedRequestMatcher(new AntPathRequestMatcher("/admin"));
            NegatedRequestMatcher adminServices = new NegatedRequestMatcher(new AntPathRequestMatcher("/admin/**"));
            NegatedRequestMatcher login = new NegatedRequestMatcher(new AntPathRequestMatcher("/login"));

            return new AndRequestMatcher(index, users, userServices, endpoints, resources, admin, adminServices, login);
        }

        @Override
        @Bean(name="myAuthenticationManager")
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {

            auth.eraseCredentials(true);
            auth.authenticationProvider(authenticationProvider);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                    .csrf().disable();

            http
                    .authorizeRequests()
                    .antMatchers(
                            "/",
                            "/endpoints",
                            "/user",
                            "/user/**",
                            "/resources/",
                            "/admin",
                            "/admin/**",
                            "/login"
                    )
                    .permitAll()
                    .anyRequest()
                    .authenticated();

            http
                    .addFilterBefore(restAuthenticationFilter(), DefaultLoginPageGeneratingFilter.class)
                        .requestMatcher(requestMatcher());

            http
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        }
    }

    @Order(2)
    @Configuration
    @PropertySource(value={"classpath:api.properties"})
    public static class AdminSecurityConfigurer extends WebSecurityConfigurerAdapter {


        @Autowired
        Environment env;

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {

            auth
                    .inMemoryAuthentication()
                    .withUser(env.getProperty("api.user"))
                    .password(env.getProperty("api.pass"))
                    .roles("USER", "ADMIN");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                    .csrf().disable();

            http
                    .authorizeRequests()
                    .antMatchers("/admin", "/admin/**")
                    .authenticated();

            http
                    .formLogin();

            http
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);

        }
    }
}