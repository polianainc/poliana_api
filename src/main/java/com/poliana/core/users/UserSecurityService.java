package com.poliana.core.users;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author David Gilmore
 * @date 3/18/14
 */
public interface UserSecurityService extends UserDetailsService {

    UserDetails getUserByApiKey(String apiKey);
}