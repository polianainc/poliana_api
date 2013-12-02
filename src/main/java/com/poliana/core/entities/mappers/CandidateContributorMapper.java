package com.poliana.core.entities.mappers;

import com.poliana.core.entities.entities.CandidateContributor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CandidateContributorMapper implements RowMapper<CandidateContributor> {
    public CandidateContributor mapRow(ResultSet rs, int rowNum) throws SQLException {
        CandidateContributor contributor = new CandidateContributor();
        contributor.setCycle(rs.getString("cycle"));
        contributor.setFecCandidateId(rs.getString("fec_candi_id"));
        contributor.setCid(rs.getString("cid"));
        contributor.setNameParty(rs.getString("name_party"));
        contributor.setParty(rs.getString("party"));
        contributor.setDistIdRunFor(rs.getString("dist_id_run_for"));
        contributor.setCurrCand(rs.getString("curr_cand"));
        contributor.setDistIdcurrent(rs.getString("dist_id_curr"));
        contributor.setCycleCand(rs.getString("cycle_cand"));
        contributor.setCrpType(rs.getString("crp_type"));
        contributor.setRecipientCode(rs.getString("recip_code"));
        contributor.setNoPacs(rs.getString("no_pacs"));
        return contributor;
    }
}