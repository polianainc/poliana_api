package com.poliana.config.web;

import com.poliana.web.rest.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
* @author David Gilmore
* @date 12/15/13
*/
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Autowired
    private OncePerRequestFilter corsFilter;

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{ WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{ "/" };
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        servletContext.addFilter("corsFilter", new CorsFilter()).addMappingForUrlPatterns(null, false, "/*");

        super.onStartup(servletContext);
    }
}
