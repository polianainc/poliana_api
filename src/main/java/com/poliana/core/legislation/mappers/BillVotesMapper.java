package com.poliana.core.legislation.mappers;

import com.poliana.core.legislation.entities.deprecated.BillVotes;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BillVotesMapper implements RowMapper<BillVotes> {
    public BillVotes mapRow(ResultSet rs, int rowNum) throws SQLException {
        BillVotes billVotes = new BillVotes();
        billVotes.setVoteId(rs.getString("vote_id"));
        billVotes.setYeaTotal(rs.getInt("yea_total"));
        billVotes.setNayTotal(rs.getInt("nay_total"));
        billVotes.setNotVotingTotal(rs.getInt("not_voting_total"));
        billVotes.setPresentTotal(rs.getInt("not_present_total"));
        billVotes.setYeas(rs.getString("yea_votes"));
        billVotes.setNays(rs.getString("nay_votes"));
        billVotes.setNotVoting(rs.getString("not_voting"));
        billVotes.setPresent(rs.getString("not_present"));
        billVotes.setYear(rs.getInt("year"));
        billVotes.setMonth(rs.getInt("month"));
        return billVotes;
    }
}
