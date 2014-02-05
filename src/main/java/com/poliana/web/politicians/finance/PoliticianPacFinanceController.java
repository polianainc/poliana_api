package com.poliana.web.politicians.finance;

import com.poliana.core.politicianFinance.pacs.PoliticianPacContributionsTotals;
import com.poliana.core.politicianFinance.pacs.PoliticianPacFinanceService;
import com.poliana.views.politicianFinance.PoliticianPacBarPlot;
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

/**
 * @author David Gilmore
 * @date 1/27/14
 */
@Controller
@RequestMapping("/politicians/")
public class PoliticianPacFinanceController extends AbstractBaseController {

    private PoliticianPacFinanceService politicianPacFinanceService;

    private static final Logger logger = Logger.getLogger(PoliticianPacFinanceController.class);

    /**
     * Get all PAC contribution totals for a given legislator.
     *
     * @param bioguideId
     * @return
     */
    @RequestMapping(value="/{bioguide_id}/contributions/pacs", method = RequestMethod.GET)
    public @ResponseBody List<PoliticianPacContributionsTotals> getPacToPoliticianTotals(
            @PathVariable("bioguide_id") String bioguideId) {

        List<PoliticianPacContributionsTotals> totals = politicianPacFinanceService.getPacToPoliticianTotals(bioguideId);
        return totals;
    }

    /**
     * Get all PAC contribution totals for a given legislator.
     *
     * @param bioguideId
     * @return
     */                                                                  //TODO: instead use sum_by
    @RequestMapping(value="/{bioguide_id}/contributions/pacs", params = {"unit"}, method = RequestMethod.GET)
    public @ResponseBody HashMap<Integer, List<PoliticianPacContributionsTotals>> getPacToPoliticianTotalsPerCongress(
            @PathVariable("bioguide_id") String bioguideId) {

        HashMap<Integer, List<PoliticianPacContributionsTotals>> totals =
                politicianPacFinanceService.getPacToPoliticianTotalsPerCongress(bioguideId);
        return totals;
    }

    /**
     * Get PAC contribution sums to a given politician over a given time range
     *
     * @param bioguideId
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "/{bioguide_id}/contributions/pacs", params = {"start", "end"}, method = RequestMethod.GET)
    public @ResponseBody List<PoliticianPacContributionsTotals> getPacToPoliticianTotals(
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "start", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date start,
            @RequestParam(value = "end", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date end) {

        List<PoliticianPacContributionsTotals> totals =
                politicianPacFinanceService.getPacToPoliticianTotals(bioguideId, start.getTime()/1000, end.getTime()/1000);

        return totals;
    }

    @RequestMapping(value="/{bioguideId}/contributions/pacs", params = {"start", "end", "unit"}, method = RequestMethod.GET)
    public @ResponseBody HashMap<Integer, List<PoliticianPacContributionsTotals>> getPacToPoliticianTotalsPerCongress(
            @PathVariable(value = "bioguideId") String bioguideId,
            @RequestParam(value = "start", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date start,
            @RequestParam(value = "end", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date end) {

        HashMap<Integer, List<PoliticianPacContributionsTotals>> pacTotals =
                politicianPacFinanceService.getPacToPoliticianTotalsPerCongress(bioguideId, start.getTime()/1000, end.getTime()/1000);

        return pacTotals;
    }

    /**
     * Plot all PAC contribution totals for all time
     * @param bioguideId
     * @return
     */
    @RequestMapping(value="/{bioguide_id}/contributions/pacs", params = {"plot"}, method = RequestMethod.GET)
    public void plotPacToPoliticianTotals (
            OutputStream stream,
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "plot", required = true) String plotType) {

        List<PoliticianPacContributionsTotals> totals = politicianPacFinanceService.getPacToPoliticianTotals(bioguideId);

        PoliticianPacBarPlot view = new PoliticianPacBarPlot(totals);

        JFreeChart chart = view.generateChart(plotType);

        try {
            ChartUtilities.writeChartAsPNG(stream, chart, 1600, 1000);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * Plot all PAC contribution totals for a given time range
     * @param bioguideId
     * @return
     */
    @RequestMapping(value="/{bioguide_id}/contributions/pacs", params = {"plot", "start", "end"}, method = RequestMethod.GET)
    public void plotPacToPoliticianTotals (
            OutputStream stream,
            @PathVariable("bioguide_id") String bioguideId,
            @RequestParam(value = "plot", required = true) String plotType,
            @RequestParam(value = "start", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date start,
            @RequestParam(value = "end", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date end) {

        List<PoliticianPacContributionsTotals> totals =
                politicianPacFinanceService.getPacToPoliticianTotals(bioguideId, start.getTime()/1000, end.getTime()/1000);

        PoliticianPacBarPlot view = new PoliticianPacBarPlot(totals, start, end);

        JFreeChart chart = view.generateChart(plotType);

        try {
            ChartUtilities.writeChartAsPNG(stream, chart, 1600, 1000);
        } catch (IOException e) {
            logger.error(e);
        }
    }


    @Autowired
    public void setPoliticianPacFinanceService(PoliticianPacFinanceService politicianPacFinanceService) {
        this.politicianPacFinanceService = politicianPacFinanceService;
    }
}
