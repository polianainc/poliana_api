package com.poliana.shell.controllers;

import com.poliana.core.industryFinance.services.IndustryPartyAndGeoService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * @author David Gilmore
 * @date 11/21/13
 */
@Controller
public class IndustryFinanceController implements CommandMarker {

    @Autowired
    private IndustryPartyAndGeoService industryPartyAndGeoService;

    @CliCommand(value = "indContrOverview")
    public String actions(
            @CliOption(key = { "id" }, mandatory = true) final String industryId,
            @CliOption(key = { "congress" }, mandatory = true) final int congress,
            @CliOption(key = { "range" }, mandatory = true) final int numSeries) throws IOException {

        JSONObject jsonIndustryTotals =  new JSONObject(
                industryPartyAndGeoService.getIndustryPartyAndGeoTotals(industryId, congress, numSeries));
        return jsonIndustryTotals.toString();
    }
}
