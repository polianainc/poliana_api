package com.poliana.core.industryFinance;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import com.poliana.core.industryFinance.entities.IndTimeRangeTotals;
import com.poliana.core.industryFinance.entities.IndToPolContrTotals;
import com.poliana.core.industryFinance.entities.IndustryPoliticianContributions;
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

    private static final Logger logger = Logger.getLogger(IndustryContributionRepo.class);

    /**
     * Save totals for an industry's contributions over a certain time range
     * @param indTimeRangeTotals
     * @return
     * @see IndTimeRangeTotals
     */
    public Key<IndTimeRangeTotals> saveIndTimeRangeTotal(IndTimeRangeTotals indTimeRangeTotals) {

        return mongoStore.save(indTimeRangeTotals);
    }

    /**
     * Save a list of industry to politician contribution objects
     * @param totalsList
     * @return
     */
    public Iterable<Key<IndToPolContrTotals>> saveIndustryToPoliticianContributions(List<IndToPolContrTotals> totalsList) {

        return mongoStore.save(totalsList);
    }

    public long countIndustryToPoliticianContributions(String bioguideId, int congress) {

        Query<IndToPolContrTotals> query = mongoStore.find(IndToPolContrTotals.class);

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
    public Iterator<IndToPolContrTotals> getIndustryToPoliticianContributions(String bioguideId) {

        Query<IndToPolContrTotals> query = mongoStore.find(IndToPolContrTotals.class);

        query.criteria("bioguideId").equal(bioguideId);

        return query.iterator();
    }

    /**
     * Using MongoDB, get a list of all industry to politician contributions for a given bioguide ID.
     * @param bioguideId
     * @return
     */
    public List<IndToPolContrTotals> getIndustryToPoliticianContributions(String bioguideId, int congress) {

        Query<IndToPolContrTotals> query = mongoStore.find(IndToPolContrTotals.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("cycle").equal(congress));

        return query.asList();
    }

    /**
     * Get a map of Cycle->Industry to politician contributions from Impala.
     * @param bioguideId
     * @return
     */
    public HashMap<Integer, List<IndToPolContrTotals>> getAllIndustryContributionsPerCongress(String bioguideId) {

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
    public List<IndToPolContrTotals> getAllIndustryContributions(String bioguideId, int congress) {

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
    public List<IndToPolContrTotals> industryContrTotals(String industryId, int... years) {

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
    public List<IndustryPoliticianContributions> legislatorReceivedIndustryTotals(String bioguideId,
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