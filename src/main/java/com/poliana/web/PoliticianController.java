package com.poliana.web;

import com.poliana.core.industryFinance.IndustryContributionService;
import com.poliana.core.industryFinance.entities.IndustryPoliticianContributions;
import com.poliana.core.legislators.Legislator;
import com.poliana.core.legislators.LegislatorService;
import com.poliana.core.time.TimeService;
import com.poliana.views.PoliticianContributionView;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static com.poliana.core.time.TimeService.CURRENT_CONGRESS;

/**
 * @author David Gilmore
 * @date 1/6/14
 */
@Controller
@RequestMapping("/politician/")
public class PoliticianController extends AbstractBaseController {

    private IndustryContributionService industryContributionService;
    private LegislatorService legislatorService;

    private TimeService timeService;

    private static final Logger logger = Logger.getLogger(IndustryContributionController.class);


    public PoliticianController() {
        this.timeService = new TimeService();
    }

    /**
     * Get all industry contribution totals for a given congressional cycle. The default congress cycle value is
     * the current congress.
     * @param bioguideId
     * @param congress
     * @return
     */
    @RequestMapping(value="/{bioguideId}/contributions/industries", params = {"congress"}, method = RequestMethod.GET)
    public @ResponseBody String getAllIndustryContributionsByCongress (
            @PathVariable("bioguideId") String bioguideId,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress) {

        List<IndustryPoliticianContributions> allTotals = industryContributionService.getIndustryToPoliticianTotals(bioguideId, congress);
        return this.gson.toJson(allTotals);
    }

    /**
     * Plot all industry contribution totals for a given congressional cycle. The default congress cycle value is
     * the current congress.
     * @param bioguideId
     * @param congress
     * @return
     */
    @RequestMapping(value="/{bioguideId}/contributions/industries", params = {"congress", "plot"}, method = RequestMethod.GET)
    public void plotAllIndustryContributionsByCongress (
            OutputStream stream,
            @PathVariable("bioguideId") String bioguideId,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
            @RequestParam(value = "plot", required = true) String plotType) {

        List<IndustryPoliticianContributions> allTotals = industryContributionService.getIndustryToPoliticianTotals(bioguideId, congress);

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

    @Autowired
    public void setIndustryContributionService(IndustryContributionService industryContributionService) {
        this.industryContributionService = industryContributionService;
    }

    @Autowired
    public void setLegislatorService(LegislatorService legislatorService) {
        this.legislatorService = legislatorService;
    }
}
