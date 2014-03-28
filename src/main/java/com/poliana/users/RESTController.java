package com.poliana.users;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author David Gilmore
 * @date 3/27/14
 */
@Controller
@RequestMapping("/users")
public class RESTController {

    private UserSecurityService userSecurityService;

    private static final Logger logger = Logger.getLogger(RESTController.class);


    /**
     * Render the AJAX & HTML for managing user accounts and API keys
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getUsersIndex() {

        return "users";
    }

    /**
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/key", method = RequestMethod.GET)
    public String getUser(@RequestBody RESTUser user) {

        return "";
    }

    /**
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createUser(@RequestBody RESTUser user) {


        return "";
    }

    /**
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public String updateUser(@RequestBody RESTUser user) {


        return "";
    }

    /**
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String deleteUser(@RequestBody RESTUser user){


        return "";
    }


    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {
        this.userSecurityService = userSecurityService;
    }
}
