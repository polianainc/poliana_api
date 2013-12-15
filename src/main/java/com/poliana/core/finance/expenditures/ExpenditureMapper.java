package com.poliana.core.finance.expenditures;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpenditureMapper implements RowMapper<Expenditure> {
    public Expenditure mapRow(ResultSet rs, int rowNum) throws SQLException {
        Expenditure expenditure = new Expenditure();
        expenditure.setId(rs.getString("id"));
        expenditure.setTransId(rs.getString("trans_id"));
        expenditure.setCrpFilerId(rs.getString("crp_filer_id"));
        expenditure.setRecipientCode(rs.getString("recip_code"));
        expenditure.setPacShort(rs.getString("pac_short"));
        expenditure.setCrpRecipName(rs.getString("crp_recip_name"));
        expenditure.setExpCode(rs.getString("exp_code"));
        expenditure.setAmount(rs.getDouble("amount"));
        expenditure.setCity(rs.getString("city"));
        expenditure.setDates(rs.getString("dates"));
        expenditure.setState(rs.getString("state"));
        expenditure.setZip(rs.getString("zip"));
        expenditure.setCmtelIdEf(rs.getString("cmtel_id_ef"));
        expenditure.setCandId(rs.getString("cand_id"));
        expenditure.setType(rs.getString("type"));
        expenditure.setDescrip(rs.getString("descrip"));
        expenditure.setPg(rs.getString("pg"));
        expenditure.setElecOther(rs.getString("elec_other"));
        expenditure.setEntType(rs.getString("ent_type"));
        expenditure.setSource(rs.getString("source"));
        return expenditure;
    }
}