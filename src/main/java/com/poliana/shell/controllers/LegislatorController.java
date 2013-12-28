package com.poliana.shell.controllers;

import com.poliana.core.legislators.LegislatorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Controller;

/**
 * @author David Gilmore
 * @date 12/26/13
 */
@Controller
public class LegislatorController implements CommandMarker {

    @Autowired
    private LegislatorRepo legislatorRepo;

    @CliCommand(value = "populateLegislators")
    public void populateLegislators() {
        legislatorRepo.loadLegislatorsToMongo();
    }
}
