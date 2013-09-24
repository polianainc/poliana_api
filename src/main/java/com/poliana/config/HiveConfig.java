package com.poliana.config;


import org.apache.hive.jdbc.HiveDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
//import org.impalaframework.extension.dataaccess.jdbc.JdbcTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;


import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ComponentScan(basePackages = "{poliana.data.jobs, com.poliana.repositories}")
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
    public DataSource dataSource() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
//        DriverManager.registerDriver((HiveDriver) Class.forName("org.apache.hive.jdbc.HiveDriver").newInstance());
//        String url = env.getProperty("impala.url");
//        Connection c = DriverManager.getConnection(url);
        return new SimpleDriverDataSource(hiveDriver(), env.getProperty("impala.url"));
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }

}