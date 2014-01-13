package com.poliana.shell.controllers;

import com.poliana.core.industries.IndustryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Controller;


/**
 * @author David Gilmore
 * @date 11/23/13
 */
@Controller
public class IndustryController implements CommandMarker {

    @Autowired
    private IndustryRepo industryRepo;

    @CliCommand(value = "populateIndustries")
    public void populateIndustries() {
        industryRepo.loadIndustriesToMongo();
    }
}
