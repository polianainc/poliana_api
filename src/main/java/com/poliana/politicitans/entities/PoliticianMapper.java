package com.poliana.politicitans.entities;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PoliticianMapper  implements RowMapper<Politician> {
    public Politician mapRow(ResultSet rs, int rowNum) throws SQLException {
        Politician politician = new Politician();
        politician.setBioguideId(rs.getString("bioguide_id"));
        politician.setRecipientExtId(rs.getString("recipient_ext_id"));
        politician.setChamber(rs.getString("chamber"));
        politician.setStateName(rs.getString("state_name"));
        politician.setFirstName(rs.getString("first_name"));
        politician.setLastName(rs.getString("last_name"));
        politician.setParty(rs.getString("party"));
        politician.setInOffice(rs.getString("in_office"));
        politician.setWebsite(rs.getString("website"));
        politician.setContactForm(rs.getString("contact_form"));
        return politician;
    }
}
