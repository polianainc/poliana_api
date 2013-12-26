package com.poliana.cli.controllers;

import com.poliana.core.legislators.Legislator;
import com.poliana.core.ideology.IdeologyMatrix;
import com.poliana.core.ideology.IdeologyAnalysis;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Controller;

import java.io.FileWriter;
import java.io.IOException;

@Controller
public class BillsController implements CommandMarker {

    @Autowired
    private IdeologyAnalysis ideologyAnalysis;


    /**
     * Saves a file to ../data/{chamber}_{beginTimestamp}_{endTimestamp}_ideology.json with an IdeologyMatrix json
     * object.
     *
     * Example query: ideologyMatrix --chamber s --beginTimestamp 1086484277 --endTimestamp 1286484277
     *
     * @param chamber
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     * @throws JSONException
     */
    @CliCommand(value = "ideologyMatrix")
    public String ideologyMatrix(
            @CliOption(key = { "chamber" }) final String chamber,
            @CliOption(key = { "beginTimestamp" }) final int beginTimestamp,
            @CliOption(key = { "endTimestamp" }) final int endTimestamp) throws JSONException {

        IdeologyMatrix ideologyMatrix = ideologyAnalysis.getIdeologyMatrix(chamber,beginTimestamp,endTimestamp);

        JSONObject ideology = new JSONObject();
        ideology.put("chamber",ideologyMatrix.getChamber());
        ideology.put("beginTimestamp",ideologyMatrix.getBeginDate());
        ideology.put("endTimestamp",ideologyMatrix.getEndDate());
        ideology.put("sponsorshipMatrix",ideologyMatrix.getSponsorshipMatrix());
        ideology.put("u",ideologyMatrix.getU());
        ideology.put("vt",ideologyMatrix.getVt());
        ideology.put("idToIndex",ideologyMatrix.getIdToIndex());

        JSONArray legislators = new JSONArray();
        for (Legislator legislator: ideologyMatrix.getLegislators()) {
            legislators.put(new JSONObject(legislator));
        }
        ideology.put("legislators",legislators);

        String path = "../data/" + chamber + "_" + beginTimestamp + "_" + endTimestamp + "_ideology.json";
        String message;
        try {

            FileWriter file = new FileWriter(path);
            ideology.write(file);
            file.flush();
            file.close();
            message = "Successfully saved ideology analysis to " + path;
        } catch (IOException e) {
            message = "There was an error processing the ideology analysis";
        }

        return message;
    }
}
