package poliana.data.repositories;

import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.data.hadoop.hive.HiveClientFactory;
import org.springframework.data.hadoop.hive.HiveOperations;
import org.springframework.data.hadoop.hive.HiveScript;
import org.springframework.data.hadoop.hive.HiveTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HiveThriftRepository {

    protected HiveOperations hiveOperations;
    protected FileSystemResourceLoader resourceLoader;


    public HiveThriftRepository(HiveOperations hiveOperations) {
        resourceLoader = new FileSystemResourceLoader();
        this.hiveOperations = hiveOperations;
    }

    public HiveThriftRepository(HiveClientFactory hiveClientFactory) {
        this(new HiveTemplate(hiveClientFactory));
    }

    public String showDatabases() {
        return hiveOperations.queryForString("SHOW DATABASES");
    }

    public String showTables() {
        return hiveOperations.queryForString("SHOW TABLES");
    }

    public List<String> selectAllFrom(String table) {
        Resource testScriptResource = resourceLoader.getResource("classpath:hql/query/templates/select_all.hql");

        Map params = new HashMap();
        params.put("table", table);

        HiveScript testScript = new HiveScript(testScriptResource, params);
        return hiveOperations.executeScript(testScript);
    }

    public List<String> selectAllFrom(String table, Integer limit) {
        if (limit != null) {
            Resource testScriptResource = resourceLoader.getResource("classpath:hql/query/templates/select_all_limit.hql");

            Map params = new HashMap();
            params.put("table", table);
            params.put("limit", table);

            HiveScript testScript = new HiveScript(testScriptResource, params);
            return hiveOperations.executeScript(testScript);
        }
        else return selectAllFrom(table);
    }

    public List<String> selectColumn(String table, String column) {
        return null;
    }

    public List<String> selectColumn(String table, String column, Integer limit) {
        return null;
    }

    public List<String> selectColumns(String table, List<String> columns) {
        return null;
    }

    public List<String> selectColumns(String table, List<String> columns, Integer limit) {
        return null;
    }
}
