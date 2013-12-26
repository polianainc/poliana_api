package com.poliana.core.pacFinance.mappers;

import com.poliana.core.pacFinance.entities.PacPoliticianContrTotals;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author David Gilmore
 * @date 12/19/13
 */
public class PacPoliticianContrMapper implements RowMapper<PacPoliticianContrTotals> {

    private int beginTimestamp;
    private int endTimestamp;

    public PacPoliticianContrMapper(int beginTimestamp, int endTimestamp) {
        this.beginTimestamp = beginTimestamp;
        this.endTimestamp = endTimestamp;
    }

    @Override
    public PacPoliticianContrTotals mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {

        PacPoliticianContrTotals pac = new PacPoliticianContrTotals();

        pac.setPacId(rs.getString("cmte_id"));
        pac.setPacName(rs.getString("cmte_nm"));
        pac.setPacParty(rs.getString("cmte_pty_affiliaction"));
        pac.setPacType(rs.getString("cmte_tp"));
        pac.setPacOrg(rs.getString("connected_org_nm"));
        pac.setOrgType(rs.getString("org_tp"));
        pac.setAmount(rs.getInt(8));

        return pac;
    }
}
