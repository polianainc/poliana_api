package com.poliana.config.web;

import com.poliana.users.CorsFilter;
import com.poliana.web.serialize.WebContextFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
* @author David Gilmore
* @date 12/15/13
*/
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

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
        servletContext.addFilter("webContextFilter", new WebContextFilter()).addMappingForUrlPatterns(null, false, "/*");

        super.onStartup(servletContext);
    }
}
