package com.poliana.core.sponsorship;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author David Gilmore
 * @date 12/28/13
 */
public class SponsorshipRSMapper implements ResultSetExtractor<HashMap<Integer, List<SponsorshipCount>>> {
    @Override
    public HashMap<Integer, List<SponsorshipCount>> extractData(ResultSet rs)
            throws SQLException, DataAccessException {

        HashMap<Integer, List<SponsorshipCount>> allTotals = new HashMap<>();
        SponsorshipMapper sponsorshipMapper = new SponsorshipMapper();

        int index = 1;
        while (rs.next()) {
            SponsorshipCount totals = sponsorshipMapper.mapRow(rs, index);
            if (allTotals.containsKey(totals.getCongress()))
                allTotals.get(totals.getCongress()).add(totals);
            else {
                List<SponsorshipCount> cycleTotals = new LinkedList<>();
                cycleTotals.add(totals);
                allTotals.put(totals.getCongress(), cycleTotals);
            }
            index++;
        }

        return allTotals;
    }

    class SponsorshipMapper implements RowMapper<SponsorshipCount> {

        @Override
        public SponsorshipCount mapRow(ResultSet rs, int rowNum)
                throws SQLException, DataAccessException {

            SponsorshipCount s = new SponsorshipCount();

            s.setSponsor(rs.getString("sponsor"));
            s.setCosponsor(rs.getString("cosponsor"));
            s.setCongress(Integer.parseInt(rs.getString("congress")));
            s.setCount(rs.getInt("counts"));
            s.setChamber(rs.getString("bill_type"));

            return s;
        }
    }
}
