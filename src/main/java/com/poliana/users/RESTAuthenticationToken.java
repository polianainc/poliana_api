package com.poliana.users;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author David Gilmore
 * @date 3/18/14
 */
public class RESTAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public RESTAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public RESTAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
