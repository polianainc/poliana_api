package com.poliana.core.industryFinance.mapppers;

import com.poliana.core.industryFinance.entities.IndPartyTotals;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author David Gilmore
 * @date 11/12/13
 */
public class IndPartyTotalsMapper implements RowMapper<IndPartyTotals> {
    public IndPartyTotals mapRow(ResultSet rs, int rowNum) throws SQLException {
        IndPartyTotals totals = new IndPartyTotals();
        totals.setIndustryId(rs.getString("party"));
        totals.setCount(rs.getInt(2));
        totals.setSum(rs.getInt(3));
        totals.setAverage(rs.getInt(3),rs.getInt(2));
        totals.setYear(4);
        totals.setMonth(5);
        return totals;
    }
}
