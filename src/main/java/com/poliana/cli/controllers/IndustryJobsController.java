package com.poliana.cli.controllers;

import com.poliana.core.industryFinance.jobs.IndContrSessionBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * @author David Gilmore
 * @date 11/23/13
 */
@Controller
public class IndustryJobsController implements CommandMarker {

    @Autowired
    private IndContrSessionBatch indContrSessionBatch;

    @CliCommand(value = "indContributions", help = "Run a job")
    public void actions(
            @CliOption(key = { "congress" }, mandatory = true) final int congress) throws IOException {
        indContrSessionBatch.processIndustryTotals(congress);
    }

}
