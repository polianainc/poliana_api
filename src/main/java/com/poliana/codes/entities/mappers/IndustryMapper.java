package com.poliana.codes.entities.mappers;

import com.poliana.codes.entities.Industry;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IndustryMapper implements RowMapper<Industry> {
    public Industry mapRow(ResultSet rs, int rowNum) throws SQLException {
        Industry industry = new Industry();
        industry.setCatCode(rs.getString("catcode"));
        industry.setCatName(rs.getString("catname"));
        industry.setCatOrder(rs.getString("catorder"));
        industry.setIndustry(rs.getString("industry"));
        industry.setSector(rs.getString("sector"));
        industry.setSectorLong(rs.getString("sector_long"));
        return industry;
    }
}
