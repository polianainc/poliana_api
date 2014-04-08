package com.poliana.web.serialize;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author David Gilmore
 * @date 4/7/14
 */
public class WebContext {

    private static ThreadLocal<WebContext> tlv = new ThreadLocal<>();
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ServletContext servletContext;

    protected WebContext() {}

    private WebContext(HttpServletRequest request, HttpServletResponse response,
                       ServletContext servletContext) {
        this.request = request;
        this.response = response;
        this.servletContext = servletContext;
    }

    public static WebContext getInstance() {
        return tlv.get();
    }

    public static void create(HttpServletRequest request, HttpServletResponse response,
                              ServletContext servletContext) {
        WebContext wc = new WebContext(request, response, servletContext);
        tlv.set(wc);
    }

    public static void clear() {
        tlv.set(null);
    }

    public static ThreadLocal<WebContext> getTlv() {
        return tlv;
    }

    public static void setTlv(ThreadLocal<WebContext> tlv) {
        WebContext.tlv = tlv;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}