package com.poliana.core.campaignFinance.mappers;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author David Gilmore
 * @date 12/12/13
 */
public class LegislatorRecievedIndustryTotals implements ResultSetExtractor<HashMap<String,Integer>> {
    @Override
    public HashMap<String, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashMap<String,Integer> totals = new LinkedHashMap<>();

        while (rs.next())
            totals.put(rs.getString(2),rs.getInt(3));

        return totals;
    }
}
