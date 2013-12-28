package com.poliana.core.pacFinance;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author David Gilmore
 * @date 12/19/13
 */
public class PacPoliticianContrMapper implements RowMapper<PacPoliticianContrTotals> {

    @Override
    public PacPoliticianContrTotals mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {

        PacPoliticianContrTotals pac = new PacPoliticianContrTotals();

        pac.setPacId(rs.getString("cmte_id"));
        pac.setPacName(rs.getString("cmte_nm"));
        pac.setAmount(rs.getInt(3));

        return pac;
    }
}
