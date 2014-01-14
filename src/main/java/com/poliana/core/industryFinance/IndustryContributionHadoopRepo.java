package com.poliana.core.industryFinance;

import com.poliana.core.industryFinance.entities.IndustryContributionTotalsMap;
import com.poliana.core.industryFinance.mapppers.IndustryContributionTotalsHashMapper;
import com.poliana.core.politicianFinance.entities.IndustryPoliticianContributionTotals;
import com.poliana.core.time.CongressTimestamps;
import com.poliana.core.time.TimeService;
import com.poliana.core.politicianFinance.mappers.IndToPolContrTotalsMapper;
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

        CongressTimestamps ts = timeService.congressTimestamps(congress);

        try {
            String query =
                    "SELECT  " +
                            "      bioguide_id " +
                            "    , first_name " +
                            "    , last_name   " +
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

        CongressTimestamps ts = timeService.congressTimestamps(congress);

        try {
            String query =
                    "SELECT " +
                            "  bioguide_id" +
                            ", first_name" +
                            ", last_name" +
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
                            "       , first_name" +
                            "       , last_name" +
                            "       , real_code" +
                            "       , industry " +
                            "       , cat_name " +
                            "       , sector " +
                            "       , sector_long " +
                            "       , congress" +
                            "       , SUM(amount) as total" +
                            "       FROM" +
                            "              (SELECT " +
                            "                bioguide_id" +
                            "              , first_name" +
                            "              , last_name" +
                            "              , real_code" +
                            "              , industry " +
                            "              , cat_name " +
                            "              , sector " +
                            "              , sector_long " +
                            "              , amount" +
                            "              , congress" +
                            "              FROM " +
                            "                     (SELECT " +
                            "                       bioguide_id" +
                            "                     , first_name" +
                            "                     , last_name" +
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
                            "       , first_name" +
                            "       , last_name" +
                            "       , real_code" +
                            "       , industry " +
                            "       , cat_name " +
                            "       , sector " +
                            "       , sector_long " +
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

        CongressTimestamps ts = timeService.congressTimestamps(congress);

        try {
            String query =
                    "SELECT  " +
                            "      bioguide_id " +
                            "    , first_name " +
                            "    , last_name   " +
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
                            "       SUBSTR(term_type, 1, 1) = \'" + chamber + "\' ) industry_contributions " +
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

        CongressTimestamps ts = timeService.congressTimestamps(congress);

        try {
            String query =
                    "SELECT " +
                    "  bioguide_id" +
                    ", first_name" +
                    ", last_name" +
                    ", real_code as industry_id" +
                    ", industry" +
                    ", cat_name" +
                    ", sector" +
                    ", sector_long" +
                    ", congress" +
                    ", total " +
                    "FROM " +
                    "       (SELECT " +
                    "         bioguide_id" +
                    "       , first_name" +
                    "       , last_name" +
                    "       , real_code" +
                    "       , congress" +
                    "       , SUM(amount) as total" +
                    "       FROM" +
                    "              (SELECT " +
                    "                bioguide_id" +
                    "              , first_name" +
                    "              , last_name" +
                    "              , real_code" +
                    "              , amount" +
                    "              , congress" +
                    "              FROM " +
                    "                     (SELECT " +
                    "                       bioguide_id" +
                    "                     , first_name" +
                    "                     , last_name" +
                    "                     , opensecrets_id" +
                    "                     FROM" +
                    "                         entities.legislators_flat_terms" +
                    "                     WHERE " +
                    "                         (begin_timestamp < " + ts.getEnd() + " AND end_timestamp > " + ts.getBegin() + ")   " +
                    "                     AND" +
                    "                         (begin_timestamp < " + ts.getBegin() + " OR end_timestamp > " + ts.getEnd() + ")    " +
                    "                     AND " +
                    "                         SUBSTR(term_type, 1, 1) = \'" + chamber + "\'" +
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
                    "       , first_name" +
                    "       , last_name" +
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
     * Get a list of of industry to politician contributions for all years passed in
     * @param industryId
     * @param years
     * @return
     */
    public List<IndustryPoliticianContributionTotals> getIndustryContributionTotals(String industryId, int... years) {

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

        return impalaTemplate.query(query, new IndToPolContrTotalsMapper());
    }


    @Autowired
    public void setImpalaTemplate(JdbcTemplate impalaTemplate) {
        this.impalaTemplate = impalaTemplate;
    }
}