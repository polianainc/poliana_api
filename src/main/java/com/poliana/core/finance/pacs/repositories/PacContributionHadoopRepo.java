package com.poliana.core.finance.pacs.repositories;

import com.poliana.core.common.util.TimeUtils;
import com.poliana.core.finance.industries.mapppers.LegislatorRecievedIndustryTotalsMapper;
import com.poliana.core.finance.pacs.entities.PacPoliticianContrTotals;
import com.poliana.core.finance.pacs.mappers.PacPoliticianContrMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

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

    public List<PacPoliticianContrTotals> legislatorReceivedPacTotals(String bioguideId, int beginTimestamp,
                                                                      int endTimestamp, int limit) {
        String lim = "";
        if (limit != 0)
            lim = " LIMIT " + limit;

        String query = "SELECT bioguide_id, cmte_id, cmte_nm, cmte_pty_affiliation, cmte_tp, org_tp, connected_org_nm, " +
                "SUM(transaction_amt) FROM (SELECT c.bioguide_id, m.cmte_id, m.cmte_nm, m.cmte_pty_affiliation, " +
                "m.cmte_tp, m.org_tp, m.connected_org_nm, c.transaction_amt FROM fec.pac_committee_master m JOIN " +
                "fec.pac_to_candidate_contributions c ON m.cand_id = c.cand_id  WHERE c.bioguide_id = \'" + bioguideId
                + "\' AND c.transation_ts > " + beginTimestamp + " AND c.transation_ts < " + endTimestamp + ") " +
                "candidate_receipts GROUP BY bioguide_id, cmte_id, cmte_nm, cmte_pty_affiliation, cmte_tp, org_tp, " +
                "connected_org_nm";

        return impalaTemplate.query(query, new PacPoliticianContrMapper(beginTimestamp, endTimestamp));
    }
}
