package com.poliana.entities.repositories;

import com.poliana.entities.mappers.LegislatorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.poliana.entities.entities.Legislator;

import java.util.List;

@Repository
public class EntitiesHiveRepo {
    @Autowired
    protected JdbcTemplate hiveTemplate;

    public List<Legislator> getAllLegislators() {
        return hiveTemplate.query("SELECT * FROM entities.legislators", new LegislatorMapper());
    }

    public List<Legislator> getAllLegislators(int limit) {
        if(limit < 0)
            limit *= -1;
        return hiveTemplate.query("SELECT * FROM entities.legislators LIMIT " + limit, new LegislatorMapper());
    }
}

