package com.poliana.core.industryFinance.mapppers;

import com.poliana.core.industryFinance.entities.IndustryPoliticianContributions;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author David Gilmore
 * @date 12/12/13
 */
public class LegislatorRecievedIndustryTotalsMapper implements RowMapper<IndustryPoliticianContributions> {

    private long beginTimestamp;
    private long endTimestamp;

    public LegislatorRecievedIndustryTotalsMapper(long beginTimestamp, long endTimestamp) {
        this.beginTimestamp = beginTimestamp;
        this.endTimestamp = endTimestamp;
    }

    @Override
    public IndustryPoliticianContributions mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {

        IndustryPoliticianContributions industry = new IndustryPoliticianContributions();

        industry.setIndustryId(rs.getString(2));
        industry.setAmount(rs.getInt(3));
        industry.setCatName(rs.getString(4));
        industry.setIndustry(rs.getString(5));
        industry.setSectorLong(rs.getString(6));

        return industry;
    }
}
