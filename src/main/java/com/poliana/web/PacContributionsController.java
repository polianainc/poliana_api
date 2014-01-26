package com.poliana.web;

import com.poliana.core.pacFinance.PacContributionService;
import com.poliana.core.pacFinance.entities.PacPoliticianContributionTotals;
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
@RequestMapping("/pacs/")
public class PacContributionsController extends AbstractBaseController {

    private PacContributionService pacContributionService;

    private static final Logger logger = Logger.getLogger(PacContributionsController.class);


    @RequestMapping(value="/{bioguideId}", method = RequestMethod.GET)
    public @ResponseBody String getAllPacContributions(
            @PathVariable("bioguideId") String bioguideId) {

        HashMap<Integer, List<PacPoliticianContributionTotals>> allTotals = pacContributionService.getPacTotalsAllTime(bioguideId);
        return this.gson.toJson(allTotals);
    }

    @RequestMapping(value="/{bioguideId}/{congress}", method = RequestMethod.GET)
    public @ResponseBody String getPacContributions(
            @PathVariable("bioguideId") String bioguideId,
            @PathVariable("congress") Integer congress) {

        List<PacPoliticianContributionTotals> pacTotals = pacContributionService.getPacTotals(bioguideId, congress);
        return this.gson.toJson(pacTotals);
    }

    @Autowired
    public void setPacContributionService(PacContributionService pacContributionService) {
        this.pacContributionService = pacContributionService;
    }
}
