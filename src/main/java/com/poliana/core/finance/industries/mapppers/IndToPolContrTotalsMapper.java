package com.poliana.core.finance.industries.mapppers;

import com.poliana.core.finance.industries.entities.IndToPolContrTotals;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author David Gilmore
 * @date 11/1/13
 */
public class IndToPolContrTotalsMapper implements RowMapper<IndToPolContrTotals> {
    public IndToPolContrTotals mapRow(ResultSet rs, int rowNum) throws SQLException {
        IndToPolContrTotals totals = new IndToPolContrTotals();
        totals.setBioguideId(rs.getString("bioguide_id"));
        totals.setParty(rs.getString("party"));
        totals.setIndustryId(rs.getString("industry_id"));
        totals.setContributionsCount(rs.getInt("contributions_count"));
        totals.setContributionSum(rs.getInt("contributions_total"));
        totals.setYear(rs.getInt("year"));
        totals.setMonth(rs.getInt("month"));
        return totals;
    }
}
