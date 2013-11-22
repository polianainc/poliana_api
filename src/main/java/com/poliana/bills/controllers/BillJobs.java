package com.poliana.bills.controllers;

import com.poliana.bills.entities.BillPojo;
import com.poliana.bills.jobs.MapBillRelationships;
import com.poliana.bills.entities.Bill;
import com.poliana.bills.entities.Vote;
import com.poliana.bills.repositories.BillHadoopRepo;
import com.poliana.bills.repositories.BillMongoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @author David Gilmore
 * @date 11/17/13
 */
@Controller
public class BillJobs implements CommandMarker {
    @Autowired
    private BillHadoopRepo billHadoopRepo;
    @Autowired
    private MapBillRelationships gtProcess;
    @Autowired
    private BillMongoRepo billMongoRepo;

    @CliCommand(value = "populateBills")
    public void populateBills(
            @CliOption(key = { "congress" }, mandatory = true ) final int congress,
            @CliOption(key = { "limit" }, mandatory = false ) final int limit) {

        List<BillPojo> rawBills = billHadoopRepo.billMetaByCongress(congress,limit);
        List<Bill>  bills = gtProcess.processBills(rawBills);
        billMongoRepo.saveBills(bills);
    }

    @CliCommand(value = "processGovtrackVotes")
    public void processGovtrackVotes(
            @CliOption(key = { "congress"}, mandatory = true) final int congress ) {
        List<Vote> votes = gtProcess.processGovtrackVotesByCongress(113);
        billMongoRepo.saveVotes(votes);
    }
}
