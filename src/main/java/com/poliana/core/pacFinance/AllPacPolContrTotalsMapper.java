package com.poliana.core.pacFinance;

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
 * @date 12/26/13
 */
public class AllPacPolContrTotalsMapper implements ResultSetExtractor<HashMap<Integer, List<PacPoliticianContrTotals>>> {
    @Override
    public HashMap<Integer, List<PacPoliticianContrTotals>> extractData(ResultSet rs)
            throws SQLException, DataAccessException {

        HashMap<Integer, List<PacPoliticianContrTotals>> allTotals = new HashMap<>();
        ContributionMapper contrMapper = new ContributionMapper();

        int index = 1;
        while (rs.next()) {
            PacPoliticianContrTotals totals = contrMapper.mapRow(rs, index);
            if (allTotals.containsKey(totals.getCycle()))
                allTotals.get(totals.getCycle()).add(totals);
            else {
                List<PacPoliticianContrTotals> cycleTotals = new LinkedList<>();
                cycleTotals.add(totals);
                allTotals.put(totals.getCycle(), cycleTotals);
            }
            index++;
        }

        return allTotals;
    }

    class ContributionMapper implements RowMapper<PacPoliticianContrTotals> {

        @Override
        public PacPoliticianContrTotals mapRow(ResultSet rs, int rowNum)
                throws SQLException, DataAccessException {

            PacPoliticianContrTotals pac = new PacPoliticianContrTotals();

            pac.setBioguideId(rs.getString("bioguide_id"));
            pac.setPacId(rs.getString("cmte_id"));
            pac.setPacName(rs.getString("cmte_nm"));
            pac.setCycle(rs.getInt(4));
            pac.setAmount(rs.getInt(5));

            return pac;
        }
    }
}
