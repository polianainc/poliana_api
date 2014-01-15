package com.poliana.web;

import com.poliana.core.ideology.IdeologyMatrix;
import com.poliana.core.ideology.IdeologyService;
import com.poliana.core.industryFinance.services.IndustryContributionService;
import com.poliana.core.industryFinance.entities.IndustryContributionCompare;
import com.poliana.core.industryFinance.entities.IndustryContributionTotalsMap;
import com.poliana.views.IndustryContributionView;
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
 * @date 1/4/14
 */
@Controller
@RequestMapping("/industry")
public class IndustryContributionController extends AbstractBaseController {

    private IdeologyService ideologyService;
    private IndustryContributionService industryContributionService;

    private static final Logger logger = Logger.getLogger(IndustryContributionController.class);


    /**
     * Get industry contributions by congressional session. If no congress is provided, it will default to the current
     * congress.
     * @param industryId
     * @param chamber
     * @param congress
     * @return
     */
    @RequestMapping(value = "/{industry_id}/contributions", method = RequestMethod.GET)
    public @ResponseBody String getIndustryContributionsByCongress(
            @PathVariable(value = "industry_id") String industryId,
            @RequestParam(value = "chamber", required = false) String chamber,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress) {

        if (chamber != null) //Get contributions for a specifc chamber
            return this.gson.toJson(industryContributionService.getIndustryContributionTotalsMap(industryId, chamber, congress));

        else  //If no chamber is specified, return both the house and senate contributions.
            return this.gson.toJson(industryContributionService.getIndustryContributionTotalsMap(industryId, congress));

    }

    /**
     * Get industry category contributions by congressional session.
     * If no congress is provided, it will default to the current congress.
     * @param categoryId
     * @param chamber
     * @param congress
     * @return
     */
    @RequestMapping(value = "/category/{category_id}/contributions", method = RequestMethod.GET)
    public @ResponseBody String getIndustryCategoryContributionsByCongress(
            @PathVariable(value = "category_id") String categoryId,
            @RequestParam(value = "chamber", required = false) String chamber,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress) {

        if (chamber != null) //Get contributions for a specifc chamber
            return this.gson.toJson(industryContributionService.getIndustryCategoryContributionTotalsMap(categoryId, chamber, congress));

        else  //If no chamber is specified, return both the house and senate contributions.
            return this.gson.toJson(industryContributionService.getIndustryCategoryContributionTotalsMap(categoryId, congress));

    }

    /**
     * Plot industry contributions by congressional session. If no congress is provided, it will default to the current
     * congress.
     * @param industryId
     * @param chamber
     * @param congress
     * @return
     */
    @RequestMapping(value = "/{industry_id}/contributions", params = {"plot"}, method = RequestMethod.GET)
    public @ResponseBody String plotIndustryContributionsByCongress(
            @PathVariable(value = "industry_id") String industryId,
            @RequestParam(value = "chamber", required = false) String chamber,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
            @RequestParam(value = "plot") String plotType) {

        if (chamber != null) //Get contributions for a specifc chamber
            return this.gson.toJson(industryContributionService.getIndustryContributionTotalsMap(industryId, chamber, congress));

        else  //If no chamber is specified, return both the house and senate contributions.
            return this.gson.toJson(industryContributionService.getIndustryContributionTotalsMap(industryId, congress));

    }

    /**
     * Plot industry category contributions by congressional session.
     * If no congress is provided, it will default to the current congress.
     * @param categoryId
     * @param chamber
     * @param congress
     * @return
     */
    @RequestMapping(value = "/category/{category_id}/contributions", params = {"plot"}, method = RequestMethod.GET)
    public @ResponseBody String plotIndustryCategoryContributionsByCongress(
            @PathVariable(value = "category_id") String categoryId,
            @RequestParam(value = "chamber", required = false) String chamber,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
            @RequestParam(value = "plot") String plotType) {

        if (chamber != null) //Get contributions for a specifc chamber
            return this.gson.toJson(industryContributionService.getIndustryCategoryContributionTotalsMap(categoryId, chamber, congress));

        else  //If no chamber is specified, return both the house and senate contributions.
            return this.gson.toJson(industryContributionService.getIndustryCategoryContributionTotalsMap(categoryId, congress));

    }

    /**
     * Get Industry contributions by congressional cycle and compared to a given metric.
     * @param industryId
     * @param chamber
     * @param congress
     * @return
     */
    @RequestMapping(value = "/{industry_id}/contributions", params = {"compare_to"},method = RequestMethod.GET)
    public @ResponseBody String getIndustryContributionsVsIdeologyByCongress(
            @PathVariable(value = "industry_id") String industryId,
            @RequestParam(value = "chamber", required = false, defaultValue = "s") String chamber,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
            @RequestParam(value = "compare_to", required = false) String compareTo) {

        IndustryContributionTotalsMap totals;
        IdeologyMatrix ideologyMatrix;

        switch (compareTo) {

            default: {
                totals = industryContributionService.getIndustryContributionTotalsMap(industryId, chamber, congress);
                ideologyMatrix = ideologyService.getIdeologyMatrix(chamber, congress);
                return this.gson.toJson(industryContributionService.getIndustryContributionsVsIdeology(ideologyMatrix, totals));
            }
        }
    }

    /**
     * Get Industry category contributions by congressional cycle and compared to a given metric.
     * @param categoryId
     * @param chamber
     * @param congress
     * @return
     */
    @RequestMapping(value = "/category/{category_id}/contributions", params = {"compare_to"},method = RequestMethod.GET)
    public @ResponseBody String getIndustryCategoryContributionsVsIdeologyByCongress(
            @PathVariable(value = "category_id") String categoryId,
            @RequestParam(value = "chamber", required = false, defaultValue = "s") String chamber,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
            @RequestParam(value = "compare_to", required = false) String compareTo) {

        IndustryContributionTotalsMap totals;
        IdeologyMatrix ideologyMatrix;

        switch (compareTo) {

            default: {
                totals = industryContributionService.getIndustryCategoryContributionTotalsMap(categoryId, chamber, congress);
                ideologyMatrix = ideologyService.getIdeologyMatrix(chamber, congress);
                return this.gson.toJson(industryContributionService.getIndustryContributionsVsIdeology(ideologyMatrix, totals));
            }
        }
    }

    /**
     * Plot Industry contributions by congressional cycle as compared to a given metric.
     * @param stream
     * @param industryId
     * @param chamber
     * @param congress
     * @return
     */
    @RequestMapping(value = "/{industry_id}/contributions", params = {"compare_to", "plot"},method = RequestMethod.GET)
    public void plotIndustryContributionsVsIdeologyByCongress(
            OutputStream stream,
            @PathVariable(value = "industry_id") String industryId,
            @RequestParam(value = "chamber", required = false, defaultValue = "s") String chamber,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
            @RequestParam(value = "compare_to") String compareTo,
            @RequestParam(value = "plot") String plotType) {

        List<IndustryContributionCompare> compareTotals;

        switch (compareTo) {

            default: {
                IndustryContributionTotalsMap industryTotals = industryContributionService.getIndustryContributionTotalsMap(industryId, chamber, congress);
                IdeologyMatrix ideologyMatrix = ideologyService.getIdeologyMatrix(chamber, congress);

                compareTotals = industryContributionService.getIndustryContributionsVsIdeology(ideologyMatrix, industryTotals);
            }
        }

        IndustryContributionView view = new IndustryContributionView(compareTotals, chamber, congress);
        JFreeChart chart = view.generateChart(plotType);

        try {
            ChartUtilities.writeChartAsPNG(stream, chart, 1200, 800);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * Plot Industry contributions by congressional cycle as compared to a given metric.
     * @param stream
     * @param categoryId
     * @param chamber
     * @param congress
     * @return
     */
    @RequestMapping(value = "/category/{category_id}/contributions", params = {"compare_to", "plot"},method = RequestMethod.GET)
    public void plotIndustryCategoryContributionsVsIdeologyByCongress(
            OutputStream stream,
            @PathVariable(value = "category_id") String categoryId,
            @RequestParam(value = "chamber", required = false, defaultValue = "s") String chamber,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
            @RequestParam(value = "compare_to") String compareTo,
            @RequestParam(value = "plot") String plotType) {

        List<IndustryContributionCompare> compareTotals;

        switch (compareTo) {

            default: {
                IndustryContributionTotalsMap industryTotals =
                        industryContributionService.getIndustryCategoryContributionTotalsMap(categoryId, chamber, congress);
                IdeologyMatrix ideologyMatrix = ideologyService.getIdeologyMatrix(chamber, congress);

                compareTotals = industryContributionService.getIndustryContributionsVsIdeology(ideologyMatrix, industryTotals);
            }
        }

        IndustryContributionView view = new IndustryContributionView(compareTotals, chamber, congress);
        JFreeChart chart = view.generateChart(plotType);

        try {
            ChartUtilities.writeChartAsPNG(stream, chart, 1200, 800);
        } catch (IOException e) {
            logger.error(e);
        }
    }


    @Autowired
    public void setIdeologyService(IdeologyService ideologyService) {
        this.ideologyService = ideologyService;
    }

    @Autowired
    public void setIndustryContributionService(IndustryContributionService industryContributionService) {
        this.industryContributionService = industryContributionService;
    }
}
