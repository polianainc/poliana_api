package com.poliana.core.users;

import com.poliana.web.error.ForbiddenException;
import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * @author David Gilmore
 * @date 3/18/14
 */
//@Repository
public class UserSecurityRepositoryImpl implements UserSecurityRepository {

    private Datastore mongoStore;

    private static final Logger logger = Logger.getLogger(UserSecurityRepository.class);

    private RESTUser simulateFetchOfuser(String apiKey) {

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (apiKey.equals("api-1234")) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ADMIN");
            grantedAuthorities.add(grantedAuthority);
            return new RESTUser("username", "password", "api-1234", grantedAuthorities);
        }
        else if (apiKey.equals("admin-1234")) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");
            grantedAuthorities.add(grantedAuthority);
            return new RESTUser("username", "password", "admin-1234", grantedAuthorities);
        }

        else
            throw new ForbiddenException();
    }

    @Override
    public UserDetails getUserByUsername(String username) {

        RESTUser exampleUser = simulateFetchOfuser(username);

        if(username.equalsIgnoreCase(exampleUser.getUsername())){
            return exampleUser;
        }
        return null;
    }

    @Override
    public UserDetails getUserByApiKey(String apiKey) {

        RESTUser exampleUser = simulateFetchOfuser(apiKey);
        if(apiKey.equals(exampleUser.getApiKey())){
            return exampleUser;
        }
        return null;
    }

    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }
}
