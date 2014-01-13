package com.poliana.core.politicianFinance.mappers;

import com.poliana.core.politicianFinance.entities.IndustryPoliticianContributions;
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
public class AllContrPerCogressMapper implements ResultSetExtractor<HashMap<Integer, List<IndustryPoliticianContributions>>> {
    @Override
    public HashMap<Integer, List<IndustryPoliticianContributions>> extractData(ResultSet rs) throws SQLException, DataAccessException {

        HashMap<Integer, List<IndustryPoliticianContributions>> allTotals = new HashMap<>();
        ContributionMapper contrMapper = new ContributionMapper();

        int index = 1;
        while (rs.next()) {
            IndustryPoliticianContributions totals = contrMapper.mapRow(rs, index);
            if (allTotals.containsKey(totals.getCongress()))
                allTotals.get(totals.getCongress()).add(totals);
            else {
                List<IndustryPoliticianContributions> cycleTotals = new LinkedList<>();
                cycleTotals.add(totals);
                allTotals.put(totals.getCongress(), cycleTotals);
            }
            index++;
        }

        return allTotals;
    }

    class ContributionMapper implements RowMapper<IndustryPoliticianContributions> {

        @Override
        public IndustryPoliticianContributions mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {

            IndustryPoliticianContributions ind = new IndustryPoliticianContributions();

            ind.setBioguideId(rs.getString("bioguide_id"));
            ind.setIndustryId(rs.getString("real_code"));
            ind.setIndustryName(rs.getString("industry"));
            ind.setSector(rs.getString("sector"));
            ind.setSectorLong(rs.getString("sector_long"));
            ind.setCongress(rs.getInt("congress"));
            ind.setContributionSum(rs.getInt(7));

            return ind;
        }
    }
}

