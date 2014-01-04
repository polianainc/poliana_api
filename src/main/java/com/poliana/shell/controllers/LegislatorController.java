package com.poliana.shell.controllers;

import com.poliana.core.legislators.Legislator;
import com.poliana.core.legislators.LegislatorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Controller;
import com.google.gson.Gson;

import java.util.List;

/**
 * @author David Gilmore
 * @date 12/26/13
 */
@Controller
public class LegislatorController implements CommandMarker {

    @Autowired
    private LegislatorRepo legislatorRepo;

    @CliCommand(value = "populateLegislatorsToMongo")
    public void populateLegislatorsToMongo() {
        legislatorRepo.saveLegislatorsToMongo();
    }

    @CliCommand(value = "populateLegislatorsToRedis")
    public void populateLegislatorsToRedis() {
        legislatorRepo.loadLegislatorTermsToRedis();
    }

    @CliCommand(value = "getLegislatorTermsByBioguideId")
    public String getLegislatorTermsByBioguideId(
            @CliOption(key = { "id" }, mandatory = true) final String bioguideId) {
        List<Legislator> legislators = legislatorRepo.getLegislatorTermsByBioguide(bioguideId);
        Gson gson = new Gson();
        return gson.toJson(legislators);
    }
}
