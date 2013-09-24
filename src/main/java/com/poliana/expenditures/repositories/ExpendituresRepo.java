package com.poliana.expenditures.repositories;

import com.poliana.expenditures.entities.Expenditure;
import com.poliana.expenditures.entities.ExpenditureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExpendituresRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String databasePrefix = "local.";
    private final String expendituresTable = databasePrefix + "expenditures";

    public List<Expenditure> expenditures() {
        return jdbcTemplate.query("SELECT * FROM " + expendituresTable, new ExpenditureMapper());
    }
}
