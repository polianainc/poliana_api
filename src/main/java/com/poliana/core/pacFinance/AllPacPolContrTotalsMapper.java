package com.poliana.core.pacFinance;

import com.poliana.core.pacFinance.entities.PacPoliticianContributionTotals;
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
public class AllPacPolContrTotalsMapper implements ResultSetExtractor<HashMap<Integer, List<PacPoliticianContributionTotals>>> {
    @Override
    public HashMap<Integer, List<PacPoliticianContributionTotals>> extractData(ResultSet rs)
            throws SQLException, DataAccessException {

        HashMap<Integer, List<PacPoliticianContributionTotals>> allTotals = new HashMap<>();
        ContributionMapper contrMapper = new ContributionMapper();

        int index = 1;
        while (rs.next()) {
            PacPoliticianContributionTotals totals = contrMapper.mapRow(rs, index);
            if (allTotals.containsKey(totals.getCycle()))
                allTotals.get(totals.getCycle()).add(totals);
            else {
                List<PacPoliticianContributionTotals> cycleTotals = new LinkedList<>();
                cycleTotals.add(totals);
                allTotals.put(totals.getCycle(), cycleTotals);
            }
            index++;
        }

        return allTotals;
    }

    class ContributionMapper implements RowMapper<PacPoliticianContributionTotals> {

        @Override
        public PacPoliticianContributionTotals mapRow(ResultSet rs, int rowNum)
                throws SQLException, DataAccessException {

            PacPoliticianContributionTotals pac = new PacPoliticianContributionTotals();

            pac.setBioguideId(rs.getString("bioguide_id"));
            pac.setPacId(rs.getString("cmte_id"));
            pac.setPacName(rs.getString("cmte_nm"));
            pac.setCycle(rs.getInt(4));
            pac.setAmount(rs.getInt(5));

            return pac;
        }
    }
}
