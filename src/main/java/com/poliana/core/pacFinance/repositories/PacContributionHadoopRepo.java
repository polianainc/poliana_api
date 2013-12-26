package com.poliana.core.pacFinance.repositories;

import com.poliana.core.pacFinance.entities.PacPoliticianContrTotals;
import com.poliana.core.pacFinance.mappers.PacPoliticianContrMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author David Gilmore
 * @date 12/19/13
 */
public class PacContributionHadoopRepo {

    @Autowired
    protected JdbcTemplate hiveTemplate;

    @Autowired
    protected JdbcTemplate impalaTemplate;

    public List<PacPoliticianContrTotals> legislatorReceivedPacTotals(String bioguideId, int beginTimestamp,
                                                                                  int endTimestamp) {
        return legislatorReceivedPacTotals(bioguideId, beginTimestamp, endTimestamp, 0);
    }

//    public List<Committee> getAllCommittees() {
//
//
//    }

    public List<PacPoliticianContrTotals> legislatorReceivedPacTotals(final String bioguideId, final int beginTimestamp,
                                                                      final int endTimestamp, final int limit) {

        try {
            String query = "SELECT bioguide_id, cmte_id, SUM(transaction_amt) FROM (SELECT c.bioguide_id, m.cmte_id " +
                    "c.transaction_amt FROM fec.pac_committee_master m JOIN fec.pac_to_candidate_contributions c ON " +
                    "m.cand_id = c.cand_id  WHERE c.bioguide_id = \'?\' AND c.transation_ts > ? AND c.transation_ts < ?) " +
                    "candidate_receipts GROUP BY bioguide_id, cmte_id";

            PreparedStatementSetter pss = new PreparedStatementSetter() {
                public void setValues(PreparedStatement preparedStatement) throws
                        SQLException {
                    preparedStatement.setString(1, bioguideId);
                    preparedStatement.setInt(2, beginTimestamp);
                    preparedStatement.setInt(3, endTimestamp);
                }
            };

            return impalaTemplate.query(query, pss, new PacPoliticianContrMapper(beginTimestamp, endTimestamp));
        }
        catch (Exception e) {}

        return new ArrayList<PacPoliticianContrTotals>();
    }
}
