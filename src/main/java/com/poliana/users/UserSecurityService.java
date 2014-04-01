package com.poliana.users;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author David Gilmore
 * @date 3/18/14
 */
public interface UserSecurityService extends UserDetailsService {

    UserDetails getUserByApiKey(String apiKey);
    String getApiKeyByUsernameAndPassword(String username, String password);
    UserDetails createUser(String username, String password, String firstName, String lastName);
    UserDetails createUser(String username, String password, String firstName, String lastName, String... roles);
}
