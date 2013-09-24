package com.poliana.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.poliana.politicitans.repositories.PoliticianHiveJdbcRepo;
import com.poliana.contributions.services.ContributionService;
import com.poliana.codes.services.IndustryService;

@Component
public class JobBase {
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected ContributionService contributionService;
    @Autowired
    protected IndustryService industryService;
    @Autowired
    protected PoliticianHiveJdbcRepo politicianHiveJdbcRepo;
}
