package com.poliana.legislation.mappers;

import com.poliana.legislation.entities.deprecated.BillPojo;
import com.poliana.legislation.entities.deprecated.BillVotes;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author David Gilmore
 * @date 10/20/13
 */
public class BillWithVotesMapper implements RowMapper<BillPojo> {
    public BillPojo mapRow(ResultSet rs, int rowNum) throws SQLException {
        BillPojo bill = new BillPojo();
        String billId = rs.getString("bill_id");
        bill.setBillId(billId);

        BillVotes billVotes = new BillVotes();
        billVotes.setBillId(billId);
        billVotes.setVoteId(rs.getString("vote_id"));
        billVotes.setChamber(rs.getString("chamber"));
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


        bill.setVoteId(rs.getString("vote_id"));
        bill.setOfficialTitle(rs.getString("official_title"));
        bill.setPopularTitle(rs.getString("popular_title"));
        bill.setShortTitle(rs.getString("short_title"));
        bill.setSponsor(rs.getString("sponsor_name"));
        bill.setSponsorState(rs.getString("sponsor_state"));
        bill.setSponsorId(rs.getString("sponsor_id"));
        bill.setCosponsorIdsDelim(rs.getString("cosponsor_ids"));
        bill.setTopSubject(rs.getString("top_subject"));
        bill.setSubjectsDelim(rs.getString("subjects"));
        bill.setSummary(rs.getString("summary"));
        bill.setIntroducedAt(rs.getInt("introduced_at"));
        bill.setHousePassageResult(rs.getString("house_passage_result"));
        bill.setHousePassageResultAt(rs.getInt("house_passage_result_at"));
        bill.setSenateClotureResult(rs.getString("senate_cloture_result"));
        bill.setSenateClotureResultAt(rs.getInt("senate_cloture_result_at"));
        bill.setSenatePassageResult(rs.getString("senate_passage_result"));
        bill.setSenatePassageResultAt(rs.getInt("senate_passage_result_at"));
        bill.setAwaitingSignature(rs.getBoolean("awaiting_signature"));
        bill.setEnacted(rs.getBoolean("enacted"));
        bill.setVetoed(rs.getBoolean("vetoed"));
        bill.setEnactedAt(rs.getInt("enacted_at"));
        bill.setCongress(rs.getInt("congress"));
        bill.setBillType(rs.getString("bill_type"));
        return bill;
    }
}
