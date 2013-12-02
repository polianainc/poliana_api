package com.poliana.core.entities.cli;

import com.poliana.core.entities.jobs.EntityJobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Controller;

/**
 * @author David Gilmore
 * @date 11/17/13
 */
@Controller
public class EntitiesController implements CommandMarker {

    @Autowired
    private EntityJobs entityJobs;

    @CliCommand(value = "populateIndustries")
    public void populateIndustries() {
        entityJobs.loadIndustriesToMongo();
    }

    @CliCommand(value = "populateLegislators")
    public void populateLegislators() {
        entityJobs.loadLegislatorsToMongo();
    }

    @CliCommand(value = "populateCommittees")
    public void populateCommittees() {
        entityJobs.loadCommitteesToMongo();
    }
}
