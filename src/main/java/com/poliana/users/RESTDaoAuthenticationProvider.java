package com.poliana.users;

import com.poliana.web.error.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author David Gilmore
 * @date 3/18/14
 */
@Component
public class RESTDaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {


    private UserSecurityService userSecurityService;

    @Override
    public Authentication authenticate(Authentication authentication) {

        String apikey = (String) authentication.getCredentials();

        UserDetails user = userSecurityService.getUserByApiKey(apikey);

        if (user == null)
            throw new ForbiddenException("No user found with the given API key");

        return new RESTAuthenticationToken(apikey, user.getUsername(), user.getAuthorities());
    }


    /**
     * This is the method which actually performs the check to see whether the user is indeed the correct user
     * @param userDetails
     * @param authentication
     * @throws org.springframework.security.core.AuthenticationException
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {}

    /**
     *
     * @param apiKey This is the API Key that was generated as a UUID in the UserSecurityService
     *
     * @param authentication The authentication request, which subclasses <em>may</em> need to perform a binding-based
     *        retrieval of the <code>UserDetails</code>
     *
     * @return the user information (never <code>null</code> - instead an exception should the thrown)
     *
     * @throws AuthenticationException if the credentials could not be validated (generally a
     *         <code>BadCredentialsException</code>, an <code>AuthenticationServiceException</code> or
     *         <code>UsernameNotFoundException</code>)
     */
    @Override
    protected UserDetails retrieveUser(String apiKey, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        UserDetails loadedUser;

        try {
            loadedUser = this.getUserSecurityService().getUserByApiKey(apiKey);
        } catch (UsernameNotFoundException notFound) {
            throw notFound;
        } catch (Exception repositoryProblem) {
            throw new AuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
        }

        if (loadedUser == null) {
            throw new AuthenticationServiceException(
                    "UserSecurityServiceImpl returned null, which is an interface contract violation");
        }
        return loadedUser;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    @Override
    protected void doAfterPropertiesSet() throws Exception {
        Assert.notNull(this.userSecurityService, "A UserSecurityServiceImpl must be set");
    }

    public UserSecurityService getUserSecurityService() {
        return userSecurityService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {
        this.userSecurityService = userSecurityService;
    }
}
