package config;


import org.apache.hadoop.hive.service.HiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.hadoop.configuration.ConfigurationFactoryBean;
import org.springframework.data.hadoop.hive.HiveClientFactory;
import org.springframework.data.hadoop.hive.HiveClientFactoryBean;
import org.springframework.data.hadoop.hive.HiveServerFactoryBean;
import org.springframework.data.hadoop.hive.HiveTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "poliana.data.warehouse")
@PropertySource(value={"classpath:hadoop.properties"})
public class HiveConfig {

    @Autowired
    Environment env;

    @Bean
    public org.apache.hadoop.conf.Configuration hadoopConfiguration() throws Exception {
        ConfigurationFactoryBean hadoopConfigurationBean = new ConfigurationFactoryBean();
        hadoopConfigurationBean.setJobTrackerUri(env.getProperty("hd.jt"));
        hadoopConfigurationBean.setFileSystemUri(env.getProperty("hd.fs"));
        hadoopConfigurationBean.afterPropertiesSet();
        return hadoopConfigurationBean.getObject();
    }

    @Bean
    Properties hiveProperties() {
        InputStream in = ClassLoader.getSystemResourceAsStream("hive.properties");
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    @Bean
    public HiveClientFactory hiveClientFactory() throws Exception {
        HiveServerFactoryBean hiveServerFactoryBean = new HiveServerFactoryBean();
        hiveServerFactoryBean.setConfiguration(hadoopConfiguration());
        hiveServerFactoryBean.setProperties(hiveProperties());
        hiveServerFactoryBean.setPort(9999);
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

}

