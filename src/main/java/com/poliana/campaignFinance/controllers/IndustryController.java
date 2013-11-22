package com.poliana.campaignFinance.controllers;

import com.poliana.campaignFinance.services.ContributionService;
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
public class IndustryController implements CommandMarker {

    @Autowired
    private ContributionService contributionService;

    @CliCommand(value = "indContrOverview", help = "Run a job")
    public String actions(
            @CliOption(key = { "id" }, mandatory = true) final String industryId,
            @CliOption(key = { "congress" }, mandatory = true) final int congress,
            @CliOption(key = { "range" }, mandatory = true) final int numSeries) throws IOException {

        JSONObject jsonIndustryTotals =  new JSONObject(
                contributionService.industryTimeRangeTotals(industryId, congress, numSeries));
        return jsonIndustryTotals.toString();
    }

}
