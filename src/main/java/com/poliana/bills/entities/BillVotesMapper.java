package com.poliana.bills.entities;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BillVotesMapper implements RowMapper<BillVote> {
    public BillVote mapRow(ResultSet rs, int rowNum) throws SQLException {
        BillVote billVote = new BillVote();
        billVote.setBillId(rs.getString("bill_id"));
        billVote.setSponsorId(rs.getString("sponsor_id"));
        billVote.setResult(rs.getString("result"));
        billVote.setCongress(rs.getString("congress"));
        billVote.setIntroducedOn(rs.getString("introduced_on"));
        billVote.setBioguideId(rs.getString("bioguide_id"));
        billVote.setVote(rs.getString("vote"));
        return billVote;
    }
}
