package com.poliana.bills.repositories;

import com.poliana.bills.entities.BillAction;
import com.poliana.bills.entities.mappers.BillActionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class BillRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<BillAction> billActions(String billId) {
        return jdbcTemplate.query("SELECT * " +
                "FROM bills_prod.bills_actions WHERE bill_id = \'" + billId + "\'", new BillActionMapper());
    }
}
