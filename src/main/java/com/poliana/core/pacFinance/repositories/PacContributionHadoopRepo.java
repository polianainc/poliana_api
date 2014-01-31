package com.poliana.core.pacFinance.repositories;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author David Gilmore
 * @date 1/26/14
 */
@Repository
public class
        PacContributionHadoopRepo {

    private JdbcTemplate impalaTemplate;

    private static final Logger logger = Logger.getLogger(PacContributionHadoopRepo.class);


    @Autowired
    public void setImpalaTemplate(JdbcTemplate impalaTemplate) {
        this.impalaTemplate = impalaTemplate;
    }
}
