package com.poliana.web.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Hello World! From the Poliana team
 *
 * @author David Gilmore
 * @date 12/15/13
 */
@Controller
@RequestMapping("/")
public class HelloController extends AbstractBaseController {

    private RequestMappingHandlerMapping requestMappingHandlerMapping;


    @RequestMapping( value = "", method = RequestMethod.GET )
    public String helloWorld( Model model ) {

        model.addAttribute("message", "Hello World!");

        return "hello";
    }

    @RequestMapping( value = "endpoints", method = RequestMethod.GET )
    public String getEndPointsInView( Model model ) {

        model.addAttribute("message", "Check out our endpoints");
        model.addAttribute( "endpoints", requestMappingHandlerMapping.getHandlerMethods().keySet() );

        return "endpoints";
    }

    @Autowired
    public void setRequestMappingHandlerMapping(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }
}