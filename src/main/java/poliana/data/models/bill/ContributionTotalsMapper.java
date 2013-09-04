package poliana.data.models.bill;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContributionTotalsMapper implements RowMapper<ContributionTotals> {
    public ContributionTotals mapRow(ResultSet rs, int rowNum) throws SQLException {
        ContributionTotals contributionTotals = new ContributionTotals();
        contributionTotals.setIndustryId(rs.getString("industry_id"));
        contributionTotals.setTotalYea(rs.getInt("yea_total_count"));
        contributionTotals.setTotalNay(rs.getInt("nay_total_count"));
        contributionTotals.setDistinctYea(rs.getInt("yea_distinct_count"));
        contributionTotals.setDistinctNay(rs.getInt("nay_distinct_count"));
        contributionTotals.setSumYea(rs.getInt("yea_sum"));
        contributionTotals.setSumNay(rs.getInt("nay_sum"));
        return contributionTotals;
    }
}