package poliana.data.repositories;

import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;

public class HiveJdbcRepository {

    protected JdbcTemplate hiveTemplate;
    protected FileSystemResourceLoader resourceLoader;

    public HiveJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.hiveTemplate = jdbcTemplate;
    }

}
