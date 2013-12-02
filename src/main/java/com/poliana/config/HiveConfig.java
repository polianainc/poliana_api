package com.poliana.config;


import org.apache.hive.jdbc.HiveDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;


import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author David Gilmore
 * @date 09/03/13
 */
@ComponentScan(basePackages = "com.poliana.core")
@PropertySource(value={"classpath:hive.properties"})
public class HiveConfig {

    @Autowired
    Environment env;

    //JDBC Client Config
    @Bean
    public HiveDriver hiveDriver() {
        return new HiveDriver();
    }

    @Bean
    public DataSource hiveDataSource() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        return new SimpleDriverDataSource(hiveDriver(), env.getProperty("hive.url"));
    }

    @Bean
    public JdbcTemplate hiveTemplate() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        JdbcTemplate hiveTemplate = new JdbcTemplate(hiveDataSource());
        return hiveTemplate;
    }

}