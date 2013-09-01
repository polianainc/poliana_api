package config;


import org.apache.hadoop.hive.jdbc.HiveDriver;
import org.apache.hadoop.hive.service.HiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.hadoop.hive.HiveClientFactory;
import org.springframework.data.hadoop.hive.HiveClientFactoryBean;
import org.springframework.data.hadoop.hive.HiveServerFactoryBean;
import org.springframework.data.hadoop.hive.HiveTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import poliana.data.repositories.HiveJdbcRepository;
import poliana.data.repositories.HiveThriftRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "{poliana.data.jobs, poliana.data.repositories}")
@PropertySource(value={"classpath:hive.properties"})
public class HiveConfig {

    @Autowired
    Environment env;

    //Thrift Client Config
    @Bean
    public HiveClientFactory hiveClientFactory() throws Exception {
        HiveServerFactoryBean hiveServerFactoryBean = new HiveServerFactoryBean();
        hiveServerFactoryBean.setPort(10000);
        hiveServerFactoryBean.afterPropertiesSet();
        hiveServerFactoryBean.start();
        HiveClientFactoryBean clientFactoryBean = new HiveClientFactoryBean();
        return clientFactoryBean.getObject();
    }

    @Bean
    public HiveTemplate hiveTemplate() throws Exception {
        return new HiveTemplate(hiveClientFactory());
    }

    @Bean
    public HiveClient hiveClient() throws Exception {
        return hiveClientFactory().getHiveClient();
    }

    @Bean
    public HiveThriftRepository hiveRepository() throws Exception {
        return new HiveThriftRepository(hiveClientFactory());
    }

    //JDBC Client Config
    @Bean
    public HiveDriver hiveDriver() {
        return new HiveDriver();
    }

    @Bean
    public DataSource simpleDriverDataSource() {
        return new SimpleDriverDataSource(hiveDriver(), env.getProperty("hive.url"));
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(simpleDriverDataSource());
    }

    @Bean
    public HiveJdbcRepository jdbcRepository() {
        return new HiveJdbcRepository(jdbcTemplate());
    }

}

