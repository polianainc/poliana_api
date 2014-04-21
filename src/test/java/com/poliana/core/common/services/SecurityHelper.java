package com.poliana.core.common.services;

import com.poliana.users.RESTAuthenticationToken;
import com.poliana.users.RESTAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.LinkedList;
import java.util.List;

/**
 * @author David Gilmore
 * @date 4/15/14
 */
public class SecurityHelper {

    public static void login() {

        GrantedAuthority authority = new RESTAuthority("RESEARCH");
        List<GrantedAuthority> authorities = new LinkedList<>();
        authorities.add(authority);

        SecurityContextHolder.getContext().setAuthentication(new RESTAuthenticationToken(authorities));
    }

    public static void logout() {
        SecurityContextHolder.clearContext();
    }
}
