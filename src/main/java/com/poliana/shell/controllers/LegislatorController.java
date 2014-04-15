package com.poliana.shell.controllers;

import com.google.gson.Gson;
import com.poliana.core.legislators.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @author David Gilmore
 * @date 12/26/13
 */
@Controller
public class LegislatorController implements CommandMarker {

    private LegislatorService legislatorService;

    @CliCommand(value = "populateLegislatorsToMongo")
    public void populateLegislatorsToMongo() {
        legislatorService.loadLegislatorsFromHadoopToMongo();
    }

    @CliCommand(value = "populateLegislatorsToRedis")
    public void populateLegislatorsToRedis() {
        legislatorService.loadLegislatorTermsFromMongoToRedis();
    }

    @CliCommand(value = "getLegislatorTermsByBioguideId")
    public String getLegislatorTermsByBioguideId(
            @CliOption(key = { "id" }, mandatory = true) final String bioguideId) {

        List<Legislator> legislators = legislatorService.getLegislatorTermsById(bioguideId);
        Gson gson = new Gson();
        return gson.toJson(legislators);
    }

    @Autowired
    public void setLegislatorService(LegislatorService legislatorService) {
        this.legislatorService = legislatorService;
    }
}
