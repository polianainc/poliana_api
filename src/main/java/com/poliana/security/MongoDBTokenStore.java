package com.poliana.security;

import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author David Gilmore
 * @date 2/1/14
 */
@Component
public class MongoDBTokenStore implements TokenStore {

    private Datastore mongoStore;

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return null;
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        return null;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {

    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        return null;
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {

    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {

    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        return null;
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return null;
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByUserName(String userName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Autowired
    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }
}
