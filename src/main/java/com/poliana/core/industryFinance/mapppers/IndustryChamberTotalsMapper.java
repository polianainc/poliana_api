package com.poliana.core.industryFinance.mapppers;

import com.poliana.core.industryFinance.entities.IndustryChamberTotals;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * @author David Gilmore
 * @date 1/6/14
 */
public class IndustryChamberTotalsMapper implements ResultSetExtractor<IndustryChamberTotals> {

    private String chamber;

    public IndustryChamberTotalsMapper(String chamber) {
        this.chamber = chamber;
    }

    @Override
    public IndustryChamberTotals extractData(ResultSet rs) throws SQLException, DataAccessException {

        IndustryChamberTotals chamberTotals = new IndustryChamberTotals();

        HashMap<String, Integer> sums = new HashMap<>();

        boolean detailsSet = false;
        while (rs.next()) {

            sums.put(rs.getString("bioguide_id"), rs.getInt("total"));

            if (!detailsSet) {
                chamberTotals.setChamber(chamber);
                chamberTotals.setIndustryId(rs.getString("industry_id"));
                chamberTotals.setName(rs.getString("industry"));
                chamberTotals.setCongress(rs.getInt("congress"));
                detailsSet = true;
            }
        }

        chamberTotals.setSums(sums);

        return chamberTotals;
    }
}

