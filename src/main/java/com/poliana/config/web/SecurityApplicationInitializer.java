package com.poliana.config.web;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

import javax.servlet.ServletContext;

/**
 * In conjunction with {@link WebApplicationInitializer}, this configuration class sets
 * up Spring Security and Spring Security OAuth.
 *
 * @author Rob Winch
 * @see WebApplicationInitializer
 */
public class SecurityApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    /**
     * Instruct Spring Security to use the {@link DispatcherServlet}'s
     * {@link WebApplicationContext} to find the springSecurityFilterChain.
     */
    @Override
    protected String getDispatcherWebApplicationContextSuffix() {
        return AbstractDispatcherServletInitializer.DEFAULT_SERVLET_NAME;
    }

    /**
     * Insert the following filters before Spring Security. Be careful when inserting
     * filters before Spring Security!
     */
    @Override
    protected void afterSpringSecurityFilterChain(ServletContext servletContext) {
        insertFilters(servletContext, new HiddenHttpMethodFilter(), new MultipartFilter() , new OpenEntityManagerInViewFilter());
    }

    /**
     * Register the {@link HttpSessionEventPublisher}
     */
    @Override
    protected boolean enableHttpSessionEventPublisher() {
        return true;
    }

}
