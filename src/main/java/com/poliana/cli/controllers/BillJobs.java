package com.poliana.cli.controllers;

import com.poliana.core.legislation.jobs.IngestGovtrack;
import com.poliana.core.legislation.services.LegislationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Controller;


/**
 * @author David Gilmore
 * @date 11/17/13
 */
@Controller
public class BillJobs implements CommandMarker {

    @Autowired
    private IngestGovtrack gtProcess;

    @Autowired
    private LegislationService legislationService;


    @CliCommand(value = "populateBills")
    public void populateBills(
            @CliOption(key = { "congress" }, mandatory = true ) final int congress,
            @CliOption(key = { "limit" }, mandatory = false ) final int limit) {

        gtProcess.processBills(congress);

    }

    @CliCommand(value = "populateVotes")
    public void processGovtrackVotes(
            @CliOption(key = { "congress"}, mandatory = true) final int congress ) {
        gtProcess.processGovtrackVotesByCongress(congress);
    }

    @CliCommand(value = "sponsorshipAnalysis")
    public String sponsorshipAnalysis(
            @CliOption(key = { "chamber" }, mandatory = true ) final String chamber,
            @CliOption(key = { "congress" }, mandatory = true ) final int congress) {
        return null;
    }
}
