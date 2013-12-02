package com.poliana.config;

import org.apache.hive.jdbc.HiveDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.*;

/**
 * @author David Gilmore
 * @date 10/19/13
 */
@ComponentScan(basePackages = "com.poliana.core")
@PropertySource(value={"classpath:impala.properties"})
public class ImpalaConfig {

    @Autowired
    Environment env;

    //JDBC Client Config
    @Bean
    public HiveDriver impalaDriver() {
        return new HiveDriver();
    }

    @Bean
    public DataSource impalaDataSource() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        return new SimpleDriverDataSource(impalaDriver(), env.getProperty("impala.url"));
    }

    @Bean
    public JdbcTemplate impalaTemplate() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        JdbcTemplate impalaTemplate = new JdbcTemplate(impalaDataSource());
        return impalaTemplate;
    }
}
