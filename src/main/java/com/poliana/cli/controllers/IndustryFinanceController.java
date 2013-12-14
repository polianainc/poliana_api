package com.poliana.cli.controllers;

import com.poliana.core.campaignFinance.services.ContributionService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author David Gilmore
 * @date 11/21/13
 */
@Controller
public class IndustryFinanceController implements CommandMarker {

    @Autowired
    private ContributionService contributionService;

    @CliCommand(value = "indContrOverview")
    public String actions(
            @CliOption(key = { "id" }, mandatory = true) final String industryId,
            @CliOption(key = { "congress" }, mandatory = true) final int congress,
            @CliOption(key = { "range" }, mandatory = true) final int numSeries) throws IOException {

        JSONObject jsonIndustryTotals =  new JSONObject(
                contributionService.industryTimeRangeTotals(industryId, congress, numSeries));
        return jsonIndustryTotals.toString();
    }

    /**
     * For a given congress, sums all industry contributions made to the given legislator.
     * Saves a json map to the path ../data/{id}_{congress}_industry_receipts_totals.json
     *
     * Example query: polRecIndTotals --id O000167 --congress 112
     *
     * @param bioguideId
     * @param congress
     * @return
     */
    @CliCommand(value = "polRecIndTotals")
    public HashMap<String,Integer> polRecIndTotals(
            @CliOption(key = { "id"}, mandatory = true) final String bioguideId,
            @CliOption(key = { "congress"}, mandatory = true) final int congress) {
        return contributionService.legislatorReceivedIndustryTotals(bioguideId,congress);
    }


}
