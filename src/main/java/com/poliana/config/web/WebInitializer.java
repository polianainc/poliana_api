//package com.poliana.config.web;
//
//import com.poliana.config.ApplicationConfig;
//import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//
///**
//* @author David Gilmore
//* @date 12/15/13
//*/
//public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
//
//    @Override
//    protected Class<?>[] getRootConfigClasses() {
//        return new Class[0];
//    }
//
//    @Override
//    protected Class<?>[] getServletConfigClasses() {
//        return new Class<?>[]{ ApplicationConfig.class };
//    }
//
//    @Override
//    protected String[] getServletMappings() {
//        return new String[]{ "/" };
//    }
//
//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//
//        super.onStartup(servletContext);
//    }
//}
