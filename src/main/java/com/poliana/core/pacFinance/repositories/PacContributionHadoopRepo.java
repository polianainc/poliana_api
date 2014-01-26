package com.poliana.core.pacFinance.repositories;

import com.poliana.core.pacFinance.AllPacPolContrTotalsMapper;
import com.poliana.core.pacFinance.entities.PacPoliticianContributionTotals;
import com.poliana.core.pacFinance.mappers.PacPoliticianContributionMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author David Gilmore
 * @date 1/26/14
 */
@Repository
public class PacContributionHadoopRepo {

    private JdbcTemplate impalaTemplate;

    private static final Logger logger = Logger.getLogger(PacContributionHadoopRepo.class);


    /**
     * Get PAC contributions for a politician in a certain congress.
     * @param bioguideId
     * @param congress
     * @return
     */
    public List<PacPoliticianContributionTotals> getPacPoliticianContrTotals(String bioguideId, int congress) {

        try {
            String query =
                    "SELECT " +
                    "     bioguide_id" +
                    "   , sums.cmte_id" +
                    "   , p.cmte_nm" +
                    "   , cycle" +
                    "   , _c3 " +
                    "FROM " +
                    "   (SELECT " +
                    "         bioguide_id" +
                    "       , cmte_id" +
                    "       , cycle" +
                    "       , SUM(transaction_amt) " +
                    "   FROM " +
                    "       (SELECT " +
                    "             m.bioguide_id" +
                    "           , c.cmte_id" +
                    "           , c.transaction_amt" +
                    "           , c.cycle " +
                    "       FROM " +
                    "           entities.legislators m " +
                    "       JOIN " +
                    "           fec.pac_to_candidate_contributions c " +
                    "       ON " +
                    "           m.fec_id = c.cand_id " +
                    "       WHERE m.bioguide_id = \'" + bioguideId + "\') " +
                    "   candidate_receipts \n" +
                    "   GROUP BY " +
                    "         bioguide_id" +
                    "       , cmte_id" +
                    "       , cycle) " +
                    "   sums " +
                    "JOIN " +
                    "   entities.pacs p " +
                    "ON " +
                    "   sums.cmte_id = p.cmte_id " +
                    "WHERE cycle = " + congress;


            return impalaTemplate.query(query, new PacPoliticianContributionMapper());
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
    public HashMap<Integer, List<PacPoliticianContributionTotals>> getAllLegislatorReceivedPacTotals(String bioguideId) {

        try {
            String query =
                    "SELECT " +
                    "     bioguide_id" +
                    "   , sums.cmte_id" +
                    "   , p.cmte_nm" +
                    "   , cycle" +
                    "   , _c3 " +
                    "FROM " +
                    "   (SELECT " +
                    "         bioguide_id" +
                    "       , cmte_id" +
                    "       , cycle" +
                    "       , SUM(transaction_amt) " +
                    "   FROM " +
                    "       (SELECT " +
                    "             m.bioguide_id" +
                    "           , c.cmte_id" +
                    "           , c.transaction_amt" +
                    "           , c.cycle " +
                    "       FROM " +
                    "           entities.legislators m " +
                    "       JOIN " +
                    "           fec.pac_to_candidate_contributions c " +
                    "       ON " +
                    "           m.fec_id = c.cand_id " +
                    "       WHERE " +
                    "           m.bioguide_id = \'" + bioguideId + "\') " +
                    "   candidate_receipts \n" +
                    "GROUP BY " +
                    "     bioguide_id" +
                    "   , cmte_id" +
                    "   , cycle) " +
                    "sums " +
                    "JOIN " +
                    "   entities.pacs p " +
                    "ON " +
                    "   sums.cmte_id = p.cmte_id";

            return impalaTemplate.query(query, new AllPacPolContrTotalsMapper());
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }


    public List<PacPoliticianContributionTotals> legislatorReceivedPacTotals(final String bioguideId, final int cycle) {

        try {
            String query =
                    "SELECT " +
                    "     bioguide_id" +
                    "   , cmte_id" +
                    "   , SUM(transaction_amt) " +
                    "FROM " +
                    "   (SELECT " +
                    "         c.bioguide_id" +
                    "       , m.cmte_id " +
                    "       , m.cmte_nm" +
                    "       , c.transaction_amt " +
                    "   FROM " +
                    "       entities.pacs m " +
                    "   JOIN " +
                    "       fec.pac_to_candidate_contributions c " +
                    "   ON " +
                    "       m.cand_id = c.cand_id  " +
                    "   WHERE " +
                    "       c.bioguide_id = \'?\' AND c.cycle = ?) " +
                    "   candidate_receipts " +
                    "GROUP BY " +
                    "     c.bioguide_id" +
                    "   , c.cmte_id" +
                    "   , m.cmte_nm";

            PreparedStatementSetter pss = new PreparedStatementSetter() {
                public void setValues(PreparedStatement preparedStatement) throws
                        SQLException {
                    preparedStatement.setString(1, bioguideId);
                    preparedStatement.setInt(2, cycle);
                }
            };

            return impalaTemplate.query(query, pss, new PacPoliticianContributionMapper());
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
    public List<PacPoliticianContributionTotals> legislatorReceivedPacTotals(final String bioguideId, final int beginTimestamp,
                                                                             final int endTimestamp, final int limit) {

        try {
            String query =
                    "SELECT " +
                    "     bioguide_id" +
                    "   , cmte_id" +
                    "   , SUM(transaction_amt) " +
                    "FROM " +
                    "   (SELECT " +
                    "         c.bioguide_id" +
                    "       , m.cmte_id " +
                    "       , m.cmte_nm" +
                    "       , c.transaction_amt " +
                    "   FROM " +
                    "       entities.pacs m " +
                    "   JOIN " +
                    "       fec.pac_to_candidate_contributions c " +
                    "   ON " +
                    "       m.cand_id = c.cand_id " +
                    "   WHERE c.bioguide_id = \'?\' AND c.transation_ts > ? AND c.transation_ts < ?) " +
                    "candidate_receipts " +
                    "GROUP BY " +
                    "     c.bioguide_id" +
                    "   , c.cmte_id" +
                    "   , m.cmte_nm";

            PreparedStatementSetter pss = new PreparedStatementSetter() {
                public void setValues(PreparedStatement preparedStatement) throws
                        SQLException {
                    preparedStatement.setString(1, bioguideId);
                    preparedStatement.setInt(2, beginTimestamp);
                    preparedStatement.setInt(3, endTimestamp);
                }
            };

            return impalaTemplate.query(query, pss, new PacPoliticianContributionMapper());
        }
        catch (Exception e) {}

        return new ArrayList<>();
    }


    @Autowired
    public void setImpalaTemplate(JdbcTemplate impalaTemplate) {
        this.impalaTemplate = impalaTemplate;
    }
}
