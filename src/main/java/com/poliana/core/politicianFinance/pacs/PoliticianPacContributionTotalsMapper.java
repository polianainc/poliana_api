package com.poliana.core.politicianFinance.pacs;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author David Gilmore
 * @date 1/26/14
 */
public class PoliticianPacContributionTotalsMapper implements RowMapper<PoliticianPacContributionsTotals> {

    private Long beginTimestamp;
    private Long endTimestamp;

    public PoliticianPacContributionTotalsMapper() {}

    public PoliticianPacContributionTotalsMapper(Long beginTimestamp, Long endTimestamp) {

        this.beginTimestamp = beginTimestamp;
        this.endTimestamp = endTimestamp;
    }

    public PoliticianPacContributionsTotals mapRow(ResultSet rs, int i) throws SQLException {

        PoliticianPacContributionsTotals totals = new PoliticianPacContributionsTotals();

        totals.setPacId(rs.getString("pac_id"));
        totals.setPacName(rs.getString("pac_name"));
        totals.setBioguideId(rs.getString("bioguide_id"));
        totals.setFirstName(rs.getString("first_name"));
        totals.setLastName(rs.getString("last_name"));
        totals.setParty(rs.getString("party"));
        totals.setReligion(rs.getString("religion"));
        totals.setContributionSum(rs.getInt("contribution_sum"));
        totals.setContributionCount(rs.getInt("contribution_count"));

        if (this.beginTimestamp != null)
            totals.setBeginTimestamp(this.beginTimestamp);

        if (this.endTimestamp != null)
            totals.setEndTimestamp(this.endTimestamp);

        return totals;
    }
}
