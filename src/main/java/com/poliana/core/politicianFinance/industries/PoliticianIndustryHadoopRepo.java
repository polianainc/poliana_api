package com.poliana.core.politicianFinance.industries;

import com.poliana.core.time.TimeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author David Gilmore
 * @date 1/13/14
 */
@Repository
@RolesAllowed(value = { "RESEARCH", "ADMIN" })
public class PoliticianIndustryHadoopRepo {

    private TimeService timeService;

    private JdbcTemplate impalaTemplate;

    private static final Logger logger = Logger.getLogger(PoliticianIndustryHadoopRepo.class);


    public PoliticianIndustryHadoopRepo() {
        this.timeService = new TimeService();
    }

    /**
     * Get a list of Industry to politician contribution sums for all industries for all time
     * @param bioguideId
     * @return
     */
    public List<Map<String, Object>> getIndustryToAllPoliticianContributions(String bioguideId) {

        try {
            String query =
                    "SELECT DISTINCT" +
                            "       bioguide_id" +
                            "     , first_name " +
                            "     , last_name " +
                            "     , industry_id" +
                            "     , party" +
                            "     , religion" +
                            "     , industry" +
                            "     , sector" +
                            "     , sector_long" +
                            "     , contribution_count" +
                            "     , contribution_sum " +
                            "FROM" +
                            "     (SELECT " +
                            "            bioguide_id" +
                            "          , first_name " +
                            "          , last_name " +
                            "          , cat_order as industry_id" +
                            "          , party" +
                            "          , religion" +
                            "          , SUM(contribution_count) as contribution_count" +
                            "          , SUM(contribution_sum) as contribution_sum      " +
                            "     FROM" +
                            "          (SELECT" +
                            "                 bioguide_id" +
                            "               , first_name " +
                            "               , last_name " +
                            "               , real_code" +
                            "               , party" +
                            "               , religion" +
                            "               , COUNT(amount) as contribution_count" +
                            "               , SUM(amount) as contribution_sum" +
                            "          FROM" +
                            "               entities.legislators m" +
                            "          JOIN" +
                            "               crp.individual_contributions c" +
                            "          ON" +
                            "               opensecrets_id = c.recip_id " +
                            "          WHERE " +
                            "               bioguide_id = \'" + bioguideId + "\'" +
                            "          GROUP BY" +
                            "                 bioguide_id" +
                            "               , first_name " +
                            "               , last_name " +
                            "               , real_code" +
                            "               , party" +
                            "               , religion " +
                            "          ) q1" +
                            "     JOIN" +
                            "          entities.industry_codes l" +
                            "     ON" +
                            "          real_code = cat_code " +
                            "     GROUP BY" +
                            "            bioguide_id" +
                            "          , first_name " +
                            "          , last_name " +
                            "          , cat_order" +
                            "          , party" +
                            "          , religion " +
                            "     ) distinct_sums " +
                            "JOIN" +
                            "     entities.industry_codes l " +
                            "ON" +
                            "     industry_id = cat_order";

            return impalaTemplate.queryForList(query);
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
    public List<PoliticianIndustryContributionsTotals> getIndustryToPoliticianContributions(String bioguideId) {

        try {
            String query =
                    "SELECT DISTINCT" +
                            "       bioguide_id" +
                            "     , first_name " +
                            "     , last_name " +
                            "     , industry_id" +
                            "     , party" +
                            "     , religion" +
                            "     , industry" +
                            "     , sector" +
                            "     , sector_long" +
                            "     , contribution_count" +
                            "     , contribution_sum " +
                            "FROM" +
                            "     (SELECT " +
                            "            bioguide_id" +
                            "          , first_name " +
                            "          , last_name " +
                            "          , cat_order as industry_id" +
                            "          , party" +
                            "          , religion" +
                            "          , SUM(contribution_count) as contribution_count" +
                            "          , SUM(contribution_sum) as contribution_sum      " +
                            "     FROM" +
                            "          (SELECT" +
                            "                 bioguide_id" +
                            "               , first_name " +
                            "               , last_name " +
                            "               , real_code" +
                            "               , party" +
                            "               , religion" +
                            "               , COUNT(amount) as contribution_count" +
                            "               , SUM(amount) as contribution_sum" +
                            "          FROM" +
                            "               entities.legislators m" +
                            "          JOIN" +
                            "               crp.individual_contributions c" +
                            "          ON" +
                            "               opensecrets_id = c.recip_id " +
                            "          WHERE " +
                            "               bioguide_id = \'" + bioguideId + "\'" +
                            "          GROUP BY" +
                            "                 bioguide_id" +
                            "               , first_name " +
                            "               , last_name " +
                            "               , real_code" +
                            "               , party" +
                            "               , religion " +
                            "          ) q1" +
                            "     JOIN" +
                            "          entities.industry_codes l" +
                            "     ON" +
                            "          real_code = cat_code " +
                            "     GROUP BY" +
                            "            bioguide_id" +
                            "          , first_name " +
                            "          , last_name " +
                            "          , cat_order" +
                            "          , party" +
                            "          , religion " +
                            "     ) distinct_sums " +
                            "JOIN" +
                            "     entities.industry_codes l " +
                            "ON" +
                            "     industry_id = cat_order";

            return impalaTemplate.query(query, new PoliticianIndustryContributionTotalsMapper());
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
    public List<PoliticianIndustryContributionsTotals> getIndustryCategoryToPoliticianContributions(String bioguideId) {

        try {
            String query =
                    "SELECT  " +
                            "     bioguide_id" +
                            "   , first_name " +
                            "   , last_name " +
                            "   , real_code as category_id" +
                            "   , party" +
                            "   , religion" +
                            "   , industry" +
                            "   , sector" +
                            "   , sector_long" +
                            "   , cat_name as category_name" +
                            "   , contribution_count " +
                            "   , contribution_sum " +
                            "FROM  " +
                            "   (SELECT  " +
                            "         bioguide_id " +
                            "       , first_name " +
                            "       , last_name " +
                            "       , real_code  " +
                            "       , party" +
                            "       , religion" +
                            "       , COUNT(amount) as contribution_count" +
                            "       , SUM(amount) as contribution_sum" +
                            "   FROM  " +
                            "       (SELECT  " +
                            "             bioguide_id " +
                            "           , first_name " +
                            "           , last_name " +
                            "           , real_code " +
                            "           , party" +
                            "           , religion" +
                            "           , amount  " +
                            "       FROM  " +
                            "           entities.legislators m  " +
                            "       JOIN  " +
                            "           crp.individual_contributions c  " +
                            "       ON  " +
                            "           opensecrets_id = c.recip_id ) candidate_receipts  " +
                            "       WHERE bioguide_id = \'" + bioguideId + "\'" +
                            "   GROUP BY  " +
                            "         bioguide_id " +
                            "       , first_name " +
                            "       , last_name " +
                            "       , real_code " +
                            "       , party" +
                            "       , religion    " +
                            "       ) sums  " +
                            "JOIN  " +
                            "   entities.industry_codes l  " +
                            "ON  " +
                            "   real_code = cat_code";

            return impalaTemplate.query(query, new PoliticianIndustryContributionTotalsMapper());
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get a list of industry to politician contribution sums for a given congressional cycle
     * @param bioguideId
     * @return
     */
    public List<PoliticianIndustryContributionsTotals> getIndustryToPoliticianContributions(String bioguideId, int congress) {

        try {
            String query =
                    "SELECT DISTINCT " +
                    "       bioguide_id " +
                    "     , first_name " +
                    "     , last_name " +
                    "     , industry_id " +
                    "     , party " +
                    "     , religion " +
                    "     , congress " +
                    "     , industry " +
                    "     , sector " +
                    "     , sector_long " +
                    "     , contribution_count " +
                    "     , contribution_sum " +
                    "FROM " +
                    "     (SELECT  " +
                    "            bioguide_id " +
                    "          , cat_order as industry_id " +
                    "          , first_name " +
                    "          , last_name " +
                    "          , party " +
                    "          , religion " +
                    "          , congress " +
                    "          , SUM(contribution_count) as contribution_count " +
                    "          , SUM(contribution_sum) as contribution_sum       " +
                    "     FROM " +
                    "          (SELECT " +
                    "                 bioguide_id " +
                    "               , first_name " +
                    "               , last_name " +
                    "               , real_code " +
                    "               , party " +
                    "               , religion " +
                    "               , congress " +
                    "               , COUNT(amount) as contribution_count " +
                    "               , SUM(amount) as contribution_sum " +
                    "          FROM " +
                    "               entities.legislators m " +
                    "          JOIN " +
                    "               crp.individual_contributions c " +
                    "          ON " +
                    "               opensecrets_id = c.recip_id  " +
                    "          WHERE  " +
                    "               bioguide_id = \'" + bioguideId + "\' " +
                    "          AND " +
                    "               congress = " + congress +
                    "          GROUP BY " +
                    "                 bioguide_id " +
                    "               , first_name " +
                    "               , last_name " +
                    "               , real_code " +
                    "               , party " +
                    "               , religion  " +
                    "               , congress  " +
                    "          ) q1 " +
                    "     JOIN " +
                    "          entities.industry_codes l " +
                    "     ON " +
                    "          real_code = cat_code  " +
                    "     GROUP BY " +
                    "            bioguide_id " +
                    "          , first_name " +
                    "          , last_name " +
                    "          , cat_order " +
                    "          , party " +
                    "          , religion  " +
                    "          , congress  " +
                    "     ) distinct_sums " +
                    "JOIN " +
                    "     entities.industry_codes l " +
                    "ON " +
                    "     industry_id = cat_order";

            List<PoliticianIndustryContributionsTotals> contributionsList =
                    impalaTemplate.query(query, new PoliticianIndustryContributionTotalsMapper());

            logger.info("Industry contribution sums to " + bioguideId + " for congress " + congress + "returned from Impala");

            return contributionsList;
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
    public List<PoliticianIndustryContributionsTotals> getIndustryCategoryToPoliticianContributions(String bioguideId, int congress) {

        try {
            String query =
                    "SELECT " +
                    "     bioguide_id" +
                    "   , first_name " +
                    "   , last_name " +
                    "   , real_code as category_id" +
                    "   , party" +
                    "   , religion" +
                    "   , industry" +
                    "   , sector" +
                    "   , sector_long" +
                    "   , cat_name as category_name" +
                    "   , congress" +
                    "   , contribution_count " +
                    "   , contribution_sum " +
                    "FROM " +
                    "   (SELECT " +
                    "         bioguide_id" +
                    "       , first_name " +
                    "       , last_name " +
                    "       , real_code" +
                    "       , party" +
                    "       , religion" +
                    "       , congress" +
                    "       , COUNT(amount) as contribution_count" +
                    "       , SUM(amount) as contribution_sum" +
                    "   FROM " +
                    "       (SELECT " +
                    "             bioguide_id" +
                    "           , first_name " +
                    "           , last_name " +
                    "           , real_code" +
                    "           , party" +
                    "           , religion" +
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
                    "       , first_name " +
                    "       , last_name " +
                    "       , real_code" +
                    "       , party" +
                    "       , religion" +
                    "       , congress ) sums " +
                    "JOIN " +
                    "   entities.industry_codes l " +
                    "ON " +
                    "   real_code = cat_code " +
                    "WHERE congress = " + congress;

            List<PoliticianIndustryContributionsTotals> contributionsList =
                    impalaTemplate.query(query, new PoliticianIndustryContributionTotalsMapper());

            logger.info("Industry category contribution sums to " + bioguideId + " for all time returned from Impala");

            return contributionsList;
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
    public List<PoliticianIndustryContributionsTotals> getIndustryToPoliticianContributions(String bioguideId, long beginTimestamp, long endTimestamp) {

        try {
            String query =
                    "SELECT DISTINCT " +
                    "       bioguide_id " +
                    "     , first_name " +
                    "     , last_name " +
                    "     , industry_id " +
                    "     , party " +
                    "     , religion " +
                    "     , industry " +
                    "     , sector " +
                    "     , sector_long " +
                    "     , contribution_count " +
                    "     , contribution_sum " +
                    "FROM " +
                    "     (SELECT  " +
                    "            bioguide_id " +
                    "          , first_name " +
                    "          , last_name " +
                    "          , cat_order as industry_id " +
                    "          , party " +
                    "          , religion " +
                    "          , SUM(contribution_count) as contribution_count " +
                    "          , SUM(contribution_sum) as contribution_sum       " +
                    "     FROM " +
                    "          (SELECT " +
                    "                 bioguide_id " +
                    "               , first_name " +
                    "               , last_name " +
                    "               , real_code " +
                    "               , party " +
                    "               , religion " +
                    "               , COUNT(amount) as contribution_count " +
                    "               , SUM(amount) as contribution_sum " +
                    "          FROM " +
                    "               entities.legislators m " +
                    "          JOIN " +
                    "               crp.individual_contributions c " +
                    "          ON " +
                    "               opensecrets_id = c.recip_id  " +
                    "          WHERE  " +
                    "               bioguide_id = \'" + bioguideId + "\' " +
                    "          AND " +
                    "               transaction_ts > " + beginTimestamp +
                    "          AND  " +
                    "               transaction_ts < " + endTimestamp +
                    "          GROUP BY " +
                    "                 bioguide_id " +
                    "               , first_name " +
                    "               , last_name " +
                    "               , real_code " +
                    "               , party " +
                    "               , religion  " +
                    "          ) q1 " +
                    "     JOIN " +
                    "          entities.industry_codes l " +
                    "     ON " +
                    "          real_code = cat_code  " +
                    "     GROUP BY " +
                    "            bioguide_id " +
                    "          , first_name " +
                    "          , last_name " +
                    "          , cat_order " +
                    "          , party " +
                    "          , religion  " +
                    "     ) distinct_sums " +
                    "JOIN " +
                    "     entities.industry_codes l " +
                    "ON " +
                    "     industry_id = cat_order";

            List<PoliticianIndustryContributionsTotals> contributionsList =
                    impalaTemplate.query(query, new PoliticianIndustryContributionTotalsMapper(beginTimestamp, endTimestamp));

            logger.info("Industry contribution sums to " + bioguideId + " from " + beginTimestamp + " to " + endTimestamp + " returned from Impala");

            return contributionsList;
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
    public List<PoliticianIndustryContributionsTotals> getIndustryCategoryToPoliticianContributions(String bioguideId, long beginTimestamp, long endTimestamp) {

        try {
            String query =
                    "SELECT  " +
                    "     bioguide_id" +
                    "   , first_name " +
                    "   , last_name " +
                    "   , real_code as category_id" +
                    "   , party" +
                    "   , religion" +
                    "   , industry" +
                    "   , sector" +
                    "   , sector_long" +
                    "   , cat_name as category_name" +
                    "   , contribution_count " +
                    "   , contribution_sum " +
                    "FROM  " +
                    "   (SELECT  " +
                    "         bioguide_id " +
                    "       , first_name " +
                    "       , last_name " +
                    "       , real_code  " +
                    "       , party" +
                    "       , religion" +
                    "       , COUNT(amount) as contribution_count" +
                    "       , SUM(amount) as contribution_sum" +
                    "   FROM  " +
                    "       (SELECT  " +
                    "             bioguide_id " +
                    "           , first_name " +
                    "           , last_name " +
                    "           , real_code " +
                    "           , party" +
                    "           , religion" +
                    "           , amount  " +
                    "       FROM  " +
                    "           entities.legislators m  " +
                    "       JOIN  " +
                    "           crp.individual_contributions c  " +
                    "       ON  " +
                    "           opensecrets_id = c.recip_id " +
                    "       WHERE " +
                    "           transaction_ts > " + beginTimestamp +
                    "       AND  " +
                    "           transaction_ts < " + endTimestamp +
                    "   ) candidate_receipts  " +
                    "       WHERE " +
                    "               bioguide_id = \'" + bioguideId + "\'" +
                    "   GROUP BY  " +
                    "         bioguide_id " +
                    "       , first_name " +
                    "       , last_name " +
                    "       , real_code " +
                    "       , party" +
                    "       , religion    " +
                    "       ) sums  " +
                    "JOIN  " +
                    "   entities.industry_codes l  " +
                    "ON  " +
                    "   real_code = cat_code";

            List<PoliticianIndustryContributionsTotals> contributionsList =
                    impalaTemplate.query(query, new PoliticianIndustryContributionTotalsMapper(beginTimestamp, endTimestamp));

            logger.info("Industry category contribution sums to " + bioguideId + " from " + beginTimestamp + " to " + endTimestamp + " returned from Impala");

            return contributionsList;
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get a map of Cycle->Industry-Category to politician contributions from Impala.
     * @param bioguideId
     * @return
     */
    public HashMap<Integer, List<PoliticianIndustryContributionsTotals>> getIndustryToPoliticianTotalsPerCongress(String bioguideId) {

        try {
            String query =
                    "SELECT DISTINCT" +
                    "       bioguide_id" +
                    "     , first_name " +
                    "     , last_name " +
                    "     , industry_id" +
                    "     , party" +
                    "     , religion" +
                    "     , congress" +
                    "     , industry" +
                    "     , sector" +
                    "     , sector_long" +
                    "     , contribution_count" +
                    "     , contribution_sum " +
                    "FROM" +
                    "     (SELECT " +
                    "            bioguide_id" +
                    "          , first_name " +
                    "          , last_name " +
                    "          , cat_order as industry_id" +
                    "          , party" +
                    "          , religion" +
                    "          , congress" +
                    "          , SUM(contribution_count) as contribution_count" +
                    "          , SUM(contribution_sum) as contribution_sum      " +
                    "     FROM" +
                    "          (SELECT" +
                    "                 bioguide_id" +
                    "               , first_name " +
                    "               , last_name " +
                    "               , real_code" +
                    "               , party" +
                    "               , religion" +
                    "               , congress" +
                    "               , COUNT(amount) as contribution_count" +
                    "               , SUM(amount) as contribution_sum" +
                    "          FROM" +
                    "               entities.legislators m" +
                    "          JOIN" +
                    "               crp.individual_contributions c" +
                    "          ON" +
                    "               opensecrets_id = c.recip_id " +
                    "          WHERE " +
                    "               bioguide_id = \'" + bioguideId + "\'" +
                    "          GROUP BY" +
                    "                 bioguide_id" +
                    "               , first_name " +
                    "               , last_name " +
                    "               , real_code" +
                    "               , party" +
                    "               , religion " +
                    "               , congress " +
                    "          ) q1" +
                    "     JOIN" +
                    "          entities.industry_codes l" +
                    "     ON" +
                    "          real_code = cat_code " +
                    "     GROUP BY" +
                    "            bioguide_id" +
                    "          , first_name " +
                    "          , last_name " +
                    "          , cat_order" +
                    "          , party" +
                    "          , religion " +
                    "          , congress " +
                    "     ) distinct_sums " +
                    "JOIN" +
                    "     entities.industry_codes l " +
                    "ON" +
                    "     industry_id = cat_order";

            return impalaTemplate.query(query, new IndustryContributionsPerCogressMapper());
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get a map of Cycle->Industry-Category to politician contributions from Impala.
     * @param bioguideId
     * @return
     */
    public HashMap<Integer, List<PoliticianIndustryContributionsTotals>> getIndustryCategoryToPoliticianTotalsPerCongress(String bioguideId) {


        String query =
                "SELECT  " +
                "     bioguide_id" +
                "   , first_name " +
                "   , last_name " +
                "   , real_code as category_id" +
                "   , party" +
                "   , religion" +
                "   , congress" +
                "   , industry" +
                "   , sector" +
                "   , sector_long" +
                "   , cat_name as category_name" +
                "   , contribution_count " +
                "   , contribution_sum " +
                "FROM  " +
                "   (SELECT  " +
                "         bioguide_id " +
                "       , first_name " +
                "       , last_name " +
                "       , real_code  " +
                "       , party" +
                "       , religion" +
                "       , congress" +
                "       , COUNT(amount) as contribution_count" +
                "       , SUM(amount) as contribution_sum" +
                "   FROM  " +
                "       (SELECT  " +
                "             bioguide_id " +
                "           , first_name " +
                "           , last_name " +
                "           , real_code " +
                "           , party" +
                "           , religion" +
                "           , congress" +
                "           , amount  " +
                "       FROM  " +
                "           entities.legislators m  " +
                "       JOIN  " +
                "           crp.individual_contributions c  " +
                "       ON  " +
                "           opensecrets_id = c.recip_id ) candidate_receipts  " +
                "       WHERE bioguide_id = \'" + bioguideId + "\'" +
                "   GROUP BY  " +
                "         bioguide_id " +
                "       , first_name " +
                "       , last_name " +
                "       , real_code " +
                "       , party " +
                "       , religion " +
                "       , congress " +
                "       ) sums  " +
                "JOIN  " +
                "   entities.industry_codes l  " +
                "ON  " +
                "   real_code = cat_code";

        try {

            return impalaTemplate.query(query, new IndustryContributionsPerCogressMapper());
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get a map of Cycle->Industry-Category to politician contributions from Impala.
     * @param bioguideId
     * @return
     */
    public HashMap<Integer, List<PoliticianIndustryContributionsTotals>> getIndustryToPoliticianTotalsPerCongress(String bioguideId, long beginTimestamp, long endTimestamp) {

        Integer[] cycles = timeService.getCongressionalCyclesByTimeRange(beginTimestamp, endTimestamp);

        String cyclesList = "";

        if (cycles.length > 0) {
            int i = 0;
            for (; i < cycles.length-1; i++)
                cyclesList += cycles[i] + ",";
            cyclesList += cycles[i];
        }

        try {
            String query =
                    "SELECT DISTINCT " +
                    "       bioguide_id " +
                    "     , first_name " +
                    "     , last_name " +
                    "     , industry_id " +
                    "     , party " +
                    "     , religion " +
                    "     , congress " +
                    "     , industry " +
                    "     , sector " +
                    "     , sector_long " +
                    "     , contribution_count " +
                    "     , contribution_sum " +
                    "FROM " +
                    "     (SELECT  " +
                    "            bioguide_id " +
                    "          , first_name " +
                    "          , last_name " +
                    "          , cat_order as industry_id " +
                    "          , party " +
                    "          , religion " +
                    "          , congress " +
                    "          , COUNT(contribution_count) as contribution_count " +
                    "          , SUM(contribution_sum) as contribution_sum       " +
                    "     FROM " +
                    "          (SELECT " +
                    "                 bioguide_id " +
                    "               , first_name " +
                    "               , last_name " +
                    "               , real_code " +
                    "               , party " +
                    "               , religion " +
                    "               , congress " +
                    "               , COUNT(amount) as contribution_count " +
                    "               , SUM(amount) as contribution_sum " +
                    "          FROM " +
                    "               entities.legislators m " +
                    "          JOIN " +
                    "               crp.individual_contributions c " +
                    "          ON " +
                    "               opensecrets_id = c.recip_id  " +
                    "          WHERE  " +
                    "               bioguide_id = \'" + bioguideId + "\' " +
                    "          AND " +
                    "               congress in (" + cyclesList + ") " +
                    "          GROUP BY " +
                    "                 bioguide_id " +
                    "               , first_name " +
                    "               , last_name " +
                    "               , real_code " +
                    "               , party " +
                    "               , religion  " +
                    "               , congress  " +
                    "          ) q1 " +
                    "     JOIN " +
                    "          entities.industry_codes l " +
                    "     ON " +
                    "          real_code = cat_code  " +
                    "     GROUP BY " +
                    "            bioguide_id " +
                    "          , first_name " +
                    "          , last_name " +
                    "          , cat_order " +
                    "          , party " +
                    "          , religion  " +
                    "          , congress " +
                    "     ) distinct_sums " +
                    "JOIN " +
                    "     entities.industry_codes l " +
                    "ON " +
                    "     industry_id = cat_order";

            return impalaTemplate.query(query, new IndustryContributionsPerCogressMapper());
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get a map of Cycle->Industry-Category to politician contributions from Impala.
     * @param bioguideId
     * @return
     */
    public HashMap<Integer, List<PoliticianIndustryContributionsTotals>> getIndustryCategoryToPoliticianTotalsPerCongress(String bioguideId, long beginTimestamp, long endTimestamp) {

        Integer[] cycles = timeService.getCongressionalCyclesByTimeRange(beginTimestamp, endTimestamp);

        String cyclesList = "";

        if (cycles.length > 0) {
            int i = 0;
            for (; i < cycles.length-1; i++)
                cyclesList += cycles[i] + ",";
            cyclesList += cycles[i];
        }

        try {
            String query =
                    "SELECT  " +
                    "     bioguide_id" +
                    "   , first_name " +
                    "   , last_name " +
                    "   , real_code as category_id" +
                    "   , party" +
                    "   , religion" +
                    "   , congress" +
                    "   , industry" +
                    "   , sector" +
                    "   , sector_long" +
                    "   , cat_name as category_name" +
                    "   , contribution_count " +
                    "   , contribution_sum " +
                    "FROM  " +
                    "   (SELECT  " +
                    "         bioguide_id " +
                    "       , first_name " +
                    "       , last_name " +
                    "       , real_code  " +
                    "       , party" +
                    "       , religion" +
                    "       , congress" +
                    "       , COUNT(amount) as contribution_count" +
                    "       , SUM(amount) as contribution_sum" +
                    "   FROM  " +
                    "       (SELECT  " +
                    "             bioguide_id " +
                    "           , first_name " +
                    "           , last_name " +
                    "           , real_code " +
                    "           , party" +
                    "           , religion" +
                    "           , congress" +
                    "           , amount  " +
                    "       FROM  " +
                    "           entities.legislators m  " +
                    "       JOIN  " +
                    "           crp.individual_contributions c  " +
                    "       ON  " +
                    "           opensecrets_id = c.recip_id " +
                    "       WHERE " +
                    "               congress in (" + cyclesList + ") " +
                    "   ) candidate_receipts  " +
                    "       WHERE " +
                    "               bioguide_id = \'" + bioguideId + "\'" +
                    "   GROUP BY  " +
                    "         bioguide_id " +
                    "       , first_name " +
                    "       , last_name " +
                    "       , real_code " +
                    "       , party" +
                    "       , religion " +
                    "       , congress " +
                    "       ) sums  " +
                    "JOIN  " +
                    "   entities.industry_codes l  " +
                    "ON  " +
                    "   real_code = cat_code";

            return impalaTemplate.query(query, new IndustryContributionsPerCogressMapper());
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
