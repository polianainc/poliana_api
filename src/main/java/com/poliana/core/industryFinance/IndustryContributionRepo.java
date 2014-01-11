package com.poliana.core.industryFinance;

import com.poliana.core.industryFinance.entities.IndustryContributionTotals;
import com.poliana.core.industryFinance.entities.IndustryPoliticianContribution;
import com.poliana.core.industryFinance.entities.IndustryPoliticianContributions;
import com.poliana.core.industryFinance.mapppers.IndustryContributionTotalsMapper;
import com.poliana.core.time.CongressTimestamps;
import com.poliana.core.time.TimeService;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import com.poliana.core.industryFinance.entities.IndustryTimeRangeTotals;
import com.poliana.core.industryFinance.mapppers.AllContrPerCogressMapper;
import com.poliana.core.industryFinance.mapppers.IndToPolContrTotalsMapper;
import com.poliana.core.industryFinance.mapppers.LegislatorRecievedIndustryTotalsMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author David Gilmore
 * @date 11/27/13
 */
@Repository
public class IndustryContributionRepo {

    private JdbcTemplate impalaTemplate;
    private Datastore mongoStore;

    private TimeService timeService;

    private static final Logger logger = Logger.getLogger(IndustryContributionRepo.class);


    public IndustryContributionRepo() {
        this.timeService = new TimeService();
    }


    /**
     * Get totals for an industry's contributions to all legislators during a given cycle.
     * @param congress
     * @return
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotals
     */
    public IndustryContributionTotals getIndustryTotalsMongo(String industryId, int congress) {

        Query<IndustryContributionTotals> query = mongoStore.createQuery(IndustryContributionTotals.class);

        query.and(
                query.criteria("industryId").equal(industryId),
                query.criteria("congress").equal(congress));

        return query.get();
    }

    /**
     * Get totals for an industry category contributions to all legislators during a given cycle.
     * @param congress
     * @return
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotals
     */
    public IndustryContributionTotals getIndustryCategoryTotalsMongo(String categoryId, int congress) {

        Query<IndustryContributionTotals> query = mongoStore.createQuery(IndustryContributionTotals.class);

        query.and(
                query.criteria("categoryId").equal(categoryId),
                query.criteria("congress").equal(congress));

        return query.get();
    }

    /**
     * Get totals for an industry's contributions to all legislators in a certain chamber during a given cycle.
     * @param chamber
     * @param congress
     * @return
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotals
     */
    public IndustryContributionTotals getIndustryChamberTotalsMongo(String industryId, String chamber, int congress) {

        Query<IndustryContributionTotals> query = mongoStore.createQuery(IndustryContributionTotals.class);

        query.and(
                query.criteria("industryId").equal(industryId),
                query.criteria("chamber").equal(chamber),
                query.criteria("congress").equal(congress));

        return query.get();
    }

    /**
     * Get totals for an industry category contributions to all legislators in a certain chamber during a given cycle.
     * @param chamber
     * @param congress
     * @return
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotals
     */
    public IndustryContributionTotals getIndustryCategoryChamberTotalsMongo(String categoryId, String chamber, int congress) {

        Query<IndustryContributionTotals> query = mongoStore.createQuery(IndustryContributionTotals.class);

        query.and(
                query.criteria("categoryId").equal(categoryId),
                query.criteria("chamber").equal(chamber),
                query.criteria("congress").equal(congress));

        return query.get();
    }

    /**
     * Save totals for an industry's contributions to all legislators in a certain chamber during a given cycle
     * @param industryContributionTotals
     * @return
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotals
     */
    public Key<IndustryContributionTotals> saveIndustryContributionTotals(IndustryContributionTotals industryContributionTotals) {

        return mongoStore.save(industryContributionTotals);
    }

    /**
     * Save totals for an industry's contributions over a certain time range
     * @param industryTimeRangeTotals
     * @return
     * @see com.poliana.core.industryFinance.entities.IndustryTimeRangeTotals
     */
    public Key<IndustryTimeRangeTotals> saveIndTimeRangeTotal(IndustryTimeRangeTotals industryTimeRangeTotals) {

        return mongoStore.save(industryTimeRangeTotals);
    }

    /**
     * Save a list of industry to politician contribution objects
     * @param totalsList
     * @return
     */
    public Iterable<Key<IndustryPoliticianContributions>> saveIndustryToPoliticianContributions(List<IndustryPoliticianContributions> totalsList) {

        return mongoStore.save(totalsList);
    }

    public long countIndustryToPoliticianContributions(String bioguideId, int congress) {

        Query<IndustryPoliticianContributions> query = mongoStore.find(IndustryPoliticianContributions.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("cycle").equal(congress));

        return mongoStore.getCount(query);
    }

    /**
     * Using MongoDB, get a list of all industry to politician contributions for a given bioguide ID.
     * @param bioguideId
     * @return
     */
    public Iterator<IndustryPoliticianContributions> getIndustryToPoliticianContributions(String bioguideId) {

        Query<IndustryPoliticianContributions> query = mongoStore.find(IndustryPoliticianContributions.class);

        query.criteria("bioguideId").equal(bioguideId);

        return query.iterator();
    }

    /**
     * Using MongoDB, get a list of all industry to politician contributions for a given bioguide ID.
     * @param bioguideId
     * @return
     */
    public List<IndustryPoliticianContributions> getIndustryToPoliticianContributions(String bioguideId, int congress) {

        Query<IndustryPoliticianContributions> query = mongoStore.find(IndustryPoliticianContributions.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("cycle").equal(congress));

        return query.asList();
    }

    /**
     * Get total sums of all money contributed to each legislator during a given congress by a certain
     * industry category.
     *
     * @return
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotals
     */
    public IndustryContributionTotals getIndustryContributionTotals(String industryId, int congress) {

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

            return impalaTemplate.query(query, new IndustryContributionTotalsMapper(null));
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
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotals
     */
    public IndustryContributionTotals getIndustryCategoryContributionTotals(String categoryId, int congress) {

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

            return impalaTemplate.query(query, new IndustryContributionTotalsMapper(null));
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
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotals
     */
    public IndustryContributionTotals getIndustryChamberContributionTotals(String industryId, String chamber, int congress) {

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

            return impalaTemplate.query(query, new IndustryContributionTotalsMapper(chamber));
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
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotals
     */
    public IndustryContributionTotals getIndustryCategoryChamberContributionTotals(String categoryId, String chamber, int congress) {

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

            return impalaTemplate.query(query, new IndustryContributionTotalsMapper(chamber));
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
                        "  bioguide_id" +
                        ", real_code" +
                        ", industry" +
                        ", sector" +
                        ", sector_long" +
                        ", congress" +
                        ", _c3 " +
                    "FROM " +
                        "(SELECT " +
                            "  bioguide_id" +
                            ", real_code" +
                            ", congress" +
                            ", SUM(amount) " +
                        "FROM " +
                            "(SELECT " +
                            "  bioguide_id" +
                            ", real_code" +
                            ", congress" +
                            ", amount " +
                            "FROM " +
                            "   entities.legislators m " +
                            "JOIN " +
                            "   crp.individual_contributions c " +
                            "ON " +
                            "   opensecrets_id = c.recip_id " +
                            "WHERE bioguide_id = \'" + bioguideId + "\') " +
                        "candidate_receipts " +
                        "GROUP BY " +
                            "  bioguide_id" +
                            ", real_code" +
                            ", congress) " +
                    "sums " +
                    "JOIN " +
                    "   entities.industry_codes l " +
                    "ON " +
                    "   real_code = cat_code";

            return impalaTemplate.query(query, new AllContrPerCogressMapper());
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get a list of industry to politician contribution sums for a given cycle
     * @param bioguideId
     * @return
     */
    public List<IndustryPoliticianContributions> getAllIndustryContributions(String bioguideId, int congress) {

        try {
            String query =
                    "SELECT " +
                            "  bioguide_id" +
                            ", real_code" +
                            ", industry" +
                            ", sector" +
                            ", sector_long" +
                            ", congress" +
                            ", _c3 " +
                            "FROM " +
                            "(SELECT " +
                            "  bioguide_id" +
                            ", real_code" +
                            ", congress" +
                            ", SUM(amount) " +
                            "FROM " +
                            "(SELECT " +
                            "  bioguide_id" +
                            ", real_code" +
                            ", congress" +
                            ", amount " +
                            "FROM " +
                            "   entities.legislators m " +
                            "JOIN " +
                            "   crp.individual_contributions c " +
                            "ON " +
                            "   opensecrets_id = c.recip_id " +
                            "WHERE bioguide_id = \'" + bioguideId + "\' " +
                            "candidate_receipts " +
                            "GROUP BY " +
                            "  bioguide_id" +
                            ", real_code" +
                            ", congress) " +
                            "sums " +
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
     * Get a list of of industry to politician contributions for all years passed in
     * @param industryId
     * @param years
     * @return
     */
    public List<IndustryPoliticianContributions> industryContrTotals(String industryId, int... years) {

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

    /**
     *
     * @param bioguideId
     * @param beginTimestamp
     * @param endTimestamp
     * @param limit
     * @return
     */
    public List<IndustryPoliticianContribution> legislatorReceivedIndustryTotals(String bioguideId,
                                                                    long beginTimestamp, long endTimestamp, int limit) {
        String lim = "";
        if (limit != 0)
            lim = " LIMIT " + limit;

        String query =
                "SELECT " +
                    "  bioguide_id" +
                    ", real_code" +
                    ", sum(amount)" +
                    ", i.cat_name" +
                    ", i.industry" +
                    ", i.sector_long " +
                "FROM " +
                    "campaign_finance.individual_contributions_timestamped c " +
                "JOIN " +
                    "entities.legislators l " +
                "ON " +
                    "c.recip_id = l.opensecrets_id " +
                "JOIN " +
                    "entities.industry_codes i " +
                "ON " +
                    "c.real_code = i.cat_code " +
                " WHERE " +
                        "bioguide_id = \'" + bioguideId +"\' " +
                    "AND " +
                        "unix_time > " + beginTimestamp + " " +
                    "AND " +
                        "unix_time < " + endTimestamp + " " +
                "GROUP BY " +
                    "  bioguide_id" +
                    ", real_code" +
                    ", i.cat_name" +
                    ", i.industry" +
                    ", i.sector_long" + lim;

        return impalaTemplate.query(query, new LegislatorRecievedIndustryTotalsMapper(beginTimestamp, endTimestamp));
    }

    @Autowired
    public void setImpalaTemplate(JdbcTemplate impalaTemplate) {
        this.impalaTemplate = impalaTemplate;
    }

    @Autowired
    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }
}