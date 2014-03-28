package com.poliana.users;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author David Gilmore
 * @date 3/27/14
 */
@Controller
@RequestMapping("/users/")
public class RESTController {

    private UserSecurityService userSecurityService;

    private static final Logger logger = Logger.getLogger(RESTController.class);


    /**
     * @return
     */
    @ResponseBody
    @RequestMapping("/")
    public String getUsersIndex(){

        return "";
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {
        this.userSecurityService = userSecurityService;
    }
}
