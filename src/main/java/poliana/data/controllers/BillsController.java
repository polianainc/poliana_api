package poliana.data.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;
import poliana.data.jobs.RecipientByBill;

@Component
public class BillsController implements CommandMarker {

    @Autowired
    private RecipientByBill recipientByBill;

    private boolean simpleCommandExecuted = false;

    @CliAvailabilityIndicator({"run"})
    public boolean isSimpleAvailable() {
        //always available
        return true;
    }

    @CliCommand(value = "run", help = "Run a job")
    public String simple(
            @CliOption(key = { "job" }, mandatory = true) final String job,
            @CliOption(key = { "item" }, mandatory = false) final String item) {
        simpleCommandExecuted = true;
        if(job.equalsIgnoreCase("influence")) {
            return recipientByBill.runJob().get(0);
        }
        else {
            return "Please specify a valid job";
        }
    }
}
