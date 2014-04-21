package com.poliana.config;

import com.rollup.olap.CubeDataRepo;
import com.rollup.olap.HolapClient;
import com.rollup.olap.impl.HolapClientImpl;
import com.rollup.olap.impl.MongoCubeDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author David Gilmore
 * @date 4/20/14
 */
@Configuration
public class RollupConfig {

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
