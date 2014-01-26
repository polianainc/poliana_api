package com.poliana.core.pacFinance.mappers;

import com.poliana.core.pacFinance.entities.PacPoliticianContributionTotals;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author David Gilmore
 * @date 12/19/13
 */
public class PacPoliticianContributionMapper implements RowMapper<PacPoliticianContributionTotals> {

    @Override
    public PacPoliticianContributionTotals mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {

        PacPoliticianContributionTotals pac = new PacPoliticianContributionTotals();

        pac.setPacId(rs.getString("cmte_id"));
        pac.setPacName(rs.getString("cmte_nm"));
        pac.setAmount(rs.getInt(3));

        return pac;
    }
}
