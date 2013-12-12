package com.poliana.cli.controllers;

import com.poliana.core.legislation.models.IdeologyMatrix;
import com.poliana.core.legislation.services.IdeologyAnalysis;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Controller;

@Controller
public class BillsController implements CommandMarker {

    @Autowired
    private IdeologyAnalysis ideologyAnalysis;

    @CliCommand(value = "ideologyMatrix")
    public JSONObject ideologyMatrix(
            @CliOption(key = { "chamber" }) final String chamber,
            @CliOption(key = { "beginTimestamp" }) final int beginTimestamp,
            @CliOption(key = { "endTimestamp" }) final int endTimestamp) throws JSONException {

        IdeologyMatrix ideologyMatrix = ideologyAnalysis.getIdeologyMatrix(chamber,beginTimestamp,endTimestamp);

        JSONObject ideology = new JSONObject();
        ideology.put("chamber",ideologyMatrix.getChamber());
        ideology.put("beginTimestamp",ideologyMatrix.getBeginDate());
        ideology.put("endTimestamp",ideologyMatrix.getEndDate());
        ideology.put("sponsorshipMatrix",ideologyMatrix.getSponsorshipMatrix());
        ideology.put("indexMap",ideologyMatrix.getIndices());

        return ideology;
    }
}
