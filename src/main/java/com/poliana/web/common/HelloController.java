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
    public String getEndPointsInView( Model model ) {

        model.addAttribute("message", "Hello, and welcome to the Poliana API");
        model.addAttribute( "endPoints", requestMappingHandlerMapping.getHandlerMethods().keySet() );
        return "endPoints";
    }

    @Autowired
    public void setRequestMappingHandlerMapping(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }
}