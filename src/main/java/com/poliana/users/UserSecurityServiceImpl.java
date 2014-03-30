package com.poliana.users;

import com.poliana.web.error.ForbiddenException;
import com.poliana.web.error.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @author David Gilmore
 * @date 3/18/14
 */
@Service
public class UserSecurityServiceImpl implements UserSecurityService {

    private UserSecurityRepository userSecurityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails userDetails = userSecurityRepository.getUserByUsername(username);

        if(userDetails == null) {
            throw new UsernameNotFoundException("User not found using supplied username");
        }

        return userDetails;
    }

    public String getApiKeyByUsernameAndPassword(String username, String password) {

        RESTUser user = (RESTUser) userSecurityRepository.getUserByUsername(username);

        if (passwordMatches(password, user.getPassword()))
            return user.getApiKey();
        else
            throw new ForbiddenException("Wrong password");
    }

    public String getApiKey() {
        return UUID.randomUUID().toString();
    }

    @Override
    public UserDetails getUserByApiKey(String apiKey) {

        UserDetails userDetails = userSecurityRepository.getUserByApiKey(apiKey);
        if(userDetails == null){
            throw new ResourceNotFoundException("User could not be found with the supplied api key.");
        }

        return userDetails;
    }

    @Override
    public UserDetails createUser(String username, String password, String firstName, String lastName) {

        List<GrantedAuthority> authorities = new LinkedList<>();

        authorities.add(new RESTAuthority("user"));

        RESTUser user = new RESTUser(username, encodePassword(password), getApiKey(), authorities);

        user.setFirstName(firstName);
        user.setLastName(lastName);

        userSecurityRepository.createUser(user);

        return user;
    }

    private String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    private boolean passwordMatches(String password, String encodedPassword) {
        return new BCryptPasswordEncoder().matches(password, encodedPassword);
    }

    @Autowired
    public void setUserSecurityRepository(UserSecurityRepository userSecurityRepository) {
        this.userSecurityRepository = userSecurityRepository;
    }
}