package com.poliana.web;

import com.poliana.core.politicianProfile.PoliticianProfile;
import com.poliana.core.politicianProfile.PoliticianProfileService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author David Gilmore
 * @date 1/3/14
 */
@Controller
public class PoliticianController extends AbstractBaseController {

    private PoliticianProfileService profileService;

    private static final Logger logger = Logger.getLogger(PoliticianController.class);


    @RequestMapping(value="/politicians/{bioguideId}", headers="Accept=*/*", method = RequestMethod.GET)
    public @ResponseBody String getPoliticianProfile(@PathVariable("bioguideId") String bioguideId) {

        PoliticianProfile politicianProfile = profileService.getPoliticianProfile(bioguideId);
        return this.gson.toJson(politicianProfile);
    }

    @Autowired
    public void setProfileService(PoliticianProfileService profileService) {
        this.profileService = profileService;
    }
}
