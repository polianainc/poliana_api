package com.poliana.core.politicianFinance;

import com.poliana.core.politicianFinance.entities.IndustryPoliticianContributions;
import com.poliana.core.politicianFinance.mappers.AllContrPerCogressMapper;
import com.poliana.core.politicianFinance.mappers.IndToPolContrTotalsMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @author David Gilmore
 * @date 1/13/14
 */
@Repository
public class PoliticianIndustryHadoopRepo {

    private JdbcTemplate impalaTemplate;

    private static final Logger logger = Logger.getLogger(PoliticianIndustryHadoopRepo.class);


    /**
     * Get a list of industry to politician contribution sums for a given congressional cycle
     * @param bioguideId
     * @return
     */
    public List<IndustryPoliticianContributions> getIndustryToPoliticianContributions(String bioguideId, int congress) {

        try {
            String query =
                    "SELECT " +
                    "     bioguide_id" +
                    "   , real_code" +
                    "   , industry" +
                    "   , sector" +
                    "   , sector_long" +
                    "   , congress" +
                    "   , _c3 " +
                    "FROM " +
                    "   (SELECT " +
                    "         bioguide_id" +
                    "       , real_code" +
                    "       , congress" +
                    "       , SUM(amount) " +
                    "   FROM " +
                    "       (SELECT " +
                    "             bioguide_id" +
                    "           , real_code" +
                    "           , congress" +
                    "           , amount " +
                    "       FROM " +
                    "           entities.legislators m " +
                    "       JOIN " +
                    "           crp.individual_contributions c " +
                    "       ON " +
                    "           opensecrets_id = c.recip_id " +
                    "       WHERE " +
                    "           bioguide_id = \'" + bioguideId + "\' ) candidate_receipts " +
                    "   GROUP BY " +
                    "         bioguide_id" +
                    "       , real_code" +
                    "       , congress ) sums " +
                    "JOIN " +
                    "   entities.industry_codes l " +
                    "ON " +
                    "   real_code = cat_code " +
                    "WHERE congress = " + congress;

            return impalaTemplate.query(query, new IndToPolContrTotalsMapper());
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get a list of industry category to politician contribution sums for a given congressional cycle
     * @param bioguideId
     * @return
     */
    public List<IndustryPoliticianContributions> getIndustryCategoryToPoliticianContributions(String bioguideId, int congress) {

        try {
            String query =
                    "";

            return impalaTemplate.query(query, new IndToPolContrTotalsMapper());
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get a list of industry to politician contribution sums for a given time range
     * @param bioguideId
     * @return
     */
    public List<IndustryPoliticianContributions> getIndustryToPoliticianContributions(
            String bioguideId, long beginTimestamp, long endTimestamp) {

        try {
            String query =
                    "";

            return impalaTemplate.query(query, new IndToPolContrTotalsMapper());
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get a list of industry category to politician contribution sums for a given time range
     * @param bioguideId
     * @return
     */
    public List<IndustryPoliticianContributions> getIndustryCategoryToPoliticianContributions(
            String bioguideId, long beginTimestamp, long endTimestamp) {

        try {
            String query =
                    "";

            return impalaTemplate.query(query, new IndToPolContrTotalsMapper());
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get a list of Industry to politician contribution sums for all industries for all time
     * @param bioguideId
     * @return
     */
    public List<IndustryPoliticianContributions> getIndustryToPoliticianContributions(String bioguideId) {

        try {
            String query =
                    "";

            return impalaTemplate.query(query, new IndToPolContrTotalsMapper());
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get a list of Industry to politician contribution sums for all industries for all time
     * @param bioguideId
     * @return
     */
    public List<IndustryPoliticianContributions> getIndustryCategoryToPoliticianContributions(String bioguideId) {

        try {
            String query =
                    "";

            return impalaTemplate.query(query, new IndToPolContrTotalsMapper());
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get a map of Cycle->Industry to politician contributions from Impala.
     * @param bioguideId
     * @return
     */
    public HashMap<Integer, List<IndustryPoliticianContributions>> getAllIndustryContributionsPerCongress(String bioguideId) {

        try {
            String query =
                    "SELECT " +
                    "     bioguide_id" +
                    "   , real_code" +
                    "   , industry" +
                    "   , sector" +
                    "   , sector_long" +
                    "   , congress" +
                    "   , _c3 " +
                    "FROM " +
                    "   (SELECT " +
                    "         bioguide_id" +
                    "       , real_code" +
                    "       , congress" +
                    "       , SUM(amount) " +
                    "   FROM " +
                    "       (SELECT " +
                    "             bioguide_id" +
                    "           , real_code" +
                    "           , congress" +
                    "           , amount " +
                    "       FROM " +
                    "           entities.legislators m " +
                    "       JOIN " +
                    "           crp.individual_contributions c " +
                    "       ON " +
                    "           opensecrets_id = c.recip_id " +
                    "       WHERE " +
                    "           bioguide_id = \'" + bioguideId + "\') candidate_receipts " +
                    "       GROUP BY " +
                    "             bioguide_id" +
                    "           , real_code" +
                    "           , congress) sums " +
                    "       JOIN " +
                    "           entities.industry_codes l " +
                    "       ON " +
                    "           real_code = cat_code";

            return impalaTemplate.query(query, new AllContrPerCogressMapper());
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
