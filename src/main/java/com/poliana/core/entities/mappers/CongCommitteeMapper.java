package com.poliana.core.entities.mappers;

import com.poliana.core.entities.entities.CongCommittee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CongCommitteeMapper implements RowMapper<CongCommittee> {
    public CongCommittee mapRow(ResultSet rs, int rowNum) throws SQLException {
        CongCommittee committee = new CongCommittee();
        committee.setCode(rs.getString("code"));
        committee.setCommitteeName(rs.getString("cmte_name"));
        return committee;
    }
}