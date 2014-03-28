package com.poliana.users;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author David Gilmore
 * @date 3/18/14
 */
public interface UserSecurityRepository {

    UserDetails getUserByUsername(String username);
    UserDetails getUserByApiKey(String apiKey);
}
