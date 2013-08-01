package poliana.data.jobs;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.data.hadoop.hive.HiveScript;
import org.springframework.stereotype.Component;
import poliana.data.warehouse.HiveBase;

@Component
public class BootstrapHive extends HiveBase implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        FileSystemResourceLoader resourceLoader = new FileSystemResourceLoader();

        Resource dropScriptResource = resourceLoader.getResource("classpath:hql/drop_tables.hql");
        Resource createScriptResource = resourceLoader.getResource("classpath:hql/create_database.hql");

        HiveScript dropScript = new HiveScript(dropScriptResource);
        HiveScript createScript = new HiveScript(createScriptResource);

        hiveTemplate.executeScript(dropScript);
        hiveTemplate.executeScript(createScript);
    }
}
