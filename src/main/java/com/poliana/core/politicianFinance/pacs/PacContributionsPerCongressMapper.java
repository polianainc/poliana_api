package com.poliana.core.politicianFinance.pacs;

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
 * @date 1/28/14
 */
public class PacContributionsPerCongressMapper implements ResultSetExtractor<HashMap<Integer, List<PoliticianPacContributionsTotals>>> {

    @Override
    public HashMap<Integer, List<PoliticianPacContributionsTotals>> extractData(ResultSet rs) throws SQLException, DataAccessException {

        HashMap<Integer, List<PoliticianPacContributionsTotals>> allTotals = new HashMap<>();
        ContributionMapper contrMapper = new ContributionMapper();

        int index = 1;
        while (rs.next()) {
            PoliticianPacContributionsTotals totals = contrMapper.mapRow(rs, index);
            if (allTotals.containsKey(totals.getCongress()))
                allTotals.get(totals.getCongress()).add(totals);
            else {
                List<PoliticianPacContributionsTotals> cycleTotals = new LinkedList<>();
                cycleTotals.add(totals);
                allTotals.put(totals.getCongress(), cycleTotals);
            }
            index++;
        }

        return allTotals;
    }

    class ContributionMapper implements RowMapper<PoliticianPacContributionsTotals> {

        @Override
        public PoliticianPacContributionsTotals mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {

            PoliticianPacContributionsTotals pac = new PoliticianPacContributionsTotals();

            return pac;
        }
    }
}