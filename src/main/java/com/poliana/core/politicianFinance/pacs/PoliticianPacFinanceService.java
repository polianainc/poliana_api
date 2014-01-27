package com.poliana.core.politicianFinance.pacs;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author David Gilmore
 * @date 1/26/14
 */
@Service
public class PoliticianPacFinanceService {

    private PoliticianPacMongoRepo politicianPacMongoRepo;
    private PoliticianPacHadoopRepo politicianPacHadoopRepo;

    private static final Logger logger = Logger.getLogger(PoliticianPacFinanceService.class);


    public List<PoliticianPacContributionsTotals> getPacToPoliticianTotals(String bioguideId) {

        return null;
    }

    public List<PoliticianPacContributionsTotals> getPacToPoliticianTotals(String bioguideId, long beginTimestamp, long endTimestamp) {

        return null;
    }

    @Autowired
    public void setPoliticianPacMongoRepo(PoliticianPacMongoRepo politicianPacMongoRepo) {
        this.politicianPacMongoRepo = politicianPacMongoRepo;
    }

    @Autowired
    public void setPoliticianPacHadoopRepo(PoliticianPacHadoopRepo politicianPacHadoopRepo) {
        this.politicianPacHadoopRepo = politicianPacHadoopRepo;
    }
}
