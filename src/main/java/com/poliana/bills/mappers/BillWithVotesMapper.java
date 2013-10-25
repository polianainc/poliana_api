package com.poliana.bills.mappers;

import com.poliana.bills.entities.Bill;
import com.poliana.bills.entities.BillVotes;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author David Gilmore
 * @date 10/20/13
 */
public class BillWithVotesMapper implements RowMapper<Bill> {
    public Bill mapRow(ResultSet rs, int rowNum) throws SQLException {
        Bill bill = new Bill();
        String billId = rs.getString("bill_id");
        bill.setBillId(billId);

        BillVotes billVotes = new BillVotes();
        billVotes.setBillId(billId);
        billVotes.setVoteId(rs.getString("vote_id"));
        billVotes.setYeaTotal(rs.getInt("yea_total"));
        billVotes.setNayTotal(rs.getInt("nay_total"));
        billVotes.setNotVotingTotal(rs.getInt("not_voting_total"));
        billVotes.setPresentTotal(rs.getInt("present_total"));
        billVotes.setYeas(rs.getString("yea_votes"));
        billVotes.setNays(rs.getString("nay_votes"));
        billVotes.setNotVoting(rs.getString("not_voting"));
        billVotes.setPresent(rs.getString("present"));
        billVotes.setYear(rs.getInt("year"));
        billVotes.setMonth(rs.getInt("month"));

        bill.setVotes(billVotes);
        bill.setVoteId(rs.getString("vote_id"));
        bill.setOfficialTitle(rs.getString("official_title"));
        bill.setPopularTitle(rs.getString("popular_title"));
        bill.setShortTitle(rs.getString("short_title"));
        bill.setSponsor(rs.getString("sponsor_name"));
        bill.setSponsorState(rs.getString("sponsor_state"));
        bill.setSponsorId(rs.getString("sponsor_id"));
        bill.setCosponsorIdsPipeDelim(rs.getString("cosponsor_ids"));
        bill.setTopSubject(rs.getString("top_subject"));
        bill.setSubjectsPipeDelim(rs.getString("subjects"));
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
