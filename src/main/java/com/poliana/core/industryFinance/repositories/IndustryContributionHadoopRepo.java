package com.poliana.core.industryFinance.repositories;

import com.poliana.core.industryFinance.entities.IndustryContributionTotalsMap;
import com.poliana.core.industryFinance.mapppers.IndustryContributionTotalsHashMapper;
import com.poliana.core.politicianFinance.industries.PoliticianIndustryContributionTotals;
import com.poliana.core.politicianFinance.industries.PoliticianIndustryContributionTotalsMapper;
import com.poliana.core.time.CongressTimestamps;
import com.poliana.core.time.TimeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author David Gilmore
 * @date 11/27/13
 */
@Repository
public class IndustryContributionHadoopRepo {

    private JdbcTemplate impalaTemplate;

    private TimeService timeService;

    private static final Logger logger = Logger.getLogger(IndustryContributionHadoopRepo.class);


    public IndustryContributionHadoopRepo() {
        this.timeService = new TimeService();
    }

    /**
     * Get total sums of all money contributed to each legislator during a given congress by a certain
     * industry category.
     *
     * @return
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotalsMap
     */
    public IndustryContributionTotalsMap getIndustryContributionTotalsMap(String industryId, int congress) {

        CongressTimestamps ts = timeService.getCongressTimestamps(congress);

        try {
            String query =
                    "SELECT  " +
                    "      bioguide_id " +
                    "    , cat_order as industry_id " +
                    "    , industry " +
                    "    , cat_name " +
                    "    , sector " +
                    "    , sector_long " +
                    "    , congress " +
                    "    , SUM(amount) as total     " +
                    "FROM " +
                    "    (SELECT  " +
                    "          bioguide_id " +
                    "        , first_name " +
                    "        , last_name " +
                    "        , cat_order " +
                    "        , industry " +
                    "        , cat_name " +
                    "        , sector " +
                    "        , sector_long " +
                    "        , congress " +
                    "        , amount " +
                    "    FROM      " +
                    "        (SELECT  " +
                    "              recip_id   " +
                    "            , cat_order " +
                    "            , industry " +
                    "            , cat_name " +
                    "            , sector " +
                    "            , sector_long " +
                    "            , congress " +
                    "            , amount " +
                    "        FROM  " +
                    "            crp.individual_contributions contributions " +
                    "        JOIN  " +
                    "            entities.industry_codes industries " +
                    "        ON  " +
                    "            real_code = cat_code " +
                    "        WHERE  " +
                    "            cat_order = \'" + industryId + "\' " +
                    "        AND  " +
                    "            congress = " + congress + " ) order_contributions " +
                    "    JOIN " +
                    "        entities.legislators_flat_terms " +
                    "    ON  " +
                    "       opensecrets_id = recip_id       " +
                    "    WHERE  " +
                    "       (begin_timestamp < " + ts.getEnd() +  " AND end_timestamp > " + ts.getBegin() +  ")    " +
                    "    AND " +
                    "       (begin_timestamp < " + ts.getBegin() +  " OR end_timestamp > " + ts.getEnd() +  ") )   " +
                    "    joined_contributions  " +
                    "GROUP BY " +
                    "      bioguide_id     " +
                    "    , first_name   " +
                    "    , last_name   " +
                    "    , industry_id " +
                    "    , industry " +
                    "    , cat_name " +
                    "    , sector " +
                    "    , sector_long " +
                    "    , congress";

            return impalaTemplate.query(query, new IndustryContributionTotalsHashMapper(null));
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get total sums of all money contributed to each legislator during a given congress by a certain
     * industry category.
     *
     * @return
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotalsMap
     */
    public IndustryContributionTotalsMap getIndustryCategoryContributionTotalsMap(String categoryId, int congress) {

        CongressTimestamps ts = timeService.getCongressTimestamps(congress);

        try {
            String query =
                    "SELECT " +
                            "  bioguide_id" +
                            ", real_code as category_id" +
                            ", industry" +
                            ", cat_name" +
                            ", sector" +
                            ", sector_long" +
                            ", congress" +
                            ", total " +
                            "FROM " +
                            "       (SELECT " +
                            "         bioguide_id" +
                            "       , real_code" +
                            "       , congress" +
                            "       , SUM(amount) as total" +
                            "       FROM" +
                            "              (SELECT " +
                            "                bioguide_id" +
                            "              , real_code" +
                            "              , amount" +
                            "              , congress" +
                            "              FROM " +
                            "                     (SELECT " +
                            "                       bioguide_id" +
                            "                     , opensecrets_id" +
                            "                     FROM" +
                            "                         entities.legislators_flat_terms" +
                            "                     WHERE " +
                            "                         (begin_timestamp < " + ts.getEnd() + " AND end_timestamp > " + ts.getBegin() + ")   " +
                            "                     AND" +
                            "                         (begin_timestamp < " + ts.getBegin() + " OR end_timestamp > " + ts.getEnd() + ")    " +
                            "                     ) legislators " +
                            "              JOIN " +
                            "                  crp.individual_contributions c " +
                            "              ON " +
                            "                  opensecrets_id = c.recip_id" +
                            "              WHERE " +
                            "                  real_code = \'" + categoryId + "\'" +
                            "              AND " +
                            "                  congress = " + congress +
                            "              )" +
                            "       candidate_receipts " +
                            "       GROUP BY " +
                            "         bioguide_id" +
                            "       , real_code" +
                            "       , congress) " +
                            "sums " +
                            "JOIN " +
                            "   entities.industry_codes l " +
                            "ON " +
                            "   real_code = cat_code";

            return impalaTemplate.query(query, new IndustryContributionTotalsHashMapper(null));
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get total sums of all money contributed to each legislator in a given chamber during a given congress by a certain
     * industry category.
     *
     * @return
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotalsMap
     */
    public IndustryContributionTotalsMap getIndustryContributionTotalsMapByChamber(String industryId, String chamber, int congress) {

        CongressTimestamps ts = timeService.getCongressTimestamps(congress);

        String legislatorType = chamber.contains("h") ? "r" : "s";

        try {
            String query =
                    "SELECT  " +
                    "      bioguide_id " +
                    "    , cat_order as industry_id " +
                    "    , industry " +
                    "    , cat_name " +
                    "    , sector " +
                    "    , sector_long " +
                    "    , congress " +
                    "    , SUM(amount) as total     " +
                    "FROM " +
                    "    (SELECT  " +
                    "          bioguide_id " +
                    "        , first_name " +
                    "        , last_name " +
                    "        , cat_order " +
                    "        , industry " +
                    "        , cat_name " +
                    "        , sector " +
                    "        , sector_long " +
                    "        , congress " +
                    "        , amount " +
                    "    FROM      " +
                    "        (SELECT  " +
                    "              recip_id   " +
                    "            , cat_order " +
                    "            , industry " +
                    "            , cat_name " +
                    "            , sector " +
                    "            , sector_long " +
                    "            , congress " +
                    "            , amount " +
                    "        FROM  " +
                    "            crp.individual_contributions contributions " +
                    "        JOIN  " +
                    "            entities.industry_codes industries " +
                    "        ON  " +
                    "            real_code = cat_code " +
                    "        WHERE  " +
                    "            cat_order = \'" + industryId + "\' " +
                    "        AND  " +
                    "            congress = " + congress + " ) order_contributions " +
                    "    JOIN " +
                    "        entities.legislators_flat_terms " +
                    "    ON  " +
                    "       opensecrets_id = recip_id       " +
                    "    WHERE  " +
                    "       (begin_timestamp < " + ts.getEnd() +  " AND end_timestamp > " + ts.getBegin() +  ")    " +
                    "    AND " +
                    "       (begin_timestamp < " + ts.getBegin() +  " OR end_timestamp > " + ts.getEnd() +  ")     " +
                    "    AND  " +
                    "       SUBSTR(term_type, 1, 1) = \'" + legislatorType + "\' ) industry_contributions " +
                    "GROUP BY " +
                    "      bioguide_id     " +
                    "    , first_name   " +
                    "    , last_name   " +
                    "    , industry_id " +
                    "    , industry " +
                    "    , cat_name " +
                    "    , sector " +
                    "    , sector_long " +
                    "    , congress";

            return impalaTemplate.query(query, new IndustryContributionTotalsHashMapper(chamber));
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get total sums of all money contributed to each legislator in a given chamber during a given congress by a certain
     * industry category.
     *
     * @return
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotalsMap
     */
    public IndustryContributionTotalsMap getIndustryCategoryContributionTotalsMap(String categoryId, String chamber, int congress) {

        CongressTimestamps ts = timeService.getCongressTimestamps(congress);

        String legislatorType = chamber.contains("h") ? "r" : "s";

        try {
            String query =
                    "SELECT " +
                    "  bioguide_id" +
                    ", real_code as category_id" +
                    ", industry" +
                    ", cat_name" +
                    ", sector" +
                    ", sector_long" +
                    ", congress" +
                    ", total " +
                    "FROM " +
                    "       (SELECT " +
                    "         bioguide_id" +
                    "       , real_code" +
                    "       , congress" +
                    "       , SUM(amount) as total" +
                    "       FROM" +
                    "              (SELECT " +
                    "                bioguide_id" +
                    "              , real_code" +
                    "              , amount" +
                    "              , congress" +
                    "              FROM " +
                    "                     (SELECT " +
                    "                       bioguide_id" +
                    "                     , opensecrets_id" +
                    "                     FROM" +
                    "                         entities.legislators_flat_terms" +
                    "                     WHERE " +
                    "                         (begin_timestamp < " + ts.getEnd() + " AND end_timestamp > " + ts.getBegin() + ")   " +
                    "                     AND" +
                    "                         (begin_timestamp < " + ts.getBegin() + " OR end_timestamp > " + ts.getEnd() + ")    " +
                    "                     AND " +
                    "                         SUBSTR(term_type, 1, 1) = \'" + legislatorType + "\'" +
                    "                     ) legislators " +
                    "              JOIN " +
                    "                  crp.individual_contributions c " +
                    "              ON " +
                    "                  opensecrets_id = c.recip_id" +
                    "              WHERE " +
                    "                  real_code = \'" + categoryId + "\'" +
                    "              AND " +
                    "                  congress = " + congress +
                    "              )" +
                    "       candidate_receipts " +
                    "       GROUP BY " +
                    "         bioguide_id" +
                    "       , real_code" +
                    "       , congress) " +
                    "sums " +
                    "JOIN " +
                    "   entities.industry_codes l " +
                    "ON " +
                    "   real_code = cat_code";

            return impalaTemplate.query(query, new IndustryContributionTotalsHashMapper(chamber));
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get total sums of all money contributed to each legislator in a given chamber during a given time range by a certain
     * industry.
     *
     * @param industryId
     * @param chamber
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public IndustryContributionTotalsMap getIndustryContributionTotalsMapByChamber(String industryId, String chamber, long beginTimestamp, long endTimestamp) {

        String legislatorType = chamber.contains("h") ? "r" : "s";

        try {
            String query =
                    "SELECT  " +
                            "      bioguide_id " +
                            "    , cat_order as industry_id " +
                            "    , industry " +
                            "    , cat_name " +
                            "    , sector " +
                            "    , sector_long " +
                            "    , congress " +
                            "    , SUM(amount) as total     " +
                            "FROM " +
                            "    (SELECT  " +
                            "          bioguide_id " +
                            "        , first_name " +
                            "        , last_name " +
                            "        , cat_order " +
                            "        , industry " +
                            "        , cat_name " +
                            "        , sector " +
                            "        , sector_long " +
                            "        , congress " +
                            "        , amount " +
                            "    FROM      " +
                            "        (SELECT  " +
                            "              recip_id   " +
                            "            , cat_order " +
                            "            , industry " +
                            "            , cat_name " +
                            "            , sector " +
                            "            , sector_long " +
                            "            , congress " +
                            "            , amount " +
                            "        FROM  " +
                            "            crp.individual_contributions contributions " +
                            "        JOIN  " +
                            "            entities.industry_codes industries " +
                            "        ON  " +
                            "            real_code = cat_code " +
                            "        WHERE  " +
                            "            cat_order = \'" + industryId + "\' " +
                            "        AND  " +
                            "            (transaction_ts < " + endTimestamp + " AND transaction_ts > " + beginTimestamp + ")   ) order_contributions " +
                            "    JOIN " +
                            "        entities.legislators_flat_terms " +
                            "    ON  " +
                            "       opensecrets_id = recip_id       " +
                            "    WHERE  " +
                            "       (begin_timestamp < " + endTimestamp +  " AND end_timestamp > " + beginTimestamp +  ")    " +
                            "    AND " +
                            "       (begin_timestamp < " + beginTimestamp +  " OR end_timestamp > " + endTimestamp +  ")     " +
                            "    AND  " +
                            "       SUBSTR(term_type, 1, 1) = \'" + legislatorType + "\' ) industry_contributions " +
                            "GROUP BY " +
                            "      bioguide_id     " +
                            "    , first_name   " +
                            "    , last_name   " +
                            "    , industry_id " +
                            "    , industry " +
                            "    , cat_name " +
                            "    , sector " +
                            "    , sector_long " +
                            "    , congress";

            return impalaTemplate.query(query, new IndustryContributionTotalsHashMapper(chamber, beginTimestamp, endTimestamp));
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get total sums of all money contributed to each legislator in a given chamber during a given time range by a certain
     * industry category.
     *
     * @param categoryId
     * @param chamber
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public IndustryContributionTotalsMap getIndustryCategoryContributionTotalsMapByChamber(String categoryId, String chamber, long beginTimestamp, long endTimestamp) {

        String legislatorType = chamber.contains("h") ? "r" : "s";

        try {
            String query =
                    "SELECT " +
                            "  bioguide_id" +
                            ", real_code as category_id" +
                            ", industry" +
                            ", cat_name" +
                            ", sector" +
                            ", sector_long" +
                            ", total " +
                            "FROM " +
                            "       (SELECT " +
                            "         bioguide_id" +
                            "       , real_code" +
                            "       , SUM(amount) as total" +
                            "       FROM" +
                            "              (SELECT " +
                            "                bioguide_id" +
                            "              , real_code" +
                            "              , amount" +
                            "              FROM " +
                            "                     (SELECT " +
                            "                       bioguide_id" +
                            "                     , opensecrets_id" +
                            "                     FROM" +
                            "                         entities.legislators_flat_terms" +
                            "                     WHERE " +
                            "                         (begin_timestamp < " + endTimestamp + " AND end_timestamp > " + beginTimestamp + ")   " +
                            "                     AND" +
                            "                         (begin_timestamp < " + beginTimestamp + " OR end_timestamp > " + endTimestamp + ")    " +
                            "                     AND " +
                            "                         SUBSTR(term_type, 1, 1) = \'" + legislatorType + "\'" +
                            "                     ) legislators " +
                            "              JOIN " +
                            "                  crp.individual_contributions c " +
                            "              ON " +
                            "                  opensecrets_id = c.recip_id" +
                            "              WHERE " +
                            "                  real_code = \'" + categoryId + "\'" +
                            "              AND " +
                            "                   (transaction_ts < " + endTimestamp + " AND transaction_ts > " + beginTimestamp + ")   " +
                            "              )" +
                            "       candidate_receipts " +
                            "       GROUP BY " +
                            "         bioguide_id" +
                            "       , real_code) " +
                            "sums " +
                            "JOIN " +
                            "   entities.industry_codes l " +
                            "ON " +
                            "   real_code = cat_code";

            return impalaTemplate.query(query, new IndustryContributionTotalsHashMapper(chamber, beginTimestamp, endTimestamp));
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get a list of Industry to politician contribution sums for all politicians for all time
     * @param industryId
     * @return
     */
    public List<PoliticianIndustryContributionTotals> getIndustryToPoliticianContributions(String industryId) {

        try {
            String query =
                    "SELECT DISTINCT" +
                    "       bioguide_id" +
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
                    "          , cat_order as industry_id" +
                    "          , party" +
                    "          , religion" +
                    "          , SUM(contribution_count) as contribution_count" +
                    "          , SUM(contribution_sum) as contribution_sum      " +
                    "     FROM" +
                    "          (SELECT" +
                    "                 bioguide_id" +
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
                    "          GROUP BY" +
                    "                 bioguide_id" +
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
                    "          , cat_order" +
                    "          , party" +
                    "          , religion " +
                    "     " +
                    "     ) distinct_sums " +
                    "" +
                    "JOIN" +
                    "     entities.industry_codes l " +
                    "ON" +
                    "     industry_id = cat_order " +
                    "WHERE industry_id = \'" + industryId + "\'";

            return impalaTemplate.query(query, new PoliticianIndustryContributionTotalsMapper());
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get a list of Industry category to politician contribution sums for all politicians for all time
     * @param categoryId
     * @return
     */
    public List<PoliticianIndustryContributionTotals> getIndustryCategoryToPoliticianContributions(String categoryId) {

        try {
            String query =
                    "SELECT  " +
                    "     bioguide_id " +
                    "   , real_code as category_id" +
                    "   , cat_name as category_name" +
                    "   , party" +
                    "   , religion        " +
                    "   , industry " +
                    "   , sector " +
                    "   , sector_long " +
                    "   , contribution_count" +
                    "   , contribution_sum " +
                    "FROM  " +
                    "   (SELECT  " +
                    "         bioguide_id " +
                    "       , real_code  " +
                    "       , party" +
                    "       , religion" +
                    "       , COUNT(amount) as contribution_count" +
                    "       , SUM(amount) as contribution_sum" +
                    "   FROM  " +
                    "       (SELECT  " +
                    "             bioguide_id " +
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
                    "   GROUP BY  " +
                    "         bioguide_id " +
                    "       , real_code " +
                    "       , party" +
                    "       , religion    " +
                    "       ) sums  " +
                    "JOIN  " +
                    "   entities.industry_codes l  " +
                    "ON  " +
                    "   real_code = cat_code " +
                    "WHERE real_code = \'" + categoryId + "\'";

            return impalaTemplate.query(query, new PoliticianIndustryContributionTotalsMapper());
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get a list of of industry to politician contributions for all years passed in
     * @param industryId
     * @param years
     * @return
     */
    public List<PoliticianIndustryContributionTotals> getIndustryContributionTotals(String industryId, int... years) {

        String yrs;

        if (years.length > 1) {
            yrs = "( year = ";

            for (int i = 0; i < years.length; i++) {

                if (i != years.length - 1)
                    yrs += years[i] + " OR year = ";
                else
                    yrs += years[i] + ")";
            }
        }
        else
            yrs = "year = " + years[0];


        String query =
                "SELECT " +
                        "* " +
                        "FROM " +
                        "   campaign_finance.industry_to_pol_contribution_monthly_totals " +
                        "WHERE " +
                        "   industry_id = \'" + industryId + "\' AND " + yrs;

        return impalaTemplate.query(query, new PoliticianIndustryContributionTotalsMapper());
    }

    @Autowired
    public void setImpalaTemplate(JdbcTemplate impalaTemplate) {
        this.impalaTemplate = impalaTemplate;
    }
}