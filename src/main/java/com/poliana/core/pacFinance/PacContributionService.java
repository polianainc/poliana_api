package com.poliana.core.pacFinance;

import com.poliana.core.pacFinance.repositories.PacContributionHadoopRepo;
import com.poliana.core.pacFinance.repositories.PacContributionMongoRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author David Gilmore
 * @date 1/4/14
 */
@Service
public class PacContributionService {

    private PacContributionMongoRepo pacContributionMongoRepo;
    private PacContributionHadoopRepo pacContributionHadoopRepo;

    private static final Logger logger = Logger.getLogger(PacContributionService.class);


    @Autowired
    public void setPacContributionMongoRepo(PacContributionMongoRepo pacContributionMongoRepo) {
        this.pacContributionMongoRepo = pacContributionMongoRepo;
    }

    @Autowired
    public void setPacContributionHadoopRepo(PacContributionHadoopRepo pacContributionHadoopRepo) {
        this.pacContributionHadoopRepo = pacContributionHadoopRepo;
    }
}
