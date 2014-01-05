package com.poliana.web;

import com.poliana.core.industryFinance.IndustryContributionService;
import com.poliana.core.industryFinance.entities.IndToPolContrTotals;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * @author David Gilmore
 * @date 1/4/14
 */
@Controller
@RequestMapping("/industry-finance/")
public class IndustryContributionController extends AbstractBaseController {

    private IndustryContributionService industryContributionService;

    private static final Logger logger = Logger.getLogger(IndustryContributionController.class);


    @RequestMapping(value="/{bioguideId}", method = RequestMethod.GET)
    public @ResponseBody String getAllIndustryContributions (
            @PathVariable("bioguideId") String bioguideId) {

        HashMap<Integer, List<IndToPolContrTotals>> allTotals = industryContributionService.getIndustryTotalsAllTime(bioguideId);
        return this.gson.toJson(allTotals);
    }

    @RequestMapping(value="/{bioguideId}/{congress}", method = RequestMethod.GET)
    public @ResponseBody String getAllIndustryContributionsByCongress (
            @PathVariable("bioguideId") String bioguideId,
            @PathVariable("congress") int congress) {

        List<IndToPolContrTotals> allTotals = industryContributionService.getIndustryTotals(bioguideId, congress);
        return this.gson.toJson(allTotals);
    }

    @Autowired
    public void setIndustryContributionService(IndustryContributionService industryContributionService) {
        this.industryContributionService = industryContributionService;
    }
}
