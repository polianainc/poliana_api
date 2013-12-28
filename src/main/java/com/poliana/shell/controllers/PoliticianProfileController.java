package com.poliana.shell.controllers;

import com.poliana.core.politicianProfile.PoliticianProfile;
import com.poliana.core.politicianProfile.PoliticianProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Controller;

/**
 * @author David Gilmore
 * @date 12/26/13
 */
@Controller
public class PoliticianProfileController implements CommandMarker {

    @Autowired
    private PoliticianProfileService profileService;

    @CliCommand(value = "politicianProfile")
    public PoliticianProfile populateCommittees(
            @CliOption(key = { "bioguide"}, mandatory = true) final String bioguideId) {
        return profileService.getPoliticianProfile(bioguideId);
    }
}
