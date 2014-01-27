package com.poliana.core.politicianFinance.pacs;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author David Gilmore
 * @date 1/26/14
 */
@Service
public class PoliticianPacFinanceService {

    private PoliticianPacMongoRepo politicianPacMongoRepo;
    private PoliticianPacHadoopRepo politicianPacHadoopRepo;

    private static final Logger logger = Logger.getLogger(PoliticianPacFinanceService.class);


    @Autowired
    public void setPoliticianPacMongoRepo(PoliticianPacMongoRepo politicianPacMongoRepo) {
        this.politicianPacMongoRepo = politicianPacMongoRepo;
    }

    @Autowired
    public void setPoliticianPacHadoopRepo(PoliticianPacHadoopRepo politicianPacHadoopRepo) {
        this.politicianPacHadoopRepo = politicianPacHadoopRepo;
    }
}
