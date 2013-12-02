package com.poliana.core.legislation.cli;

import com.poliana.core.legislation.entities.deprecated.BillAction;
import com.poliana.core.legislation.services.LegislationService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class BillsController implements CommandMarker {

    @Autowired
    private LegislationService legislationService;

    private boolean simpleCommandExecuted = false;

    @CliAvailabilityIndicator({"graph"})
    public boolean isSimpleAvailable() {
        return true;
    }


    @CliCommand(value = "actions", help = "Run a job")
    public String actions(
            @CliOption(key = { "bill" }, mandatory = true) final String billId) throws IOException {

        JSONArray actionArray = new JSONArray();
        for(BillAction billAction : legislationService.billActions(billId)) {
            actionArray.put(new JSONObject(billAction));
        }
        return actionArray.toString();
    }
}
