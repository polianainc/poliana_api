package com.poliana.web.politicians.finance;

import com.poliana.core.politicianFinance.industries.PoliticianIndustryContributionsTotals;
import com.poliana.core.politicianFinance.industries.PoliticianIndustryFinanceService;
import com.poliana.views.politicianFinance.PoliticianIndustryBarPlot;
import com.poliana.web.common.AbstractBaseController;
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
@RequestMapping("/politicians/") //TODO: start and end assumed if one or other is not given
public class PoliticianIndustryFinanceController extends AbstractBaseController {

    private PoliticianIndustryFinanceService politicianIndustryFinanceService;

    private static final Logger logger = Logger.getLogger(PoliticianIndustryFinanceController.class);


    /**
     * Get all industry contribution totals for a given congressional cycle. The default congress cycle value is
     * the current congress.
     *
     * @param bioguideId
     * @return
     */
    @RequestMapping(value="/{bioguide_id}/contributions/industries", method = RequestMethod.GET)
    public @ResponseBody List<PoliticianIndustryContributionsTotals> getIndustryToPoliticianTotals(
            @PathVariable("bioguide_id") String bioguideId) {

        List<PoliticianIndustryContributionsTotals> allTotals = politicianIndustryFinanceService.getIndustryToPoliticianTotals(bioguideId);
        return allTotals;
    }

    /**
     * Get all industry category contribution totals for a given congressional cycle. The default congress cycle value is
     * the current congress.
     *
     * @param bioguideId
     * @return
     */
    @RequestMapping(value="/{bioguide_id}/contributions/industries/categories", params = {"unit"}, method = RequestMethod.GET)
    public @ResponseBody HashMap<Integer, List<PoliticianIndustryContributionsTotals>> getIndustryCategoryToPoliticianTotalsPerCongress(
            @PathVariable("bioguide_id") String bioguideId) {

        HashMap<Integer, List<PoliticianIndustryContributionsTotals>> allTotals =
                politicianIndustryFinanceService.getIndustryCategoryToPoliticianTotalsPerCongress(bioguideId);

        return allTotals;
    }

    /**
     * Get all industry contribution totals for a given congressional cycle. The default congress cycle value is
     * the current congress.
     *
     * @param bioguideId
     * @return
     */
    @RequestMapping(value="/{bioguide_id}/contributions/industries", params = {"unit"}, method = RequestMethod.GET)
    public @ResponseBody HashMap<Integer, List<PoliticianIndustryContributionsTotals>> getIndustryToPoliticianTotalsPerCongress(
            @PathVariable("bioguide_id") String bioguideId) {

        HashMap<Integer, List<PoliticianIndustryContributionsTotals>> allTotals =
                politicianIndustryFinanceService.getIndustryToPoliticianTotalsPerCongress(bioguideId);
        return allTotals;
    }

    /**
     * Get all industry category contribution totals for a given congressional cycle. The default congress cycle value is
     * the current congress.
     *
     * @param bioguideId
     * @return
     */
    @RequestMapping(value="/{bioguide_id}/contributions/industries/categories", method = RequestMethod.GET)
    public @ResponseBody List<PoliticianIndustryContributionsTotals> getIndustryCategoryToPoliticianTotals(
            @PathVariable("bioguide_id") String bioguideId) {

        List<PoliticianIndustryContributionsTotals> allTotals =
                politicianIndustryFinanceService.getIndustryCategoryToPoliticianTotals(bioguideId);

        return allTotals;
    }

    /**
     * Get all industry contribution totals for a given congressional cycle. The default congress cycle value is
     * the current congress.
     *
     * @param bioguideId
     * @param congress
     * @return
     */
    @RequestMapping(value="/{bioguide_id}/contributions/industries", params = {"congress"}, method = RequestMethod.GET)
    public @ResponseBody List<PoliticianIndustryContributionsTotals> getIndustryToPoliticianTotals(
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress) {

        List<PoliticianIndustryContributionsTotals> allTotals = politicianIndustryFinanceService.getIndustryToPoliticianTotals(bioguideId, congress);
        return allTotals;
    }

    /**
     * Get all industry category contribution totals for a given congressional cycle. The default congress cycle value is
     * the current congress.
     *
     * @param bioguideId
     * @param congress
     * @return
     */
    @RequestMapping(value="/{bioguide_id}/contributions/industries/categories", params = {"congress"}, method = RequestMethod.GET)
    public @ResponseBody List<PoliticianIndustryContributionsTotals> getIndustryCategoryToPoliticianTotals(
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress) {

        List<PoliticianIndustryContributionsTotals> allTotals =
                politicianIndustryFinanceService.getIndustryCategoryToPoliticianTotals(bioguideId, congress);

        return allTotals;
    }

    /**
     * Get industry contribution sums to a given politician over a given time range
     *
     * @param bioguideId
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "/{bioguide_id}/contributions/industries", params = {"start", "end"}, method = RequestMethod.GET)
    public @ResponseBody List<PoliticianIndustryContributionsTotals> getIndustryToPoliticianTotals(
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "start", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date start,
            @RequestParam(value = "end", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date end) {

        List<PoliticianIndustryContributionsTotals> totals =
                politicianIndustryFinanceService.getIndustryToPoliticianTotals(bioguideId, start.getTime()/1000, end.getTime()/1000);

        return totals;
    }

    /**
     * Get industry category contribution sums to a given politician over a given time range
     *
     * @param bioguideId
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "/{bioguide_id}/contributions/industries/categories", params = {"start", "end"}, method = RequestMethod.GET)
    public @ResponseBody List<PoliticianIndustryContributionsTotals> getIndustryCategoryToPoliticianTotals(
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "start", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date start,
            @RequestParam(value = "end", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date end) {

        List<PoliticianIndustryContributionsTotals> totals =
                politicianIndustryFinanceService.getIndustryCategoryToPoliticianTotals(bioguideId, start.getTime() / 1000, end.getTime() / 1000);

        return totals;
    }

    /**
     * Get industry contribution sums to a given politician over a given time range
     *
     * @param bioguideId
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "/{bioguide_id}/contributions/industries", params = {"start", "end", "unit"}, method = RequestMethod.GET)
    public @ResponseBody HashMap<Integer, List<PoliticianIndustryContributionsTotals>> getIndustryToPoliticianTotalsPerCongress(
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "start", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date start,
            @RequestParam(value = "end", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date end) {

        HashMap<Integer, List<PoliticianIndustryContributionsTotals>> totals =
                politicianIndustryFinanceService.getIndustryToPoliticianTotalsPerCongress(bioguideId, start.getTime()/1000, end.getTime()/1000);

        return totals;
    }

    /**
     * Get industry category contribution sums to a given politician over a given time range
     *
     * @param bioguideId
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "/{bioguide_id}/contributions/industries/categories", params = {"start", "end", "unit"}, method = RequestMethod.GET)
    public @ResponseBody HashMap<Integer, List<PoliticianIndustryContributionsTotals>> getIndustryCategoryToPoliticianTotalsPerCongress(
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "start", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date start,
            @RequestParam(value = "end", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date end) {

        HashMap<Integer, List<PoliticianIndustryContributionsTotals>> totals =
                politicianIndustryFinanceService.getIndustryCategoryToPoliticianTotalsPerCongress(bioguideId, start.getTime() / 1000, end.getTime() / 1000);

        return totals;
    }

    /**
     * Plot all industry contribution totals for all time
     * @param bioguideId
     * @return
     */
    @RequestMapping(value="/{bioguide_id}/contributions/industries", params = {"plot"}, method = RequestMethod.GET)
    public void plotIndustryToPoliticianTotals (
            OutputStream stream,
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "plot", required = true) String plotType) {

        List<PoliticianIndustryContributionsTotals> allTotals = politicianIndustryFinanceService.getIndustryToPoliticianTotals(bioguideId);

        PoliticianIndustryBarPlot view = new PoliticianIndustryBarPlot(allTotals);

        JFreeChart chart = view.generateChart(plotType);

        try {
            ChartUtilities.writeChartAsPNG(stream, chart, 1600, 1000);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * Plot all industry category contribution totals for all time
     * @param bioguideId
     * @return
     */
    @RequestMapping(value="/{bioguide_id}/contributions/industries/categories", params = {"plot"}, method = RequestMethod.GET)
    public void plotIndustryCategoryToPoliticianTotals (
            OutputStream stream,
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "plot", required = true) String plotType) {

        List<PoliticianIndustryContributionsTotals> allTotals =
                politicianIndustryFinanceService.getIndustryCategoryToPoliticianTotals(bioguideId);

        PoliticianIndustryBarPlot view = new PoliticianIndustryBarPlot(allTotals);

        JFreeChart chart = view.generateChart(plotType);

        try {
            ChartUtilities.writeChartAsPNG(stream, chart, 1600, 1000);
        } catch (IOException e) {
            logger.error(e);
        }
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

        List<PoliticianIndustryContributionsTotals> allTotals = politicianIndustryFinanceService.getIndustryToPoliticianTotals(bioguideId, congress);

        PoliticianIndustryBarPlot view = new PoliticianIndustryBarPlot(allTotals, congress);

        JFreeChart chart = view.generateChart(plotType);

        try {
            ChartUtilities.writeChartAsPNG(stream, chart, 1600, 1000);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * Plot all industry category contribution totals for a given congressional cycle. The default congress cycle value is
     * the current congress.
     * @param bioguideId
     * @param congress
     * @return
     */
    @RequestMapping(value="/{bioguide_id}/contributions/industries/categories", params = {"congress", "plot"}, method = RequestMethod.GET)
    public void plotIndustryCategoryToPoliticianTotals (
            OutputStream stream,
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
            @RequestParam(value = "plot", required = true) String plotType) {

        List<PoliticianIndustryContributionsTotals> allTotals = politicianIndustryFinanceService.getIndustryCategoryToPoliticianTotals(bioguideId, congress);

        PoliticianIndustryBarPlot view = new PoliticianIndustryBarPlot(allTotals, congress);

        JFreeChart chart = view.generateChart(plotType);

        try {
            ChartUtilities.writeChartAsPNG(stream, chart, 1600, 1000);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * Plot all industry contribution totals for a given congressional cycle. The default congress cycle value is
     * the current congress.
     * @param stream
     * @param bioguideId
     * @param start
     * @param end
     * @param plotType
     */
    @RequestMapping(value="/{bioguide_id}/contributions/industries", params = {"start", "end", "plot"}, method = RequestMethod.GET)
    public void plotIndustryToPoliticianTotals (
            OutputStream stream,
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "start", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date start,
            @RequestParam(value = "end", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date end,
            @RequestParam(value = "plot", required = true) String plotType) {

        List<PoliticianIndustryContributionsTotals> allTotals =
                politicianIndustryFinanceService.getIndustryToPoliticianTotals(bioguideId, start.getTime()/1000, end.getTime()/1000);

        PoliticianIndustryBarPlot view = new PoliticianIndustryBarPlot(allTotals, start, end);

        JFreeChart chart = view.generateChart(plotType);

        try {
            ChartUtilities.writeChartAsPNG(stream, chart, 1600, 1000);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * Plot all industry category contribution totals for a given congressional cycle. The default congress cycle value is
     * the current congress.
     * @param bioguideId
     * @return
     */
    @RequestMapping(value="/{bioguide_id}/contributions/industries/categories", params = {"start", "end", "plot"}, method = RequestMethod.GET)
    public void plotIndustryCategoryToPoliticianTotals (
            OutputStream stream,
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "start", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date start,
            @RequestParam(value = "end", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date end,
            @RequestParam(value = "plot", required = true) String plotType) {

        List<PoliticianIndustryContributionsTotals> allTotals =
                politicianIndustryFinanceService.getIndustryCategoryToPoliticianTotals(bioguideId, start.getTime()/1000, end.getTime()/1000);

        PoliticianIndustryBarPlot view = new PoliticianIndustryBarPlot(allTotals, start, end);

        JFreeChart chart = view.generateChart(plotType);

        try {
            ChartUtilities.writeChartAsPNG(stream, chart, 1600, 1000);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @Autowired
    public void setPoliticianIndustryFinanceService(PoliticianIndustryFinanceService politicianIndustryFinanceService) {
        this.politicianIndustryFinanceService = politicianIndustryFinanceService;
    }
}
