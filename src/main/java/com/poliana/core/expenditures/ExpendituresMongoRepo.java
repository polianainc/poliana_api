package com.poliana.core.expenditures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExpendituresMongoRepo {

    private JdbcTemplate hiveTemplate;

    private final String databasePrefix = "local.";
    private final String expendituresTable = databasePrefix + "expenditures";

    public List<Expenditure> expenditures() {
        return hiveTemplate.query("SELECT * FROM " + expendituresTable, new ExpenditureMapper());
    }

    @Autowired
    public void setHiveTemplate(JdbcTemplate hiveTemplate) {
        this.hiveTemplate = hiveTemplate;
    }
}
