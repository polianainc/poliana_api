package com.poliana.core.finance.pacs.mappers;

import com.poliana.core.finance.pacs.entities.PacPoliticianContrTotals;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        pac.setPacNames(splitPacNames(rs.getString("cmte_id")));
        pac.setPacId(rs.getString("cmte_id"));
        pac.setPacId(rs.getString("cmte_id"));
        pac.setPacId(rs.getString("cmte_id"));

        return pac;
    }

    private List<String> splitPacNames(String pacNames) {
        return new ArrayList<>(Arrays.asList(pacNames.split("\t")));
    }
}
