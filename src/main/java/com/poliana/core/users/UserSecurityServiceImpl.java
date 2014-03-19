package com.poliana.core.users;

import com.poliana.web.error.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author David Gilmore
 * @date 3/18/14
 */
@Service
public class UserSecurityServiceImpl implements UserSecurityService {

    @Autowired
    private UserSecurityRepository userSecurityRepository;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetails userDetails = userSecurityRepository.getUserByUsername(s);
        if(userDetails == null) {
            throw new UsernameNotFoundException("User not found using supplied username");
        }

        return userDetails;
    }


    @Override
    public UserDetails getUserByApiKey(String apiKey) {
        UserDetails userDetails = userSecurityRepository.getUserByApiKey(apiKey);
        if(userDetails == null){
            throw new ResourceNotFoundException("User could not be found with the supplied api key.");
        }

        return userDetails;
    }
}