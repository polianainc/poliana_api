package com.poliana.shell.controllers;

import com.poliana.core.industryFinance.IndustryContributionService;
import com.poliana.core.industryFinance.entities.IndustryPoliticianContributions;
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
import java.util.List;

/**
 * @author David Gilmore
 * @date 11/21/13
 */
@Controller
public class IndustryFinanceController implements CommandMarker {

    @Autowired
    private IndustryContributionService industryContributionService;

    @CliCommand(value = "indContrOverview")
    public String actions(
            @CliOption(key = { "id" }, mandatory = true) final String industryId,
            @CliOption(key = { "congress" }, mandatory = true) final int congress,
            @CliOption(key = { "range" }, mandatory = true) final int numSeries) throws IOException {

        JSONObject jsonIndustryTotals =  new JSONObject(
                industryContributionService.industryTimeRangeTotals(industryId, congress, numSeries));
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
    public String polRecIndTotals(
            @CliOption(key = {"id"}, mandatory = true) final String bioguideId,
            @CliOption(key = {"congress"}, mandatory = true) final int congress) {
        JSONArray jsonArray = new JSONArray();
        List<IndustryPoliticianContributions> industies =
                industryContributionService.legislatorReceivedIndustryTotals(bioguideId,congress);
        for (IndustryPoliticianContributions industry: industies) {
            JSONObject jsonIndustry = new JSONObject(industry);
            jsonArray.put(jsonIndustry);
        }

        String path = "../data/" + bioguideId + "_" + congress + "_" + "_industry_contributions.json";
        String message;
        try {

            FileWriter file = new FileWriter(path);
            jsonArray.write(file);
            file.flush();
            file.close();
            message = "Successfully saved industry contributions for " + bioguideId + " to " + path;
        } catch (IOException | JSONException e) {
            message = "There was an error processing the industry contributions for " + bioguideId;
        }

        return message;
    }


}
