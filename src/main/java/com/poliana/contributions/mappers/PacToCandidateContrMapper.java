package com.poliana.contributions.mappers;

import com.poliana.contributions.entities.PacToCandidateContribution;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PacToCandidateContrMapper implements RowMapper<PacToCandidateContribution> {
    public PacToCandidateContribution mapRow(ResultSet rs, int rowNum) throws SQLException {
        PacToCandidateContribution contribution = new PacToCandidateContribution();
        contribution.setCycle(rs.getString("cycle"));
        contribution.setFecCandId(rs.getString("fec_rec_no"));
        contribution.setPacId(rs.getString("pac_id"));
        contribution.setCid(rs.getString("cid"));
        contribution.setAmount(rs.getDouble("amount"));
        contribution.setDate(rs.getString("dates"));
        contribution.setRealCode(rs.getString("real_code"));
        contribution.setType(rs.getString("type"));
        contribution.setDi(rs.getString("di"));
        contribution.setFecCandId(rs.getString("fec_camd_id"));
        return contribution;
    }
}