package poliana.data.jobs;

import org.apache.hadoop.hive.service.HiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hive.HiveClientFactory;
import org.springframework.data.hadoop.hive.HiveTemplate;
import org.springframework.stereotype.Component;
import poliana.data.repositories.HiveJdbcRepository;
import poliana.data.repositories.HiveThriftRepository;

@Component
public class JobBase {
    @Autowired
    protected HiveTemplate hiveTemplate;
    @Autowired
    protected HiveClient hiveClient;
    @Autowired
    protected HiveThriftRepository hiveThrift;
    @Autowired
    protected HiveJdbcRepository hiveJdbc;
}
