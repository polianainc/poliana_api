package com.poliana.core.industryFinance.mapppers;

import com.poliana.core.industryFinance.entities.IndustryContributionSummary;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IndustryContrTotalsMapper implements RowMapper<IndustryContributionSummary> {
    public IndustryContributionSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
        IndustryContributionSummary industryContributionSummary = new IndustryContributionSummary();
        industryContributionSummary.setIndustryId(rs.getString("industry_id"));
        industryContributionSummary.setTotalYea(rs.getInt("yea_total_count"));
        industryContributionSummary.setTotalNay(rs.getInt("nay_total_count"));
        industryContributionSummary.setDistinctYea(rs.getInt("yea_distinct_count"));
        industryContributionSummary.setDistinctNay(rs.getInt("nay_distinct_count"));
        industryContributionSummary.setSumYea(rs.getInt("yea_sum"));
        industryContributionSummary.setSumNay(rs.getInt("nay_sum"));
        industryContributionSummary.setDistinctDiff();
        industryContributionSummary.setTotalDiff();
        industryContributionSummary.setSumDiff();
        return industryContributionSummary;
    }
}