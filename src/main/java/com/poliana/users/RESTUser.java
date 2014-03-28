package com.poliana.users;

import org.mongodb.morphia.annotations.Entity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author David Gilmore
 * @date 3/18/14
 */
@Entity("user")
public class RESTUser extends User {

    private String apiKey;

    public RESTUser(String username, String password, String apiKey, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.apiKey = apiKey;
    }

    public RESTUser(String username, String password, String apiKey, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
