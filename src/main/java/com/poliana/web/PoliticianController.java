package com.poliana.web;

import com.poliana.core.politicianFinance.industries.PoliticianIndustryFinanceService;
import com.poliana.core.politicianFinance.industries.IndustryPoliticianContributionTotals;
import com.poliana.core.legislators.Legislator;
import com.poliana.core.legislators.LegislatorService;
import com.poliana.core.time.TimeService;
import com.poliana.views.PoliticianContributionView;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import static com.poliana.core.time.TimeService.CURRENT_CONGRESS;
/**
 * @author Grayson Carroll
 * @date 1/15/14
 */

@Controller
@RequestMapping("/politicians/")
public class PoliticianController extends AbstractBaseController {

    private static final Logger logger = Logger.getLogger(PoliticianController.class);


    /**
     * Politician root
     * @param congress
     * @param fields
     * @return
     */
    @RequestMapping(value="", params={"congress, fields"}, method = RequestMethod.GET)
    public @ResponseBody String getRoot (
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
            @RequestParam(value = "fields", required = false, defaultValue = "") String fields){


        return "politician root, congress " + congress + "with fields " + fields;
    }

    /**
     * Grabs politician data for a given bioguide and congresssional cycle.
     * @param bioguideId
     * @param congress
     * @param fields
     * @return
     */
    @RequestMapping(value="/{bioguide_id}/", params={"congress, fields"}, method = RequestMethod.GET)
    public @ResponseBody String getPolitician (
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
            @RequestParam(value = "fields", required = false, defaultValue = "") String fields) {


        return "politician whose bioguide is " + bioguideId + ", in the congress " + congress + "with fields " + fields;
    }

    /**
     * Grabs vote data for a politician identified by the given bioguide and congresssional cycle.
     * @param bioguideId
     * @param congress
     * @return
     */
    @RequestMapping(value="/{bioguide_id}/votes/", params={"congress, fields"}, method = RequestMethod.GET)
    public @ResponseBody String getVotes (
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
            @RequestParam(value = "fields", required = false, defaultValue = "") String fields) {


        return "votes for politician whose bioguide is " + bioguideId + ", in the congress " + congress;
    }

    /**
     * Grabs expenditure data for a politician identified by the given bioguide and congresssional cycle.
     * @param bioguideId
     * @param congress
     * @return
     */
    @RequestMapping(value="/{bioguide_id}/expenditures/", params={"congress, fields"}, method = RequestMethod.GET)
    public @ResponseBody String getExpenditures (
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
            @RequestParam(value = "fields", required = false, defaultValue = "") String fields) {


        return "expenditures for politician whose bioguide is " + bioguideId + ", in the congress " + congress;
    }
    /**
     * Grabs sponsorship data for a politician identified by the given bioguide and congresssional cycle.
     * @param bioguideId
     * @param congress
     * @return
     */
    @RequestMapping(value="/{bioguide_id}/sponsorship/", params={"congress, fields"}, method = RequestMethod.GET)
    public @ResponseBody String getSponsorship(
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
            @RequestParam(value = "fields", required = false, defaultValue = "") String fields) {


        return "sponsorship for politician whose bioguide is " + bioguideId + ", in the congress " + congress;
    }
}
