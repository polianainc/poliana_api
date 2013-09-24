package com.poliana.politicitans.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.poliana.politicitans.entities.Politician;
import com.poliana.politicitans.entities.PoliticianMapper;

import java.util.List;

@Repository
public class PoliticianHiveJdbcRepo {
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public List<Politician> getPoliticians() {
        return jdbcTemplate.query("SELECT * FROM politicians", new PoliticianMapper());
    }
}

