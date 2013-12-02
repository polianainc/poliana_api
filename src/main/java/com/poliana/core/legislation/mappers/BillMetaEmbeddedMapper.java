package com.poliana.core.legislation.mappers;

import com.poliana.core.legislation.entities.deprecated.BillPojo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * @author David Gilmore
 * @date 10/20/13
 */
public class BillMetaEmbeddedMapper implements RowMapper<BillPojo> {
    public BillPojo mapRow(ResultSet rs, int rowNum) throws SQLException {
        BillPojo bill = new BillPojo();
        bill.setBillId(rs.getString("bill_id"));
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

        int timestamp = rs.getInt("introduced_at");
        bill.setIntroducedAt(timestamp);
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

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis((long) timestamp * 1000L);

        bill.setYear(cal.get(Calendar.YEAR));
        bill.setMonth(cal.get(Calendar.MONTH + 1));

        return bill;
    }
}

