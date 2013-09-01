package poliana.data.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;
import poliana.data.jobs.batch.BillAnalysis;
import poliana.data.repositories.HiveThriftRepository;

import java.util.List;

@Component
public class BillsController implements CommandMarker {

    @Autowired
    private BillAnalysis billAnalysis;
    @Autowired
    private HiveThriftRepository hiveRepository;

    private boolean simpleCommandExecuted = false;

    @CliAvailabilityIndicator({"run"})
    public boolean isSimpleAvailable() {
        return true;
    }

    @CliCommand(value = "select", help = "Run a job")
    public List<String> temp(
            @CliOption(key = { "bill" }, mandatory = true) final String billId) {
        return hiveRepository.selectAllFrom(billId);
    }

}
