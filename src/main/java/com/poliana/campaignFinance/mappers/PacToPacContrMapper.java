package com.poliana.campaignFinance.mappers;

import com.poliana.campaignFinance.entities.PacToPacContribution;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PacToPacContrMapper implements RowMapper<PacToPacContribution> {
    public PacToPacContribution mapRow(ResultSet rs, int rowNum) throws SQLException {
        PacToPacContribution contribution = new PacToPacContribution();
        contribution.setCycle(rs.getString("cycle"));
        contribution.setFecRecNo(rs.getString("fec_rec_no"));
        contribution.setFileRid(rs.getString("file_rid"));
        contribution.setDonorCmte(rs.getString("donor_cmte"));
        contribution.setContribLendTrans(rs.getString("contrib_lend_trans"));
        contribution.setCity(rs.getString("city"));
        contribution.setState(rs.getString("state"));
        contribution.setZip(rs.getString("zip"));
        contribution.setFecOccEmp(rs.getString("fec_occ_emp"));
        contribution.setPrimCode(rs.getString("prim_code"));
        contribution.setDate(rs.getString("dates"));
        contribution.setAmount(rs.getDouble("amount"));
        contribution.setRecipCode(rs.getString("recip_id"));
        contribution.setParty(rs.getString("party"));
        contribution.setOtherId(rs.getString("other_id"));
        contribution.setRecipCode(rs.getString("recip_code"));
        contribution.setRecipCode(rs.getString("recip_prim_code"));
        contribution.setAmend(rs.getString("amend"));
        contribution.setReport(rs.getString("report"));
        contribution.setPg(rs.getString("pg"));
        contribution.setMicrofilm(rs.getString("microfilm"));
        contribution.setType(rs.getString("type"));
        contribution.setRealCode(rs.getString("real_code"));
        contribution.setSource(rs.getString("source"));
        return contribution;
    }
}