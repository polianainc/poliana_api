package poliana.data.models.bill;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContributionMapper  implements RowMapper<Contribution> {
    public Contribution mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contribution contribution = new Contribution();
        contribution.setIndustryId(rs.getString("industry_id"));
        contribution.setTotalYea(rs.getInt("total_yea_count"));
        contribution.setTotalNay(rs.getInt("total_nay_count"));
        contribution.setDistinctYea(rs.getInt("total_diff"));
        contribution.setDistinctNay(rs.getInt("distinct_yea_count"));
        contribution.setSumYea(rs.getInt("distinct_nay_count"));
        contribution.setSumNay(rs.getInt("distinct_diff"));
        return contribution;
    }
}