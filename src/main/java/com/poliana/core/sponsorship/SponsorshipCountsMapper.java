package com.poliana.core.sponsorship;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author David Gilmore
 * @date 11/27/13
 */
public class SponsorshipCountsMapper implements RowMapper<SponsorshipCount> {

    private String chamber;
    private int congress;

    public SponsorshipCountsMapper(String chamber, int congress) {
        this.chamber = chamber;
        this.congress = congress;
    }

    public SponsorshipCount mapRow(ResultSet rs, int rowNum) throws SQLException {
        SponsorshipCount sponsorship = new SponsorshipCount();
        sponsorship.setChamber(chamber);
        sponsorship.setSponsor(rs.getString(1));
        sponsorship.setCosponsor(rs.getString(2));
        sponsorship.setCount(rs.getInt(3));
        sponsorship.setCongress(congress);
        return sponsorship;
    }
}
