package com.poliana.core.industryFinance.mapppers;

import com.poliana.core.industryFinance.entities.IndToPolContrTotals;
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
 * @date 12/27/13
 */
public class AllContrPerCogressMapper implements ResultSetExtractor<HashMap<Integer, List<IndToPolContrTotals>>> {
    @Override
    public HashMap<Integer, List<IndToPolContrTotals>> extractData(ResultSet rs) throws SQLException, DataAccessException {

        HashMap<Integer, List<IndToPolContrTotals>> allTotals = new HashMap<>();
        ContributionMapper contrMapper = new ContributionMapper();

        int index = 1;
        while (rs.next()) {
            IndToPolContrTotals totals = contrMapper.mapRow(rs, index);
            if (allTotals.containsKey(totals.getCycle()))
                allTotals.get(totals.getCycle()).add(totals);
            else {
                List<IndToPolContrTotals> cycleTotals = new LinkedList<>();
                cycleTotals.add(totals);
                allTotals.put(totals.getCycle(), cycleTotals);
            }
            index++;
        }

        return allTotals;
    }

    class ContributionMapper implements RowMapper<IndToPolContrTotals> {

        @Override
        public IndToPolContrTotals mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {

            IndToPolContrTotals ind = new IndToPolContrTotals();

            ind.setBioguideId(rs.getString("bioguide_id"));
            ind.setIndustryId(rs.getString("real_code"));
            ind.setIndustryName(rs.getString("industry"));
            ind.setSector(rs.getString("sector"));
            ind.setSectorLong(rs.getString("sector_long"));
            ind.setCycle(rs.getInt("congress"));
            ind.setContributionSum(rs.getInt(7));

            return ind;
        }
    }
}

