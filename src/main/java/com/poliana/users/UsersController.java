package com.poliana.users;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author David Gilmore
 * @date 3/31/14
 */
@Controller
@RequestMapping("/user")
public class UsersController {

    private UserSecurityService userSecurityService;

    private static final Logger logger = Logger.getLogger(AdminController.class);


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
    @RequestMapping(value = "/key", params = {"username", "password"}, method = RequestMethod.GET)
    public @ResponseBody String getKey(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password) {

        return userSecurityService.getApiKeyByUsernameAndPassword(username, password);
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
