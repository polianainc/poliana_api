package com.poliana.bills.mappers;

import com.poliana.bills.entities.Bill;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BillMapper implements RowMapper<Bill> {
    public Bill mapRow(ResultSet rs, int rowNum) throws SQLException {
        Bill bill = new Bill();
        bill.setBillId(rs.getString("bill_id"));
        bill.setVoteId(rs.getString("vote_id"));
        bill.setOfficialTitle(rs.getString("official_title"));
        bill.setPopularTitle(rs.getString("popular_title"));
        bill.setShortTitle(rs.getString("short_title"));
        bill.setSponsor(rs.getString("sponsor_name"));
        bill.setSponsorState(rs.getString("sponsor_state"));
        bill.setSponsorId(rs.getString("sponsor_id"));
        bill.setCosponsorIdsJson(rs.getString("cosponsor_ids"));
        bill.setTopSubject(rs.getString("top_subject"));
        bill.setSubjectsJson(rs.getString("subjects"));
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
