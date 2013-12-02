package com.poliana.core.entities.mappers;

import com.poliana.core.entities.entities.Industry;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IndustryMapper implements RowMapper<Industry> {
    public Industry mapRow(ResultSet rs, int rowNum) throws SQLException {
        Industry industry = new Industry();
        industry.setIndustryId(rs.getString("cat_code"));
        industry.setName(rs.getString("cat_name"));
        industry.setOrder(rs.getString("cat_order"));
        industry.setIndustry(rs.getString("industry"));
        industry.setSector(rs.getString("sector"));
        industry.setSectorLong(rs.getString("sector_long"));
        return industry;
    }
}
