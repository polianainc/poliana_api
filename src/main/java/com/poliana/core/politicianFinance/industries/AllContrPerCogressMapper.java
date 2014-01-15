package com.poliana.core.politicianFinance.industries;

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
public class AllContrPerCogressMapper implements ResultSetExtractor<HashMap<Integer, List<IndustryPoliticianContributionTotals>>> {
    @Override
    public HashMap<Integer, List<IndustryPoliticianContributionTotals>> extractData(ResultSet rs) throws SQLException, DataAccessException {

        HashMap<Integer, List<IndustryPoliticianContributionTotals>> allTotals = new HashMap<>();
        ContributionMapper contrMapper = new ContributionMapper();

        int index = 1;
        while (rs.next()) {
            IndustryPoliticianContributionTotals totals = contrMapper.mapRow(rs, index);
            if (allTotals.containsKey(totals.getCongress()))
                allTotals.get(totals.getCongress()).add(totals);
            else {
                List<IndustryPoliticianContributionTotals> cycleTotals = new LinkedList<>();
                cycleTotals.add(totals);
                allTotals.put(totals.getCongress(), cycleTotals);
            }
            index++;
        }

        return allTotals;
    }

    class ContributionMapper implements RowMapper<IndustryPoliticianContributionTotals> {

        @Override
        public IndustryPoliticianContributionTotals mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {

            IndustryPoliticianContributionTotals ind = new IndustryPoliticianContributionTotals();

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

