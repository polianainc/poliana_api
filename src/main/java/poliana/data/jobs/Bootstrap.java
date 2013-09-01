package poliana.data.jobs;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.data.hadoop.hive.HiveScript;
import org.springframework.stereotype.Component;
import poliana.data.models.bill.Contribution;
import poliana.data.repositories.HiveJdbcRepository;

import java.util.List;

@Component
public class Bootstrap extends JobBase implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println(hiveThrift.showDatabases());
        System.out.println(hiveThrift.showTables());
        for(String row : hiveThrift.selectAllFrom("s743_financials"))
            System.out.println(row);
        List<Contribution> table = hiveJdbc.s743_financials();
        System.out.println(table.size());
    }
}
