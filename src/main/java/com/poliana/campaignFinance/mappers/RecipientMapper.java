package com.poliana.campaignFinance.mappers;

import com.poliana.campaignFinance.models.demo.Recipient;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipientMapper implements RowMapper<Recipient> {
    public Recipient mapRow(ResultSet rs, int rowNum) throws SQLException {
        Recipient recipient = new Recipient();
        recipient.setIndustryId(rs.getString("industry_id"));
        recipient.setBioguideId(rs.getString("bioguide_id"));
        recipient.setName(rs.getString("first_name") + " " + rs.getString("last_name"));
        recipient.setContribution(rs.getDouble("sum"));
        return recipient;
    }
}