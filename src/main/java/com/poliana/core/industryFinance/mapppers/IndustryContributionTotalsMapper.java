package com.poliana.core.industryFinance.mapppers;

import com.poliana.core.industryFinance.entities.IndustryContributionTotals;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * @author David Gilmore
 * @date 1/6/14
 */
public class IndustryContributionTotalsMapper implements ResultSetExtractor<IndustryContributionTotals> {

    private String chamber;

    public IndustryContributionTotalsMapper(String chamber) {
        this.chamber = chamber;
    }

    @Override
    public IndustryContributionTotals extractData(ResultSet rs) throws SQLException, DataAccessException {

        IndustryContributionTotals chamberTotals = new IndustryContributionTotals();

        HashMap<String, Integer> sums = new HashMap<>();

        boolean detailsSet = false;
        while (rs.next()) {

            sums.put(rs.getString("bioguide_id"), rs.getInt("total"));

            if (!detailsSet) {
                if (chamber != null)
                    chamberTotals.setChamber(chamber);

                try {
                    chamberTotals.setIndustryId(rs.getString("industry_id"));
                }
                catch (SQLException e) {}

                try {
                    chamberTotals.setCategoryId(rs.getString("category_id"));
                }
                catch (SQLException e) {}

                chamberTotals.setIndustryName(rs.getString("industry"));
                chamberTotals.setCategoryName(rs.getString("cat_name"));
                chamberTotals.setSector(rs.getString("sector"));
                chamberTotals.setSectorLong(rs.getString("sector_long"));
                chamberTotals.setCongress(rs.getInt("congress"));
                detailsSet = true;
            }
        }

        chamberTotals.setSums(sums);

        return chamberTotals;
    }
}

