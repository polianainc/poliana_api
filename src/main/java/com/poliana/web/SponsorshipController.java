package com.poliana.web;

import com.poliana.core.sponsorship.SponsorshipMatrix;
import com.poliana.core.sponsorship.SponsorshipService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author David Gilmore
 * @date 12/31/13
 */
@Controller
@RequestMapping("/bill-sponsorship")
public class SponsorshipController extends AbstractBaseController {

    private SponsorshipService sponsorshipService;

    private static final Logger logger = Logger.getLogger(SponsorshipController.class);


    @RequestMapping(value="/matrix/{chamber}/{congress}", headers="Accept=*/*", method = RequestMethod.GET)
    public @ResponseBody String getSponsorshipMatrix(
            @PathVariable("chamber") String chamber,
            @PathVariable("congress") Integer congress) {

        SponsorshipMatrix sponsorshipMatrix = sponsorshipService.getSponsorshipMatrix(chamber, congress);
        return this.gson.toJson(sponsorshipMatrix);
    }


    @Autowired
    public void setSponsorshipService(SponsorshipService sponsorshipService) {
        this.sponsorshipService = sponsorshipService;
    }
}
