package poliana.data.repositories.bills;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.data.hadoop.hive.HiveClientFactory;
import org.springframework.data.hadoop.hive.HiveScript;
import org.springframework.data.hadoop.hive.HiveTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Class Description of MyClass */
@Repository
public class BillHiveThriftRepo {

    @Autowired
    protected HiveClientFactory hiveClientFactory;
    @Autowired
    protected HiveTemplate hiveTemplate;
    @Autowired
    protected FileSystemResourceLoader resourceLoader;

    public List<String> allContributions(String billId) {
        Resource testScriptResource = resourceLoader.getResource("classpath:hql/query/templates/select_all.hql");

        Map params = new HashMap();
        params.put("table", billId);

        HiveScript testScript = new HiveScript(testScriptResource, params);
        return hiveTemplate.executeScript(testScript);
    }

    public List<String> allContributions(String billId, Integer limit) {
        if (limit != null) {
            Resource testScriptResource = resourceLoader.getResource("classpath:hql/query/templates/select_all_limit.hql");

            Map params = new HashMap();
            params.put("table", billId);
            params.put("limit", billId);

            HiveScript testScript = new HiveScript(testScriptResource, params);
            return hiveTemplate.executeScript(testScript);
        }
        else return allContributions(billId);
    }
}
// TODO begin testing
// TODO make HiveRepository asynchronous