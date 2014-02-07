package com.poliana.web.bills;

import com.poliana.core.sponsorship.SponsorshipMatrix;
import com.poliana.core.sponsorship.SponsorshipService;
import com.poliana.web.common.AbstractBaseController;
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
@RequestMapping("/sponsorships")
public class SponsorshipController extends AbstractBaseController {

    private SponsorshipService sponsorshipService;

    private static final Logger logger = Logger.getLogger(SponsorshipController.class);


    @RequestMapping(value="/matrix/{chamber}/{congress}", headers="Accept=*/*", method = RequestMethod.GET)
    public @ResponseBody SponsorshipMatrix getSponsorshipMatrix(
            @PathVariable("chamber") String chamber,
            @PathVariable("congress") Integer congress) {

        SponsorshipMatrix sponsorshipMatrix = sponsorshipService.getSponsorshipMatrix(chamber, congress);

        return sponsorshipMatrix;
    }


    @Autowired
    public void setSponsorshipService(SponsorshipService sponsorshipService) {
        this.sponsorshipService = sponsorshipService;
    }
}
