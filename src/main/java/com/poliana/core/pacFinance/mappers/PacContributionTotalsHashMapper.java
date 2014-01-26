package com.poliana.core.pacFinance.mappers;

import com.poliana.core.pacFinance.entities.PacContributionTotalsMap;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * @author David Gilmore
 * @date 1/26/14
 */
public class PacContributionTotalsHashMapper implements ResultSetExtractor<PacContributionTotalsMap> {

    private String chamber;
    private Long beginTimestamp;
    private Long endTimestamp;

    public PacContributionTotalsHashMapper(String chamber) {

        this.chamber = chamber;
    }

    public PacContributionTotalsHashMapper(String chamber, long beginTimestamp, long endTimestamp) {

        this.chamber = chamber;
        this.beginTimestamp = beginTimestamp;
        this.endTimestamp = endTimestamp;
    }

    @Override
    public PacContributionTotalsMap extractData(ResultSet rs) throws SQLException, DataAccessException {

        PacContributionTotalsMap totals = new PacContributionTotalsMap();

        HashMap<String, Integer> sums = new HashMap<>();

        boolean detailsSet = false;
        while (rs.next()) {

            sums.put(rs.getString("bioguide_id"), rs.getInt("total"));

            if (!detailsSet) {

                try {
                    totals.setCongress(rs.getInt("congress"));
                }
                catch (SQLException e) {}

                if (this.chamber != null)
                    totals.setChamber(chamber);

                if (this.beginTimestamp != null)
                    totals.setBeginTimestamp(beginTimestamp);

                if (this.endTimestamp != null)
                    totals.setEndTimestamp(endTimestamp);

                detailsSet = true;
            }

        }

        return totals;
    }
}
