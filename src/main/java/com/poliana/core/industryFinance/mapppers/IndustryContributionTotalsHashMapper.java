package com.poliana.core.industryFinance.mapppers;

import com.poliana.core.industryFinance.entities.IndustryContributionTotalsHashMap;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * @author David Gilmore
 * @date 1/6/14
 */
public class IndustryContributionTotalsHashMapper implements ResultSetExtractor<IndustryContributionTotalsHashMap> {

    private String chamber;
    private Long beginTimestamp;
    private Long endTimestamp;

    public IndustryContributionTotalsHashMapper(String chamber) {
        this.chamber = chamber;
    }

    public IndustryContributionTotalsHashMapper(long beginTimestamp, long endTimestamp) {
        this.beginTimestamp = beginTimestamp;
        this.endTimestamp = endTimestamp;
    }

    @Override
    public IndustryContributionTotalsHashMap extractData(ResultSet rs) throws SQLException, DataAccessException {

        IndustryContributionTotalsHashMap totals = new IndustryContributionTotalsHashMap();

        HashMap<String, Integer> sums = new HashMap<>();

        boolean detailsSet = false;
        while (rs.next()) {

            sums.put(rs.getString("bioguide_id"), rs.getInt("total"));

            if (!detailsSet) {
                if (chamber != null)
                    totals.setChamber(chamber);

                try {
                    totals.setIndustryId(rs.getString("industry_id"));
                }
                catch (SQLException e) {}

                try {
                    totals.setCategoryId(rs.getString("category_id"));
                }
                catch (SQLException e) {}

                totals.setIndustryName(rs.getString("industry"));
                totals.setCategoryName(rs.getString("cat_name"));
                totals.setSector(rs.getString("sector"));
                totals.setSectorLong(rs.getString("sector_long"));

                try {
                    totals.setCongress(rs.getInt("congress"));
                }
                catch (SQLException e) {}

                if (this.beginTimestamp != null)
                    totals.setBeginTimestamp(beginTimestamp);

                if (this.endTimestamp != null)
                    totals.setBeginTimestamp(endTimestamp);

                detailsSet = true;
            }
        }

        totals.setSums(sums);

        return totals;
    }
}

