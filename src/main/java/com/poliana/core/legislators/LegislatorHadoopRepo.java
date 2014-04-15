package com.poliana.core.legislators;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author David Gilmore
 * @date 2/28/14
 */
@Repository
public class LegislatorHadoopRepo {

    private JdbcTemplate impalaTemplate;

    private static final Logger logger = Logger.getLogger(LegislatorHadoopRepo.class);


    /**
     * Fetch all legislator objects from Impala for every term he or she has served.
     * @return
     */
    public List<Legislator> getAllLegistlatorTerms() {

        String query = "SELECT * FROM entities.legislators_flat_terms";
        return impalaTemplate.query(query, new LegislatorMapper());
    }

    @Autowired
    public void setImpalaTemplate(JdbcTemplate impalaTemplate) {
        this.impalaTemplate = impalaTemplate;
    }
}
