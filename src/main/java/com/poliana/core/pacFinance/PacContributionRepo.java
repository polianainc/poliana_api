package com.poliana.core.pacFinance;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author David Gilmore
 * @date 12/19/13
 */
@Repository
public class PacContributionRepo {

    @Autowired
    protected JdbcTemplate hiveTemplate;

    @Autowired
    protected JdbcTemplate impalaTemplate;

    private static final Logger logger = Logger.getLogger(PacContributionRepo.class);

    public HashMap<Integer, List<PacPoliticianContrTotals>> allLegislatorReceivedPacTotals(final String bioguideId) {

        try {
            String query = "SELECT bioguide_id, sums.cmte_id, p.cmte_nm, cycle, _c3 FROM\n" +
                    "    (SELECT bioguide_id, cmte_id, cycle, SUM(transaction_amt) FROM \n" +
                    "        (SELECT m.bioguide_id, c.cmte_id, c.transaction_amt, c.cycle FROM\n" +
                    "         entities.legislators m JOIN fec.pac_to_candidate_contributions c ON m.fec_id = c.cand_id " +
                    "         WHERE m.bioguide_id = \'" + bioguideId + "\') candidate_receipts \n" +
                    "    GROUP BY bioguide_id, cmte_id, cycle) sums JOIN entities.pacs p ON sums.cmte_id = p.cmte_id";

            return impalaTemplate.query(query, new AllPacPolContrTotalsMapper());
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }


    public List<PacPoliticianContrTotals> legislatorReceivedPacTotals(final String bioguideId, final int cycle) {

        try {
            String query = "SELECT bioguide_id, cmte_id, SUM(transaction_amt) FROM (SELECT c.bioguide_id, m.cmte_id " +
                    "m.cmte_nm, c.transaction_amt FROM entities.pacs m JOIN fec.pac_to_candidate_contributions c ON " +
                    "m.cand_id = c.cand_id  WHERE c.bioguide_id = \'?\' AND c.cycle = ?) " +
                    "candidate_receipts GROUP BY c.bioguide_id, c.cmte_id, m.cmte_nm";

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
     * @return
     */
    public List<PacPoliticianContrTotals> legislatorReceivedPacTotals(String bioguideId, int beginTimestamp,
                                                                                  int endTimestamp) {
        return legislatorReceivedPacTotals(bioguideId, beginTimestamp, endTimestamp, 0);
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
            String query = "SELECT bioguide_id, cmte_id, SUM(transaction_amt) FROM (SELECT c.bioguide_id, m.cmte_id " +
                    "m.cmte_nm, c.transaction_amt FROM entities.pacs m JOIN fec.pac_to_candidate_contributions c ON " +
                    "m.cand_id = c.cand_id  WHERE c.bioguide_id = \'?\' AND c.transation_ts > ? AND c.transation_ts < ?) " +
                    "candidate_receipts GROUP BY c.bioguide_id, c.cmte_id, m.cmte_nm";

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
}
