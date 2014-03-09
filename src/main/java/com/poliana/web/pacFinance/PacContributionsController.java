package com.poliana.web.pacFinance;

import com.poliana.core.pacFinance.PacContributionService;
import com.poliana.core.pacFinance.entities.PacContributionTotalsMap;
import com.poliana.web.common.AbstractBaseController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static com.poliana.core.time.TimeService.CURRENT_CONGRESS;

/**
 * @author David Gilmore
 * @date 1/4/14
 */
@Controller
@RequestMapping(value = "/pacs", method = RequestMethod.GET)
public class PacContributionsController extends AbstractBaseController {

    private PacContributionService pacContributionService;

    private static final Logger logger = Logger.getLogger(PacContributionsController.class);


    /**
     * Get pac contributions by congressional session. If no congress is provided, it will default to the current congress
     * @param pacId
     * @param congress
     * @return
     */
    @RequestMapping(value = "/{pac_id}/contributions", produces = "application/json", headers = "x-requested-with=XMLHttpRequest")
    public @ResponseBody PacContributionTotalsMap getPacContributionsByCongress(
            @PathVariable(value = "pac_id") String pacId,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress) {

            return pacContributionService.getPacContributionTotalsMap(pacId, congress);
    }

    /**
     * Get pac contributions by chamber and congressional session.
     * @param pacId
     * @param chamber
     * @param congress
     * @return
     */
    @RequestMapping(value = "/{pac_id}/contributions", params = {"chamber", "congress"}, produces = "application/json", headers = "x-requested-with=XMLHttpRequest")
    public @ResponseBody PacContributionTotalsMap getPacContributionsByCongressAndChamber(
            @PathVariable(value = "pac_id") String pacId,
            @RequestParam(value = "chamber", required = true) String chamber,
            @RequestParam(value = "congress", required = true) Integer congress) {

        return pacContributionService.getPacContributionTotalsMap(pacId, chamber, congress);
    }

    /**
     * Get pac contributions by time range.
     * @param pacId
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "/{pac_id}/contributions", params = {"start", "end"}, produces = "application/json", headers = "x-requested-with=XMLHttpRequest")
    public @ResponseBody PacContributionTotalsMap getPacContributionsByStartAndEnd(
            @PathVariable(value = "pac_id") String pacId,
            @RequestParam(value = "start", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date start,
            @RequestParam(value = "end", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date end) {

        return pacContributionService.getPacContributionTotalsMap(pacId, start.getTime()/1000, end.getTime()/1000);
    }

    /**
     * Get pac contributions by chamber and time range.
     * @param pacId
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "/{pac_id}/contributions", params = {"chamber", "start", "end"}, produces = "application/json", headers = "x-requested-with=XMLHttpRequest")
    public @ResponseBody PacContributionTotalsMap getPacContributionsByChamberAndStartAndEnd(
            @PathVariable(value = "pac_id") String pacId,
            @RequestParam(value = "chamber", required = true) String chamber,
            @RequestParam(value = "start", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date start,
            @RequestParam(value = "end", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date end) {

        return pacContributionService.getPacContributionTotalsMap(pacId, chamber, start.getTime()/1000, end.getTime()/1000);
    }

    @Autowired
    public void setPacContributionService(PacContributionService pacContributionService) {
        this.pacContributionService = pacContributionService;
    }

}
