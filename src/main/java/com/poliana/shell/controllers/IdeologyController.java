package com.poliana.shell.controllers;

import com.poliana.core.ideology.IdeologyService;
import com.poliana.core.ideology.IdeologyMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Controller;
import com.google.gson.Gson;


@Controller
public class IdeologyController implements CommandMarker {

    @Autowired
    private IdeologyService ideologyService;


    /**
     * Saves a file to ../data/{chamber}_{congress}_ideology.json with an IdeologyMatrix json
     * object.
     *
     * Example query: ideologyMatrix --chamber s --congress 110
     *
     * @param chamber
     * @param congress
     * @return
     */
    @CliCommand(value = "ideologyMatrix")
    public String ideologyMatrix(
            @CliOption(key = { "chamber" }) final String chamber,
            @CliOption(key = { "congress" }) final int congress) {

        IdeologyMatrix ideologyMatrix = ideologyService.getIdeologyMatrix(chamber, congress);
        Gson gson = new Gson();
        return gson.toJson(ideologyMatrix);
    }
}
