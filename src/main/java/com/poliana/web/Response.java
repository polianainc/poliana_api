package com.poliana.web;

import org.springframework.hateoas.ResourceSupport;

/**
 * @author David Gilmore
 * @date 4/18/14
 */
public class Response<T> extends ResourceSupport {

    private T content;

    public Response(T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
