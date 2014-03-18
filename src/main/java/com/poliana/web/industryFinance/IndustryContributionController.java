package com.poliana.web.industryFinance;

import com.poliana.core.ideology.IdeologyMatrix;
import com.poliana.core.ideology.IdeologyService;
import com.poliana.core.industryFinance.entities.IndustryContributionCompare;
import com.poliana.core.industryFinance.entities.IndustryContributionTotalsMap;
import com.poliana.core.industryFinance.services.IndustryContributionCompareService;
import com.poliana.core.industryFinance.services.IndustryContributionService;
import com.poliana.views.IndustryContributionBarPlot;
import com.poliana.web.aspect.Industry;
import com.poliana.web.aspect.IndustryCategory;
import com.poliana.web.common.AbstractBaseController;
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
@RequestMapping(value = "/industries", method = RequestMethod.GET)
public class IndustryContributionController extends AbstractBaseController {

    private IdeologyService ideologyService;
    private IndustryContributionService industryContributionService;
    private IndustryContributionCompareService industryContributionCompareService;

    private static final Logger logger = Logger.getLogger(IndustryContributionController.class);


    /**
     * Get industry contributions by congressional session. If no congress is provided, it will default to the current
     * congress.
     *
     * @param industryId
     * @param chamber
     * @param congress
     * @return
     */
    @RequestMapping(value = "/{industry_id}/contributions")
    public @ResponseBody IndustryContributionTotalsMap getIndustryContributionsByCongress(
            @Industry @PathVariable(value = "industry_id") String industryId,
            @RequestParam(value = "chamber", required = false) String chamber,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress) {

        if (chamber != null) //Get contributions for a specific chamber
            return industryContributionService.getIndustryContributionTotalsMap(industryId, chamber, congress);

        else  //If no chamber is specified, return both the house and senate contributions.
            return industryContributionService.getIndustryContributionTotalsMap(industryId, congress);

    }

    /**
     * Get industry category contributions by congressional session.
     * If no congress is provided, it will default to the current congress.
     *
     * @param categoryId
     * @param chamber
     * @param congress
     * @return
     */
    @RequestMapping(value = "/category/{category_id}/contributions")
    public @ResponseBody IndustryContributionTotalsMap getIndustryCategoryContributionsByCongress(
            @IndustryCategory   @PathVariable(value = "category_id") String categoryId,
            @RequestParam(value = "chamber", required = false) String chamber,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress) {

        if (chamber != null) //Get contributions for a specific chamber
            return industryContributionService.getIndustryCategoryContributionTotalsMap(categoryId, chamber, congress);

        else  //If no chamber is specified, return both the house and senate contributions.
            return industryContributionService.getIndustryCategoryContributionTotalsMap(categoryId, congress);

    }

    /**
     * Plot industry contributions by congressional session. If no congress is provided, it will default to the current
     * congress.
     *
     * @param industryId
     * @param chamber
     * @param congress
     * @return
     */
    @RequestMapping(value = "/{industry_id}/contributions", params = {"plot"})
    public @ResponseBody
    IndustryContributionTotalsMap plotIndustryContributionsByCongress(
            @Industry @PathVariable(value = "industry_id") String industryId,
            @RequestParam(value = "chamber", required = false) String chamber,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
            @RequestParam(value = "plot") String plotType) {

        if (chamber != null) //Get contributions for a specific chamber
            return industryContributionService.getIndustryContributionTotalsMap(industryId, chamber, congress);

        else  //If no chamber is specified, return both the house and senate contributions.
            return industryContributionService.getIndustryContributionTotalsMap(industryId, congress);

    }

    /**
     * Plot industry category contributions by congressional session.
     * If no congress is provided, it will default to the current congress.
     *
     * @param categoryId
     * @param chamber
     * @param congress
     * @return
     */
    @RequestMapping(value = "/categories/{category_id}/contributions", params = {"plot"})
    public @ResponseBody
    IndustryContributionTotalsMap plotIndustryCategoryContributionsByCongress(
            @IndustryCategory @PathVariable(value = "category_id") String categoryId,
            @RequestParam(value = "chamber", required = false) String chamber,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
            @RequestParam(value = "plot") String plotType) {

        if (chamber != null) //Get contributions for a specific chamber
            return industryContributionService.getIndustryCategoryContributionTotalsMap(categoryId, chamber, congress);

        else  //If no chamber is specified, return both the house and senate contributions.
            return industryContributionService.getIndustryCategoryContributionTotalsMap(categoryId, congress);

    }

    /**
     * Get Industry contributions by congressional cycle and compared to a given metric.
     *
     * @param industryId
     * @param chamber
     * @param congress
     * @return
     */
    @RequestMapping(value = "/{industry_id}/contributions", params = {"compare_to"})
    public @ResponseBody
    List<IndustryContributionCompare> getIndustryContributionsVsIdeologyByCongress(
            @Industry @PathVariable(value = "industry_id") String industryId,
            @RequestParam(value = "chamber", required = false, defaultValue = "s") String chamber,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
            @RequestParam(value = "compare_to", required = false) String compareTo) {

        IndustryContributionTotalsMap totals;
        IdeologyMatrix ideologyMatrix;

        switch (compareTo) {

            default: {
                totals = industryContributionService.getIndustryContributionTotalsMap(industryId, chamber, congress);
                ideologyMatrix = ideologyService.getIdeologyMatrix(chamber, congress);
                return industryContributionCompareService.getIndustryContributionsVsIdeology(ideologyMatrix, totals);
            }
        }
    }

    /**
     * Get Industry category contributions by congressional cycle and compared to a given metric.
     *
     * @param categoryId
     * @param chamber
     * @param congress
     * @return
     */
    @RequestMapping(value = "/categories/{category_id}/contributions", params = {"compare_to"})
    public @ResponseBody
    List<IndustryContributionCompare> getIndustryCategoryContributionsVsIdeologyByCongress(
            @IndustryCategory @PathVariable(value = "category_id") String categoryId,
            @RequestParam(value = "chamber", required = false, defaultValue = "s") String chamber,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
            @RequestParam(value = "compare_to", required = false) String compareTo) {

        IndustryContributionTotalsMap totals;
        IdeologyMatrix ideologyMatrix;

        switch (compareTo) {

            default: {
                totals = industryContributionService.getIndustryCategoryContributionTotalsMap(categoryId, chamber, congress);
                ideologyMatrix = ideologyService.getIdeologyMatrix(chamber, congress);
                return industryContributionCompareService.getIndustryContributionsVsIdeology(ideologyMatrix, totals);
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
    @RequestMapping(value = "/{industry_id}/contributions", params = {"compare_to", "plot"})
    public void plotIndustryContributionsVsIdeologyByCongress(
            OutputStream stream,
            @Industry @PathVariable(value = "industry_id") String industryId,
            @RequestParam(value = "chamber", required = false, defaultValue = "s") String chamber,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
            @RequestParam(value = "compare_to") String compareTo,
            @RequestParam(value = "plot") String plotType) {

        List<IndustryContributionCompare> compareTotals;

        switch (compareTo) {

            default: {
                IndustryContributionTotalsMap industryTotals = industryContributionService.getIndustryContributionTotalsMap(industryId, chamber, congress);
                IdeologyMatrix ideologyMatrix = ideologyService.getIdeologyMatrix(chamber, congress);

                compareTotals = industryContributionCompareService.getIndustryContributionsVsIdeology(ideologyMatrix, industryTotals);
            }
        }

        IndustryContributionBarPlot view = new IndustryContributionBarPlot(compareTotals, chamber, congress);
        JFreeChart chart = view.generateChart(plotType);

        try {
            ChartUtilities.writeChartAsPNG(stream, chart, 1200, 800);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * Plot Industry contributions by congressional cycle as compared to a given metric.
     *
     * @param stream
     * @param categoryId
     * @param chamber
     * @param congress
     * @return
     */
    @RequestMapping(value = "/categories/{category_id}/contributions", params = {"compare_to", "plot"})
    public void plotIndustryCategoryContributionsVsIdeologyByCongress(
            OutputStream stream,
            @IndustryCategory @PathVariable(value = "category_id") String categoryId,
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

                compareTotals = industryContributionCompareService.getIndustryContributionsVsIdeology(ideologyMatrix, industryTotals);
            }
        }

        IndustryContributionBarPlot view = new IndustryContributionBarPlot(compareTotals, chamber, congress);
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

    @Autowired
    public void setIndustryContributionCompareService(IndustryContributionCompareService industryContributionCompareService) {
        this.industryContributionCompareService = industryContributionCompareService;
    }
}
