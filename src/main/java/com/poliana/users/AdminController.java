package com.poliana.users;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author David Gilmore
 * @date 3/27/14
 */
@Controller
@RequestMapping("/admin")

public class AdminController {

    private UserSecurityService userSecurityService;

    private static final Logger logger = Logger.getLogger(AdminController.class);


    /**
     * Render the AJAX & HTML for managing user accounts and API keys
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getUsersIndex() {

        return "admin";
    }

    /**
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody UserDetails createUser(HttpServletRequest request) {

        Map<String, String[]> params = request.getParameterMap();

        String username = params.get("username")[0];
        String password = params.get("password")[0];
        String firstName = params.get("firstName")[0];
        String lastName = params.get("lastName")[0];

        Integer role = Integer.parseInt(params.get("role")[0]);

        if (role == 2)
            return userSecurityService.createUser(username, password, firstName, lastName, "USER", "RESEARCH");

        return userSecurityService.createUser(username, password, firstName, lastName);
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
