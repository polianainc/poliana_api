package com.poliana.web.serialize;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author David Gilmore
 * @date 4/7/14
 */
@Component("webContextFilter")
public class WebContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ServletContext servletContext = this.getServletContext();

        WebContext.create(request, response, servletContext);
        filterChain.doFilter(request, response);
        WebContext.clear();
    }
}
