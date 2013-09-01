package poliana.data.repositories;

import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import poliana.data.models.bill.ContributionMapper;
import poliana.data.models.bill.Contribution;

import java.sql.PreparedStatement;
import java.util.List;

public class HiveJdbcRepository {

    protected JdbcTemplate hiveTemplate;
    protected FileSystemResourceLoader resourceLoader;


    public HiveJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.hiveTemplate = jdbcTemplate;
    }

    public void createTable() {}

    public void showTables() {}

    public List<Contribution> s743_financials() {
        hiveTemplate.execute("SELECT * FROM s743_financials");
        List<Contribution> table = hiveTemplate.query("SELECT * FROM s743_financials", new ContributionMapper());
        return table;
    }

}
