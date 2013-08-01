package poliana.data.warehouse;

import org.apache.hadoop.hive.service.HiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hive.HiveTemplate;
import org.springframework.stereotype.Component;

@Component
public class HiveBase {
    @Autowired
    protected HiveTemplate hiveTemplate;
    @Autowired
    protected HiveClient hiveClient;
}
