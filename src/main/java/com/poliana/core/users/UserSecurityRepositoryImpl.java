package com.poliana.core.users;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @author David Gilmore
 * @date 3/18/14
 */
@Repository
public class UserSecurityRepositoryImpl implements UserSecurityRepository {

    private RESTUser simulateFetchOfuser() {
        return new RESTUser("username", "password", "api-1234", new ArrayList<GrantedAuthority>());
    }

    @Override
    public UserDetails getUserByUsername(String username) {
        RESTUser exampleUser = simulateFetchOfuser();

        if(username.equalsIgnoreCase(exampleUser.getUsername())){
            return exampleUser;
        }
        return null;
    }

    @Override
    public UserDetails getUserByApiKey(String apiKey) {
        RESTUser exampleUser = simulateFetchOfuser();
        if(apiKey.equals(exampleUser.getApiKey())){
            return exampleUser;
        }
        return null;
    }
}
