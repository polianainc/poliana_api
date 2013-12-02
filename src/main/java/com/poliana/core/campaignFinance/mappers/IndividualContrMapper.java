package com.poliana.core.campaignFinance.mappers;

import com.poliana.core.campaignFinance.entities.IndividualContribution;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IndividualContrMapper implements RowMapper<IndividualContribution> {
    public IndividualContribution mapRow(ResultSet rs, int rowNum) throws SQLException {
        IndividualContribution contribution = new IndividualContribution();
        contribution.setCycle(rs.getString("cycle"));
        contribution.setFecTransId(rs.getString("fec_trans_id"));
        contribution.setContribId(rs.getString("contrib_id"));
        contribution.setContrib(rs.getString("contrib"));
        contribution.setRecipId(rs.getString("recip_id"));
        contribution.setOrgName(rs.getString("org_name"));
        contribution.setUltOrg(rs.getString("ult_org"));
        contribution.setRealCode(rs.getString("real_code"));
        contribution.setDate(rs.getString("dates"));
        contribution.setAmount(rs.getDouble("amount"));
        contribution.setStreet(rs.getString("street"));
        contribution.setCity(rs.getString("city"));
        contribution.setState(rs.getString("state"));
        contribution.setZip(rs.getString("zip"));
        contribution.setRecipCode(rs.getString("recip_code"));
        contribution.setType(rs.getString("type"));
        contribution.setCmtelId(rs.getString("cmtel_id"));
        contribution.setOtherId(rs.getString("other_id"));
        contribution.setGender(rs.getString("gender"));
        contribution.setMicrofilm(rs.getString("microfilm"));
        contribution.setOccupation(rs.getString("occupation"));
        contribution.setEmployer(rs.getString("employer"));
        contribution.setSource(rs.getString("source"));
        return contribution;
    }
}
