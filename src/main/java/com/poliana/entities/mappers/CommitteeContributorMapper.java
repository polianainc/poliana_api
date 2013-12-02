package com.poliana.entities.mappers;

import com.poliana.entities.entities.CommitteeContributor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommitteeContributorMapper implements RowMapper<CommitteeContributor> {
    public CommitteeContributor mapRow(ResultSet rs, int rowNum) throws SQLException {
        CommitteeContributor contributor = new CommitteeContributor();
        contributor.setCycle(rs.getString("cycle"));
        contributor.setCmtelId(rs.getString("cmtel_id"));
        contributor.setPacShort(rs.getString("pac_short"));
        contributor.setAffiliate(rs.getString("affiliate"));
        contributor.setUiTorg(rs.getString("ui_torg"));
        contributor.setRecipientId(rs.getString("recip_id"));
        contributor.setRecipCode(rs.getString("recip_code"));
        contributor.setFecCandId(rs.getString("fec_cand_id"));
        contributor.setParty(rs.getString("party"));
        contributor.setPrimCode(rs.getString("prim_code"));
        contributor.setSource(rs.getString("source"));
        contributor.setSensitive(rs.getString("sensitive"));
        contributor.setForeign(rs.getString("foreign"));
        contributor.setActive(rs.getString("active"));
        return contributor;
    }
}