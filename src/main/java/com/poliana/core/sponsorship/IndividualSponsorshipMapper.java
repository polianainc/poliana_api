package com.poliana.core.sponsorship;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author David Gilmore
 * @date 11/27/13
 */
public class IndividualSponsorshipMapper implements RowMapper<Sponsorship> {

    private String chamber;
    private int congress;

    public IndividualSponsorshipMapper(String chamber, int congress) {
        this.chamber = chamber;
        this.congress = congress;
    }

    public Sponsorship mapRow(ResultSet rs, int rowNum) throws SQLException {
        Sponsorship sponsorship = new Sponsorship();
        sponsorship.setChamber(chamber);
        sponsorship.setSponsor(rs.getString(1));
        sponsorship.setCosponsor(rs.getString(2));
        sponsorship.setCount(rs.getInt(3));
        sponsorship.setCongress(congress);
        return sponsorship;
    }
}
