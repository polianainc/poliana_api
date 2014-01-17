package com.poliana.web;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @ResponseBody
    @RequestMapping(value = "", params={"congress, fields"}, method = RequestMethod.GET)
    public String getRoot (
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
    @ResponseBody
    @RequestMapping(value = "{bioguide_id}", params = {"congress, fields"}, method = RequestMethod.GET)
    public String getPolitician (
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
    @ResponseBody
    @RequestMapping(value = "{bioguide_id}/votes", params = {"congress, fields"}, method = RequestMethod.GET)
    public String getVotes (
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
    @ResponseBody
    @RequestMapping(value="{bioguide_id}/expenditures", params = {"congress, fields"}, method = RequestMethod.GET)
    public String getExpenditures (
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
    @ResponseBody
    @RequestMapping(value="{bioguide_id}/sponsorship", params = {"congress, fields"}, method = RequestMethod.GET)
    public String getSponsorship(
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
            @RequestParam(value = "fields", required = false, defaultValue = "") String fields) {


        return "sponsorship for politician whose bioguide is " + bioguideId + ", in the congress " + congress;
    }
}
