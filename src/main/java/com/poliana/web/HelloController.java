package com.poliana.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Hello World! From the Poliana team
 *
 * @author David Gilmore
 * @date 12/15/13
 */
@Controller
@RequestMapping("/")
public class HelloController extends AbstractBaseController {

    @ResponseBody
    @RequestMapping(value="", method = RequestMethod.GET)
    public String index() {
        return "Hello. Welcome to the Poliana API!";
    }

}