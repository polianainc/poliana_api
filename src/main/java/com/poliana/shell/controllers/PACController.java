package com.poliana.shell.controllers;

import com.poliana.core.pacs.PACRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Controller;

/**
 * @author David Gilmore
 * @date 12/26/13
 */
@Controller
public class PACController implements CommandMarker {

    @Autowired
    private PACRepo pacRepo;

    @CliCommand(value = "populateCommittees")
    public void populateCommittees() {
        pacRepo.loadCommitteesToMongo();
    }
}
