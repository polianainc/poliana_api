package com.poliana.config.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author David Gilmore
 * @date 2/3/14
 */
@Component(value = "authenticationProvider")
public class ApplicationAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        return null;
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }
}