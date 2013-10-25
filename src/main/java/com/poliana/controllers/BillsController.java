package com.poliana.controllers;

import com.poliana.bills.entities.BillAction;
import com.poliana.bills.services.BillService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;
import com.poliana.jobs.batch.BillAnalysis;
import org.json.JSONObject;

import java.io.IOException;

@Component
public class BillsController implements CommandMarker {

    @Autowired
    private BillAnalysis billAnalysis;
    @Autowired
    private BillService billService;

    private boolean simpleCommandExecuted = false;

    @CliAvailabilityIndicator({"graph"})
    public boolean isSimpleAvailable() {
        return true;
    }

    @CliCommand(value = "bill_overview", help = "Run a job")
    public String billAnalysis(
            @CliOption(key = { "bill" }, mandatory = true) final String billId) throws IOException {

        JSONObject jsonObj = new JSONObject( billAnalysis.billTopList(billId) );
        return jsonObj.toString();
    }

    @CliCommand(value = "actions", help = "Run a job")
    public String actions(
            @CliOption(key = { "bill" }, mandatory = true) final String billId) throws IOException {

        JSONArray actionArray = new JSONArray();
        for(BillAction billAction : billService.billActions(billId)) {
            actionArray.put(new JSONObject(billAction));
        }
        return actionArray.toString();
    }

}
