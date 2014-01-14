package com.poliana.core.politicianFinance.mappers;

import com.poliana.core.politicianFinance.entities.IndustryPoliticianContributionTotals;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author David Gilmore
 * @date 11/1/13
 */
public class IndToPolContrTotalsMapper implements RowMapper<IndustryPoliticianContributionTotals> {

    public IndustryPoliticianContributionTotals mapRow(ResultSet rs, int rowNum) throws SQLException {

        IndustryPoliticianContributionTotals totals = new IndustryPoliticianContributionTotals();

        totals.setBioguideId(rs.getString("bioguide_id"));
        totals.setParty(rs.getString("party"));
        totals.setIndustryId(rs.getString("industry_id"));
        totals.setContributionsCount(rs.getInt("contributions_count"));
        totals.setContributionSum(rs.getInt("contributions_total"));

        try {
            totals.setCongress(rs.getInt("congress"));
        }
        catch (SQLException e) {}

        try {
            totals.setYear(rs.getInt("year"));
        }
        catch (SQLException e) {}

        try {
            totals.setMonth(rs.getInt("month"));
        }
        catch (SQLException e) {}

        return totals;
    }
}
