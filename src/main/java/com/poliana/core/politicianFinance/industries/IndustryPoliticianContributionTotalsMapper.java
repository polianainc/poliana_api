package com.poliana.core.politicianFinance.industries;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author David Gilmore
 * @date 11/1/13
 */
public class IndustryPoliticianContributionTotalsMapper implements RowMapper<IndustryPoliticianContributionTotals> {

    private Long beginTimestamp;
    private Long endTimestamp;

    public IndustryPoliticianContributionTotalsMapper() {}

    public IndustryPoliticianContributionTotalsMapper(long beginTimestamp, long endTimestamp) {

        this.beginTimestamp = beginTimestamp;
        this.endTimestamp = endTimestamp;
    }

    public IndustryPoliticianContributionTotals mapRow(ResultSet rs, int rowNum) throws SQLException {

        IndustryPoliticianContributionTotals totals = new IndustryPoliticianContributionTotals();

        try {
            totals.setBioguideId(rs.getString("bioguide_id"));
        }
        catch (SQLException e) {}

        try {
            totals.setFirstName(rs.getString("first_name"));
        }
        catch (SQLException e) {}

        try {
            totals.setLastName(rs.getString("last_name"));
        }
        catch (SQLException e) {}

        try {
            totals.setParty(rs.getString("party"));
        }
        catch (SQLException e) {}

        try {
            totals.setReligion(rs.getString("religion"));
        }
        catch (SQLException e) {}

        try {
            totals.setIndustryId(rs.getString("industry_id"));
        }
        catch (SQLException e) {}

        try {
            totals.setIndustryName(rs.getString("industry"));
        }
        catch (SQLException e) {}

        try {
            totals.setSector(rs.getString("sector"));
        }
        catch (SQLException e) {}

        try {
            totals.setSectorLong(rs.getString("sector_long"));
        }
        catch (SQLException e) {}

        try {
            totals.setCategoryName(rs.getString("category_name"));
        }
        catch (SQLException e) {}

        try {
            totals.setCategoryId(rs.getString("category_id"));
        }
        catch (SQLException e) {}

        try {
            totals.setContributionCount(rs.getInt("contribution_count"));
        }
        catch (SQLException e) {}

        try {
            totals.setContributionSum(rs.getInt("contribution_sum"));
        }
        catch (SQLException e) {}

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

        if (this.beginTimestamp != null)
            totals.setBeginTimestamp(this.beginTimestamp);

        if (this.endTimestamp != null)
            totals.setEndTimestamp(this.endTimestamp);


        return totals;
    }
}
