package com.poliana.core.pacFinance.repositories;

import com.poliana.core.pacFinance.entities.PacContributionTotalsMap;
import com.poliana.core.pacFinance.mappers.PacContributionTotalsHashMapper;
import com.poliana.core.time.CongressTimestamps;
import com.poliana.core.time.TimeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author David Gilmore
 * @date 1/26/14
 */
@Repository
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

        CongressTimestamps ts = timeService.getCongressTimestamps(congress);

        try {
            String query =
                    "";

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
    public PacContributionTotalsMap getPacContributionTotalsMapByChamber(String pacId, String chamber, int congress) {

        CongressTimestamps ts = timeService.getCongressTimestamps(congress);

        String legislatorType = chamber.contains("h") ? "r" : "s";

        try {
            String query =
                    "";

            return impalaTemplate.query(query, new PacContributionTotalsHashMapper(chamber));
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get total sums of all money contributed to each legislator during a given time range by a certain
     * pac.
     *
     * @param pacId
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public PacContributionTotalsMap getPacContributionTotalsMap(String pacId, long beginTimestamp, long endTimestamp) {

        try {
            String query =
                    "";

            return impalaTemplate.query(query, new PacContributionTotalsHashMapper(beginTimestamp, endTimestamp));
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get total sums of all money contributed to each legislator in a given chamber during a given time range by a certain
     * pac.
     *
     * @param pacId
     * @param chamber
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public PacContributionTotalsMap getPacContributionTotalsMap(String pacId, String chamber, long beginTimestamp, long endTimestamp) {

        String legislatorType = chamber.contains("h") ? "r" : "s";

        try {
            String query =
                    "";

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
