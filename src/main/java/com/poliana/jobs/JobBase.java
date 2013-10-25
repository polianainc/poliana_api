package com.poliana.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.poliana.entities.repositories.EntitiesHiveRepo;
import com.poliana.contributions.services.ContributionService;
import com.poliana.codes.services.IndustryService;

@Component
public class JobBase {
    @Autowired
    protected JdbcTemplate hiveTemplate;
    @Autowired
    protected ContributionService contributionService;
    @Autowired
    protected IndustryService industryService;
    @Autowired
    protected EntitiesHiveRepo entitiesHiveRepo;
}
