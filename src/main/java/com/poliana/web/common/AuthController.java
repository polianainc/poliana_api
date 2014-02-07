package com.poliana.web.common;

import com.poliana.config.web.security.JsonRestObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author David Gilmore
 * @date 2/7/14
 */
@Controller
public class AuthController {

    @RequestMapping(value = "/json/{id}", method = RequestMethod.GET)
    public @ResponseBody JsonRestObject getJsonRestObject(@PathVariable Integer id) {
        return new JsonRestObject(id);
    }
}
