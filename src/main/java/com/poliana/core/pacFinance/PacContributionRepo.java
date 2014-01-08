package com.poliana.core.pacFinance;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * @author David Gilmore
 * @date 12/19/13
 */
@Repository
public class PacContributionRepo {

    private JdbcTemplate impalaTemplate;
    private Datastore mongoStore;

    private static final Logger logger = Logger.getLogger(PacContributionRepo.class);


    /**
     * Save a list of PacPoliticianContrTotals.
     * @param totalsList
     * @return
     * @see PacPoliticianContrTotals
     */
    public Iterable<Key<PacPoliticianContrTotals>> savePacPoliticianContrTotals(List<PacPoliticianContrTotals> totalsList) {

        return mongoStore.save(totalsList);
    }

    /**
     * Get an iterator for all PacPoliticianContrTotals in MongoDB for a given bioguide ID
     * @param bioguideId
     * @return
     */
    public Iterator<PacPoliticianContrTotals> getAllPacPoliticianContrTotals(String bioguideId) {

        Query<PacPoliticianContrTotals> query = mongoStore.find(PacPoliticianContrTotals.class);

        query.criteria("bioguideId").equal(bioguideId);

        return query.iterator();
    }

    /**
     * Get a list of PAC politician contributions totals for a politician in a given congressional cycle.
     * @param bioguideId
     * @param congress
     * @return
     */
    public List<PacPoliticianContrTotals> getPacPoliticianContrTotalsMongo(String bioguideId, int congress) {

        Query<PacPoliticianContrTotals> query = mongoStore.find(PacPoliticianContrTotals.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("cycle").equal(congress));

        return query.asList();
    }

    public long countPacPoliticianContrTotals(String bioguideId, int congress) {

        Query<PacPoliticianContrTotals> query = mongoStore.find(PacPoliticianContrTotals.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("cycle").equal(congress));

        return mongoStore.getCount(query);
    }

    /**
     * Get PAC contributions for a politician in a certain congress.
     * @param bioguideId
     * @param congress
     * @return
     */
    public List<PacPoliticianContrTotals> getPacPoliticianContrTotals(String bioguideId, int congress) {

        try {
            String query =
                    "SELECT " +
                            "  bioguide_id" +
                            ", sums.cmte_id" +
                            ", p.cmte_nm" +
                            ", cycle" +
                            ", _c3 " +
                            "FROM " +
                            "(SELECT " +
                            "bioguide_id" +
                            ", cmte_id" +
                            ", cycle" +
                            ", SUM(transaction_amt) " +
                            "FROM " +
                            "(SELECT " +
                            "m.bioguide_id" +
                            ", c.cmte_id" +
                            ", c.transaction_amt" +
                            ", c.cycle " +
                            "FROM " +
                            "entities.legislators m " +
                            "JOIN " +
                            "fec.pac_to_candidate_contributions c " +
                            "ON m.fec_id = c.cand_id " +
                            "WHERE m.bioguide_id = \'" + bioguideId + "\') candidate_receipts \n" +
                            "GROUP BY " +
                            "bioguide_id" +
                            ", cmte_id" +
                            ", cycle) " +
                            "sums " +
                            "JOIN " +
                            "entities.pacs p " +
                            "ON sums.cmte_id = p.cmte_id" +
                            "WHERE cycle = " + congress;


            return impalaTemplate.query(query, new PacPoliticianContrMapper());
        }
        catch (Exception e) {
            logger.error(e);
        }

        return new LinkedList<>();
    }

    /**
     * Get a HashMap of Lists of PAC contributions to politicians
     * @param bioguideId
     * @return
     */
    public HashMap<Integer, List<PacPoliticianContrTotals>> getAllLegislatorReceivedPacTotals(String bioguideId) {

        try {
            String query =
                    "SELECT " +
                        "  bioguide_id" +
                        ", sums.cmte_id" +
                        ", p.cmte_nm" +
                        ", cycle" +
                        ", _c3 " +
                    "FROM " +
                        "(SELECT " +
                            "bioguide_id" +
                            ", cmte_id" +
                            ", cycle" +
                            ", SUM(transaction_amt) " +
                        "FROM " +
                        "(SELECT " +
                            "m.bioguide_id" +
                            ", c.cmte_id" +
                            ", c.transaction_amt" +
                            ", c.cycle " +
                            "FROM " +
                                "entities.legislators m " +
                            "JOIN " +
                                "fec.pac_to_candidate_contributions c " +
                            "ON m.fec_id = c.cand_id " +
                            "WHERE m.bioguide_id = \'" + bioguideId + "\') candidate_receipts \n" +
                            "GROUP BY " +
                                "bioguide_id" +
                                ", cmte_id" +
                                ", cycle) " +
                        "sums " +
                    "JOIN " +
                        "entities.pacs p " +
                            "ON sums.cmte_id = p.cmte_id";

            return impalaTemplate.query(query, new AllPacPolContrTotalsMapper());
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }


    public List<PacPoliticianContrTotals> legislatorReceivedPacTotals(final String bioguideId, final int cycle) {

        try {
            String query =
                    "SELECT " +
                        "  bioguide_id" +
                        ", cmte_id" +
                        ", SUM(transaction_amt) " +
                    "FROM " +
                        "(SELECT " +
                            "c.bioguide_id" +
                            ", m.cmte_id " +
                            ", m.cmte_nm" +
                            ", c.transaction_amt " +
                        "FROM " +
                            "entities.pacs m " +
                        "JOIN " +
                            "fec.pac_to_candidate_contributions c " +
                        "ON m.cand_id = c.cand_id  " +
                        "WHERE c.bioguide_id = \'?\' AND c.cycle = ?) " +
                    "candidate_receipts " +
                    "GROUP BY " +
                        "c.bioguide_id" +
                        ", c.cmte_id" +
                        ", m.cmte_nm";

            PreparedStatementSetter pss = new PreparedStatementSetter() {
                public void setValues(PreparedStatement preparedStatement) throws
                        SQLException {
                    preparedStatement.setString(1, bioguideId);
                    preparedStatement.setInt(2, cycle);
                }
            };

            return impalaTemplate.query(query, pss, new PacPoliticianContrMapper());
        }
        catch (Exception e) {}

        return new ArrayList<>();
    }

    /**
     *
     * @param bioguideId
     * @param beginTimestamp
     * @param endTimestamp
     * @param limit
     * @return
     */
    public List<PacPoliticianContrTotals> legislatorReceivedPacTotals(final String bioguideId, final int beginTimestamp,
                                                                      final int endTimestamp, final int limit) {

        try {
            String query =
                    "SELECT " +
                        "  bioguide_id" +
                        ", cmte_id" +
                        ", SUM(transaction_amt) " +
                    "FROM " +
                        "(SELECT " +
                            "c.bioguide_id" +
                            ", m.cmte_id " +
                            ", m.cmte_nm" +
                            ", c.transaction_amt " +
                        "FROM " +
                            "entities.pacs m " +
                        "JOIN fec.pac_to_candidate_contributions c " +
                        "ON m.cand_id = c.cand_id " +
                        "WHERE c.bioguide_id = \'?\' AND c.transation_ts > ? AND c.transation_ts < ?) " +
                    "candidate_receipts " +
                    "GROUP BY " +
                        "c.bioguide_id" +
                        ", c.cmte_id" +
                        ", m.cmte_nm";

            PreparedStatementSetter pss = new PreparedStatementSetter() {
                public void setValues(PreparedStatement preparedStatement) throws
                        SQLException {
                    preparedStatement.setString(1, bioguideId);
                    preparedStatement.setInt(2, beginTimestamp);
                    preparedStatement.setInt(3, endTimestamp);
                }
            };

            return impalaTemplate.query(query, pss, new PacPoliticianContrMapper());
        }
        catch (Exception e) {}

        return new ArrayList<>();
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
