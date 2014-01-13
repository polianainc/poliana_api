package com.poliana.web;

import com.poliana.core.politicianFinance.PoliticianFinanceService;
import com.poliana.core.politicianFinance.entities.IndustryPoliticianContributions;
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
import java.util.HashMap;
import java.util.List;

import static com.poliana.core.time.TimeService.CURRENT_CONGRESS;

/**
 * @author David Gilmore
 * @date 1/6/14
 */
@Controller
@RequestMapping("/politician/")
public class PoliticianFinanceController extends AbstractBaseController {

    private PoliticianFinanceService politicianFinanceService;
    private LegislatorService legislatorService;

    private TimeService timeService;

    private static final Logger logger = Logger.getLogger(PoliticianFinanceController.class);


    public PoliticianFinanceController() {
        this.timeService = new TimeService();
    }

    /**
     * Get all industry contribution totals for a given congressional cycle. The default congress cycle value is
     * the current congress.
     * @param bioguideId
     * @param congress
     * @return
     */
    @RequestMapping(value="/{bioguide_id}/contributions/industries", params = {"congress"}, method = RequestMethod.GET)
    public @ResponseBody String getIndustryToPoliticianTotals (
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress) {

        List<IndustryPoliticianContributions> allTotals = politicianFinanceService.getIndustryToPoliticianTotals(bioguideId, congress);
        return this.gson.toJson(allTotals);
    }

    /**
     *
     * @param bioguideId
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "/{bioguide_id}/contributions/industries", params = {"start", "end"}, method = RequestMethod.GET)
    public @ResponseBody String getIndustryToPoliticianTotals(
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "start", required = true) @DateTimeFormat(pattern = "mm-dd-yyyy") Date start,
            @RequestParam(value = "end", required = true) @DateTimeFormat(pattern = "mm-dd-yyyy") Date end) {

        List<IndustryPoliticianContributions> totals =
                politicianFinanceService.getIndustryToPoliticianTotals(bioguideId, start.getTime(), end.getTime());

        return this.gson.toJson(totals);
    }

    /**
     * Plot all industry contribution totals for a given congressional cycle. The default congress cycle value is
     * the current congress.
     * @param bioguideId
     * @param congress
     * @return
     */
    @RequestMapping(value="/{bioguide_id}/contributions/industries", params = {"congress", "plot"}, method = RequestMethod.GET)
    public void plotIndustryToPoliticianTotals (
            OutputStream stream,
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
            @RequestParam(value = "plot", required = true) String plotType) {

        List<IndustryPoliticianContributions> allTotals = politicianFinanceService.getIndustryToPoliticianTotals(bioguideId, congress);

        Legislator legislator;
        try {
            legislator = legislatorService.getLegislatorTermsById(bioguideId).get(0);
        }
        catch (Exception e) {
            legislator = new Legislator();
        }

        PoliticianContributionView view = new PoliticianContributionView(allTotals, legislator, congress);

        JFreeChart chart = view.generateChart(plotType);

        try {
            ChartUtilities.writeChartAsPNG(stream, chart, 1600, 1000);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @RequestMapping(value = "/{bioguide_id}/contributions/industries", method = RequestMethod.GET)
    public @ResponseBody String getAllIndustryContributions(
            @PathVariable("bioguide_id") String bioguideId) {

        HashMap<Integer, List<IndustryPoliticianContributions>> contributionsMap =
                politicianFinanceService.getIndustryTotalsAllTime(bioguideId);

        return this.gson.toJson(contributionsMap);
    }

    @Autowired
    public void setPoliticianFinanceService(PoliticianFinanceService politicianFinanceService) {
        this.politicianFinanceService = politicianFinanceService;
    }

    @Autowired
    public void setLegislatorService(LegislatorService legislatorService) {
        this.legislatorService = legislatorService;
    }
}
