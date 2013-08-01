package poliana.data.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.data.hadoop.hive.HiveScript;
import org.springframework.stereotype.Component;
import poliana.data.models.bill.Bill;
import poliana.data.warehouse.HiveBase;
import poliana.data.repositories.BillRepository;

import java.util.List;

@Component
public class RecipientByBill extends HiveBase {

    @Autowired
    private BillRepository billRepository;

    public List<String> runJob() {
        FileSystemResourceLoader resourceLoader = new FileSystemResourceLoader();
        Resource recipientSelectionResource = resourceLoader.getResource("classpath:hql/select_recipients.hql");
        HiveScript recipientSelection = new HiveScript(recipientSelectionResource);
        List<String> recipients = hiveTemplate.executeScript(recipientSelection);

        Bill bill = new Bill("theIdYo");
        billRepository.save(bill);
        System.out.print(billRepository.findAll());

        return recipients.subList(0, 30);
    }
}
