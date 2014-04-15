package com.poliana.core.pacFinance.repositories;

import com.poliana.core.pacFinance.entities.PacContributionTotalsMap;
import com.poliana.core.pacFinance.mappers.PacContributionTotalsHashMapper;
import com.poliana.core.time.TimeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.security.RolesAllowed;

/**
 * @author David Gilmore
 * @date 1/26/14
 */
@Repository
@RolesAllowed(value = { "RESEARCH", "ADMIN" })
public class PacContributionHadoopRepo {

    private TimeService timeService;

    private JdbcTemplate impalaTemplate;

    private static final Logger logger = Logger.getLogger(PacContributionHadoopRepo.class);


    public PacContributionHadoopRepo() {

        this.timeService = new TimeService();
    }


    /**
     * Get total sums of all money contributed to each legislator during a given congress by a certain
     * pac
     *
     * @return
     * @see com.poliana.core.pacFinance.entities.PacContributionTotalsMap
     */
    public PacContributionTotalsMap getPacContributionTotalsMap(String pacId, int congress) {

        String query =
                "SELECT DISTINCT " +
                "       bioguide_id " +
                "     , pac_id " +
                "     , cmte_nm AS pac_name " +
                "     , congress " +
                "     , total " +
                "FROM " +
                "    (SELECT " +
                "           bioguide_id " +
                "         , pac_id " +
                "         , congress " +
                "         , SUM(amount) as total " +
                "    FROM  " +
                "        crp.pac_to_candidate_contributions   " +
                "    JOIN   " +
                "        entities.legislators   " +
                "    ON   " +
                "        cid = opensecrets_id   " +
                "    WHERE " +
                "        pac_id = \'" + pacId + "\' " +
                "    AND " +
                "        congress = " + congress +
                "    GROUP BY   " +
                "          pac_id   " +
                "        , bioguide_id " +
                "        , congress) totals " +
                "JOIN   " +
                "    entities.pacs    " +
                "ON    " +
                "    cmte_id = pac_id " +
                "WHERE  " +
                "    cycle = " + congress;
        try {

            return impalaTemplate.query(query, new PacContributionTotalsHashMapper(null));
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get total sums of all money contributed to each legislator in a given chamber during a given congress by a certain
     * pac
     *
     * @return
     * @see com.poliana.core.pacFinance.entities.PacContributionTotalsMap
     */
    public PacContributionTotalsMap getPacContributionTotalsMap(String pacId, String chamber, int congress) {

        String legislatorType = chamber.contains("h") ? "rep" : "sen";

        try {
            String query =
                    "SELECT DISTINCT " +
                    "       bioguide_id " +
                    "     , pac_id " +
                    "     , cmte_nm AS pac_name " +
                    "     , congress " +
                    "     , total     " +
                    "FROM " +
                    "    (SELECT " +
                    "           bioguide_id " +
                    "         , pac_id " +
                    "         , congress " +
                    "         , SUM(amount) as total " +
                    "    FROM  " +
                    "        crp.pac_to_candidate_contributions   " +
                    "    JOIN   " +
                    "        entities.legislators_flat_terms   " +
                    "    ON   " +
                    "        cid = opensecrets_id   " +
                    "    WHERE " +
                    "        pac_id = \'" + pacId + "\' " +
                    "    AND " +
                    "        congress = " + congress +
                    "    AND  " +
                    "        term_type = \'" + legislatorType + "\' " +
                    "    GROUP BY   " +
                    "          pac_id   " +
                    "        , bioguide_id " +
                    "        , congress) totals " +
                    "JOIN   " +
                    "    entities.pacs    " +
                    "ON    " +
                    "    cmte_id = pac_id " +
                    "WHERE  " +
                    "    cycle = " + congress;

            return impalaTemplate.query(query, new PacContributionTotalsHashMapper(chamber));
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get total sums of all money contributed to each legislator during a given time range by a certain
     * pac. If a PAC has multiple names, the name listed at the begin timestamp will be used.
     *
     * @param pacId
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public PacContributionTotalsMap getPacContributionTotalsMap(String pacId, long beginTimestamp, long endTimestamp) {

        int congress = timeService.getCongressByTimestamp(beginTimestamp);

        try {
            String query =
                    "SELECT DISTINCT " +
                    "       bioguide_id " +
                    "     , pac_id " +
                    "     , cmte_nm AS pac_name " +
                    "     , total     " +
                    "FROM " +
                    "    (SELECT " +
                    "           bioguide_id " +
                    "         , pac_id " +
                    "         , SUM(amount) as total " +
                    "    FROM  " +
                    "        crp.pac_to_candidate_contributions   " +
                    "    JOIN   " +
                    "        entities.legislators_flat_terms   " +
                    "    ON   " +
                    "        cid = opensecrets_id   " +
                    "    WHERE " +
                    "        pac_id = \'" + pacId + "\' " +
                    "    AND " +
                    "        date_ts > " + beginTimestamp +
                    "    AND  " +
                    "        date_ts < " + endTimestamp +
                    "    GROUP BY   " +
                    "          pac_id   " +
                    "        , bioguide_id) totals " +
                    "JOIN   " +
                    "    entities.pacs    " +
                    "ON    " +
                    "    cmte_id = pac_id " +
                    "AND " +
                    "    cycle = " + congress;

            return impalaTemplate.query(query, new PacContributionTotalsHashMapper(beginTimestamp, endTimestamp));
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get total sums of all money contributed to each legislator in a given chamber during a given time range by a certain
     * pac. If a PAC has multiple names, the name listed at the begin timestamp will be used.
     *
     * @param pacId
     * @param chamber
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public PacContributionTotalsMap getPacContributionTotalsMap(String pacId, String chamber, long beginTimestamp, long endTimestamp) {

        int congress = timeService.getCongressByTimestamp(beginTimestamp);

        String legislatorType = chamber.contains("h") ? "rep" : "sen";

        String query =
                "SELECT DISTINCT " +
                "       bioguide_id " +
                "     , pac_id " +
                "     , cmte_nm AS pac_name " +
                "     , total     " +
                "FROM " +
                "    (SELECT " +
                "           bioguide_id " +
                "         , pac_id " +
                "         , SUM(amount) as total " +
                "    FROM  " +
                "        crp.pac_to_candidate_contributions   " +
                "    JOIN   " +
                "        entities.legislators_flat_terms   " +
                "    ON   " +
                "        cid = opensecrets_id   " +
                "    WHERE " +
                "        pac_id = \'" + pacId + "\' " +
                "    AND " +
                "        date_ts > " + beginTimestamp +
                "    AND  " +
                "        date_ts < " + endTimestamp +
                "    AND " +
                "        term_type = \'" + legislatorType + "\' " +
                "    GROUP BY   " +
                "          pac_id   " +
                "        , bioguide_id) totals " +
                "JOIN   " +
                "    entities.pacs    " +
                "ON    " +
                "    cmte_id = pac_id " +
                "AND " +
                "    cycle = " + congress;
        try {

            return impalaTemplate.query(query, new PacContributionTotalsHashMapper(chamber, beginTimestamp, endTimestamp));
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    @Autowired
    public void setImpalaTemplate(JdbcTemplate impalaTemplate) {
        this.impalaTemplate = impalaTemplate;
    }
}
