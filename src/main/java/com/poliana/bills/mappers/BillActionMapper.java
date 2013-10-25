package com.poliana.bills.mappers;

import com.poliana.bills.entities.BillAction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BillActionMapper implements RowMapper<BillAction> {
    public BillAction mapRow(ResultSet rs, int rowNum) throws SQLException {
        BillAction action = new BillAction();
        action.setBillId(rs.getString("bill_id"));
        action.setActedAt(rs.getString("acted_at"));
        action.setCommittee(rs.getString("committee"));
        action.setHow(rs.getString("how"));
        action.setRoll(rs.getString("roll"));
        action.setStatus(rs.getString("status"));
        action.setText(rs.getString("text"));
        action.setType(rs.getString("type"));
        action.setVoteType(rs.getString("vote_type"));
        action.setLocation(rs.getString("location"));
        action.setCongress(rs.getString("congress"));
        action.setBillType(rs.getString("bill_type"));
        return action;
    }
}