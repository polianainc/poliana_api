package com.poliana.entities.mappers;

import com.poliana.entities.entities.Legislator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LegislatorMapper implements RowMapper<Legislator> {
    public Legislator mapRow(ResultSet rs, int rowNum) throws SQLException {
        Legislator legislator = new Legislator();
        legislator.setBioguideId(rs.getString("bioguide_id"));
        legislator.setFirstName(rs.getString("first_name"));
        legislator.setLastName(rs.getString("last_name"));
        legislator.setOfficialFull(rs.getString("official_full"));
        legislator.setParty(rs.getString("party"));
        legislator.setThomasId(rs.getString("thomas_id"));
        legislator.setOpensecretsId(rs.getString("opensecrets_id"));
        legislator.setFecId(rs.getString("fec_id"));
        legislator.setVotesmartId(rs.getString("votesmart_id"));
        legislator.setBallotpedia(rs.getString("ballotpedia"));
        legislator.setLisId(rs.getString("lis_id"));
        legislator.setWikipediaId(rs.getString("wikipedia_id"));
        legislator.setGovtrackId(rs.getString("govtrack_id"));
        legislator.setMaplightId(rs.getString("maplight_id"));
        legislator.setIcsprId(rs.getString("icpsr_id"));
        legislator.setCspan(rs.getString("cspan_id"));
        legislator.setHouseHistoryId(rs.getString("house_history_id"));
        legislator.setWashingtonPostId(rs.getString("washington_post_id"));
        legislator.setGender(rs.getString("gender"));
        legislator.setBirthday(rs.getString("birthday"));
        legislator.setReligion(rs.getString("religion"));
        return legislator;
    }
}
