package com.poliana.web;

import com.poliana.core.ideology.IdeologyMatrix;
import com.poliana.core.ideology.IdeologyService;
import com.poliana.core.time.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 *
 * @author David Gilmore
 * @date 12/15/13
 */
@Controller
@RequestMapping("/ideology")
public class IdeologyController extends AbstractBaseController {

    private IdeologyService ideologyService;


    @RequestMapping(value="/{chamber}/{congress}", headers="Accept=*/*", method = RequestMethod.GET)
    public @ResponseBody String getIdeologyMatrix(
            @PathVariable("chamber") String chamber,
            @PathVariable("congress") Integer congress) {

        IdeologyMatrix ideologyMatrix = ideologyService.getIdeologyMatrix(chamber,congress);
        return this.gson.toJson(ideologyMatrix);
    }

    @Autowired
    public void setIdeologyService(IdeologyService ideologyService) {
        this.ideologyService = ideologyService;
    }
}
