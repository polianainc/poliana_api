package config;

import org.apache.hadoop.hbase.client.HTableFactory;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.hadoop.hbase.HbaseConfigurationFactoryBean;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.TableCallback;

//@Configuration
public class HBaseConfig {

    @Bean
    public HbaseTemplate hbaseTemplate() {
        HbaseConfigurationFactoryBean hbaseConfigurationFactoryBean = new HbaseConfigurationFactoryBean();
        hbaseConfigurationFactoryBean.setZkPort(2181);
        hbaseConfigurationFactoryBean.setZkQuorum("localhost");
        hbaseConfigurationFactoryBean.afterPropertiesSet();
        org.apache.hadoop.conf.Configuration conf = hbaseConfigurationFactoryBean.getObject();
        HbaseTemplate hbaseTemplate = new HbaseTemplate(conf);

        return hbaseTemplate;
    }
}
