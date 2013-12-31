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
public class SponsorshipRSMapper implements ResultSetExtractor<HashMap<Integer, List<Sponsorship>>> {
    @Override
    public HashMap<Integer, List<Sponsorship>> extractData(ResultSet rs)
            throws SQLException, DataAccessException {

        HashMap<Integer, List<Sponsorship>> allTotals = new HashMap<>();
        SponsorshipMapper sponsorshipMapper = new SponsorshipMapper();

        int index = 1;
        while (rs.next()) {
            Sponsorship totals = sponsorshipMapper.mapRow(rs, index);
            if (allTotals.containsKey(totals.getCongress()))
                allTotals.get(totals.getCongress()).add(totals);
            else {
                List<Sponsorship> cycleTotals = new LinkedList<>();
                cycleTotals.add(totals);
                allTotals.put(totals.getCongress(), cycleTotals);
            }
            index++;
        }

        return allTotals;
    }

    class SponsorshipMapper implements RowMapper<Sponsorship> {

        @Override
        public Sponsorship mapRow(ResultSet rs, int rowNum)
                throws SQLException, DataAccessException {

            Sponsorship s = new Sponsorship();

            s.setSponsor(rs.getString("sponsor"));
            s.setCosponsor(rs.getString("cosponsor"));
            s.setCongress(Integer.parseInt(rs.getString("congress")));
            s.setCount(rs.getInt("counts"));
            s.setChamber(rs.getString("bill_type"));

            return s;
        }
    }
}
