package com.poliana.config;

import com.locke.olap.CubeDataRepo;
import com.locke.olap.HolapClient;
import com.locke.olap.impl.HolapClientImpl;
import com.locke.olap.impl.MongoCubeDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author David Gilmore
 * @date 4/20/14
 */
@Configuration
public class LockeConfig {

    @Autowired
    MongoConfig mongoConfig;

    @Bean
    public HolapClient holapClient() {
        return  new HolapClientImpl();
    }

    @Bean
    public CubeDataRepo cubeDataRepo() throws Exception {

        MongoCubeDataRepo cubeDataRepo = new MongoCubeDataRepo();
        cubeDataRepo.setMongoDb(mongoConfig.mongoDb());
        return cubeDataRepo;
    }
}
