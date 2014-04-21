package com.poliana.config;

import com.rollup.olap.HolapClient;
import com.rollup.olap.impl.HolapClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author David Gilmore
 * @date 4/20/14
 */
@Configuration
public class RollupConfig {

    @Bean
    public HolapClient holapClient() {
        return  new HolapClientImpl();
    }
}
