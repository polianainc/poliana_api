package com.poliana.contributions.entities.mappers;

import com.poliana.contributions.entities.IndustryContrTotals;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IndustryContrTotalsMapper implements RowMapper<IndustryContrTotals> {
    public IndustryContrTotals mapRow(ResultSet rs, int rowNum) throws SQLException {
        IndustryContrTotals industryContrTotals = new IndustryContrTotals();
        industryContrTotals.setIndustryId(rs.getString("industry_id"));
        industryContrTotals.setTotalYea(rs.getInt("yea_total_count"));
        industryContrTotals.setTotalNay(rs.getInt("nay_total_count"));
        industryContrTotals.setDistinctYea(rs.getInt("yea_distinct_count"));
        industryContrTotals.setDistinctNay(rs.getInt("nay_distinct_count"));
        industryContrTotals.setSumYea(rs.getInt("yea_sum"));
        industryContrTotals.setSumNay(rs.getInt("nay_sum"));
        industryContrTotals.setDistinctDiff();
        industryContrTotals.setTotalDiff();
        industryContrTotals.setSumDiff();
        return industryContrTotals;
    }
}