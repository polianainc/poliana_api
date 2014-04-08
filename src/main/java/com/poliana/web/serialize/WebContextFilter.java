package com.poliana.web.serialize;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author David Gilmore
 * @date 4/7/14
 */
//@Component("webContextFilter")
public class WebContextFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        ServletContext servletContext = this.getServletContext();

        WebContext.create(request, response, servletContext);
        chain.doFilter(request, response);
        WebContext.clear();
    }
}
