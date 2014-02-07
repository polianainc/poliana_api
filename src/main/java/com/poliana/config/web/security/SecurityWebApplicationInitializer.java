package com.poliana.config.web.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
* @author David Gilmore
* @date 2/3/14
*/
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebApplicationInitializer() {
        super(MultiSecurityConfig.class);
    }
}