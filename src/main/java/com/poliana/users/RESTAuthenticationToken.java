package com.poliana.users;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author David Gilmore
 * @date 3/18/14
 */
public class RESTAuthenticationToken extends AbstractAuthenticationToken {

    private String credentials;
    private String principal;

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the
     *                    principal represented by this authentication object.
     */
    public RESTAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param apikey
     * @param id
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the
     *                    principal represented by this authentication object.
     */
    public RESTAuthenticationToken(String apikey, String id, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.credentials = apikey;
        this.principal = id;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }


}
