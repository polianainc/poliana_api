package com.poliana.core.politicianFinance.general;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author David Gilmore
 * @date 4/19/14
 */
@Repository
public class PoliticianFinanceRepository {

    private JdbcTemplate impalaTemplate;

    private static final Logger logger = Logger.getLogger(PoliticianFinanceRepository.class);

    public List<Map<String, Object>> getPacAndIndustryTotals() {

        try {
            String query =
                    "SELECT DISTINCT          " +
                            "      pac_contributions.bioguide_id " +
                            "    , individual_contributions   " +
                            "    , pac_contributions         " +
                            "FROM         " +
                            "   " +
                            "  (SELECT         " +
                            "        bioguide_id         " +
                            "      , SUM(amount) as pac_contributions         " +
                            "  FROM         " +
                            "      crp.pac_to_candidate_contributions      " +
                            "  JOIN         " +
                            "        entities.legislators         " +
                            "    ON         " +
                            "        cid = opensecrets_id " +
                            "  WHERE         " +
                            "      type = '24K'                                             " +
                            "  GROUP BY         " +
                            "        bioguide_id         " +
                            "  ) pac_contributions " +
                            "FULL OUTER JOIN   " +
                            "   " +
                            "  (SELECT         " +
                            "        bioguide_id         " +
                            "      , SUM(amount) as individual_contributions         " +
                            "  FROM         " +
                            "      crp.individual_contributions         " +
                            "  JOIN         " +
                            "        entities.legislators         " +
                            "    ON         " +
                            "        recip_id = opensecrets_id " +
                            "  GROUP BY         " +
                            "        bioguide_id ) individual_contributions " +
                            "ON " +
                            "    pac_contributions.bioguide_id = individual_contributions.bioguide_id";

            return impalaTemplate.queryForList(query);
        }
        catch (Exception e) {
            logger.error(e);
        }

        return new LinkedList<>();
    }

    public List<Map<String, Object>> getPacAndIndustryTotalsPerCongress() {

        try {
            String query =
                    "SELECT DISTINCT          " +
                    "      pac_contributions.bioguide_id " +
                    "    , individual_contributions   " +
                    "    , pac_contributions         " +
                    "    , pac_contributions.congress as congress         " +
                    "FROM         " +
                    "   " +
                    "  (SELECT         " +
                    "        bioguide_id         " +
                    "      , SUM(amount) as pac_contributions         " +
                    "      , congress         " +
                    "  FROM         " +
                    "      crp.pac_to_candidate_contributions      " +
                    "  JOIN         " +
                    "        entities.legislators         " +
                    "    ON         " +
                    "        cid = opensecrets_id " +
                    "  WHERE         " +
                    "      type = '24K'                                             " +
                    "  GROUP BY         " +
                    "        bioguide_id         " +
                    "      , congress         " +
                    "  ) pac_contributions " +
                    "FULL OUTER JOIN   " +
                    "   " +
                    "  (SELECT         " +
                    "        bioguide_id         " +
                    "      , SUM(amount) as individual_contributions         " +
                    "      , congress         " +
                    "  FROM         " +
                    "      crp.individual_contributions         " +
                    "  JOIN         " +
                    "        entities.legislators         " +
                    "    ON         " +
                    "        recip_id = opensecrets_id " +
                    "  GROUP BY         " +
                    "        bioguide_id " +
                    "      , congress ) individual_contributions " +
                    "ON " +
                    "    pac_contributions.bioguide_id = individual_contributions.bioguide_id";

            return impalaTemplate.queryForList(query);
        }
        catch (Exception e) {
            logger.error(e);
        }

        return new LinkedList<>();
    }

    @Autowired
    public void setImpalaTemplate(JdbcTemplate impalaTemplate) {
        this.impalaTemplate = impalaTemplate;
    }
}
