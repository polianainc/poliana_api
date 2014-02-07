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
public class IndustryContributionsPerCogressMapper implements ResultSetExtractor<HashMap<Integer, List<PoliticianIndustryContributionsTotals>>> {

    @Override
    public HashMap<Integer, List<PoliticianIndustryContributionsTotals>> extractData(ResultSet rs) throws SQLException, DataAccessException {

        HashMap<Integer, List<PoliticianIndustryContributionsTotals>> allTotals = new HashMap<>();
        ContributionMapper contrMapper = new ContributionMapper();

        int index = 1;
        while (rs.next()) {
            PoliticianIndustryContributionsTotals totals = contrMapper.mapRow(rs, index);
            if (allTotals.containsKey(totals.getCongress()))
                allTotals.get(totals.getCongress()).add(totals);
            else {
                List<PoliticianIndustryContributionsTotals> cycleTotals = new LinkedList<>();
                cycleTotals.add(totals);
                allTotals.put(totals.getCongress(), cycleTotals);
            }
            index++;
        }

        return allTotals;
    }

    class ContributionMapper implements RowMapper<PoliticianIndustryContributionsTotals> {

        @Override
        public PoliticianIndustryContributionsTotals mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {

            PoliticianIndustryContributionsTotals ind = new PoliticianIndustryContributionsTotals();

            ind.setBioguideId(rs.getString("bioguide_id"));
            ind.setFirstName(rs.getString("first_name"));
            ind.setLastName(rs.getString("last_name"));
            ind.setParty(rs.getString("party"));
            ind.setReligion(rs.getString("religion"));

            try {
                ind.setCategoryId(rs.getString("category_id"));
            } catch (Exception e) {}

            try {
                ind.setIndustryId(rs.getString("industry_id"));
            } catch (Exception e) {}

            try {
                ind.setCategoryName(rs.getString("category_name"));
            } catch (Exception e) {}

            try {
                ind.setIndustryName(rs.getString("industry"));
            } catch (Exception e) {}


            ind.setSector(rs.getString("sector"));
            ind.setSectorLong(rs.getString("sector_long"));

            ind.setCongress(rs.getInt("congress"));

            ind.setContributionCount(rs.getInt("contribution_count"));
            ind.setContributionSum(rs.getInt("contribution_sum"));

            return ind;
        }
    }
}

