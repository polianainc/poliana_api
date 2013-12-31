package com.poliana.web;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.poliana.core.ideology.IdeologyMatrix;
import com.poliana.core.ideology.LegislatorIdeology;
import com.poliana.core.ideology.IdeologyService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *
 * @author David Gilmore
 * @date 12/15/13
 */
@Controller
@RequestMapping("/ideology")
public class IdeologyController extends BaseController  {

    @Autowired
    private IdeologyService ideologyService;

    private Gson gson;

    public IdeologyController() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @RequestMapping(value="/{chamber}/{congress}", headers="Accept=*/*", method = RequestMethod.GET)
    public @ResponseBody String ideology(
            @PathVariable("chamber") String chamber,
            @PathVariable("congress") Integer congress) {

        IdeologyMatrix ideologyMatrix = ideologyService.getIdeologyMatrix(chamber,congress);
        return gson.toJson(ideologyMatrix);
    }
}
