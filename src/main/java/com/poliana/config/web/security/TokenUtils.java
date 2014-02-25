package com.poliana.config.web.security;

import org.springframework.security.core.userdetails.UserDetails;

/**
* @author David Gilmore
* @date 2/7/14
*/
public interface TokenUtils {
    String getToken(UserDetails userDetails);
    String getToken(UserDetails userDetails, Long expiration);
    boolean validate(String token);
    UserDetails getUserFromToken(String token);
}