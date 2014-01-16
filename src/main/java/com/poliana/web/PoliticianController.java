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
 *
 * /politician

/contributions
/organizations
?politician_id=XXXXXX *
?organization_id=XXXXX

/industries
?politician_id=XXXXXX *
?industry_id=XXX
?category_id=XXXXX

/pacs
?politician_id=XXXXXX *
?pac_id=XXXX

/expenditures

/committees

/votes

/sponsorships
 */

@Controller
@RequestMapping("/politician/")
public class PoliticianController extends AbstractBaseController {

    private static final Logger logger = Logger.getLogger(PoliticianController.class);


    /**
     * Grabs politician data for a given bioguide and congresssional cycle.
     * @param bioguideId
     * @param congress
     * @return
     */
    @RequestMapping(value="/{bioguide_id}/", params={"congress, fields"}, method = RequestMethod.GET)
    public @ResponseBody String getPolitician (
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
            @RequestParam(value = "fields", required = false, defaultValue = "") String fields){


        return "politician whose bioguide is " + bioguideId + ", in the congress " + congress + "with fields " + fields;
    }

    /**
     * Grabs politician data for a given bioguide and congresssional cycle.
     * @param bioguideId
     * @param congress
     * @return
     */
    @RequestMapping(value="/{bioguide_id}/", params={"congress, fields"}, method = RequestMethod.GET)
    public @ResponseBody String getPolitician (
           @PathVariable("bioguide_id") String bioguideId,
           @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
           @RequestParam(value = "fields", required = false, defaultValue = "") String fields){


        return "politician whose bioguide is " + bioguideId + ", in the congress " + congress + "with fields " + fields;
    }

    /**
     * Grabs vote data for a politician identified by the given bioguide and congresssional cycle.
     * @param bioguideId
     * @param congress
     * @return
     */
    @RequestMapping(value="/{bioguide_id}/votes/", params={"congress"}, method = RequestMethod.GET)
    public @ResponseBody String getPoliticianVotes (
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress) {


        return "votes for politician whose bioguide is " + bioguideId + ", in the congress " + congress;
    }
}
