package com.poliana.codes.entities.mappers;

import com.poliana.codes.entities.CandidateIds;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CandidateIdsMapper implements RowMapper<CandidateIds> {
    public CandidateIds mapRow(ResultSet rs, int rowNum) throws SQLException {
        CandidateIds candidateIds = new CandidateIds();
        candidateIds.setRecipientExtId(rs.getString("recipient_ext_id"));
        candidateIds.setCrpName(rs.getString("crp_name"));
        candidateIds.setParty(rs.getString("party"));
        candidateIds.setDistIdRunFor(rs.getString("dist_id_run_for"));
        candidateIds.setFecCandId(rs.getString("fec_cand_id"));
        return candidateIds;
    }
}
