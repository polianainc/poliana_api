package com.poliana.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.poliana.entities.repositories.EntitiesHadoopRepo;
import com.poliana.campaignFinance.services.DEMOContributionService;
import com.poliana.entities.services.IndustryService;

@Component
public class JobBase {
    @Autowired
    protected JdbcTemplate hiveTemplate;
    @Autowired
    protected DEMOContributionService DEMOContributionService;
    @Autowired
    protected IndustryService industryService;
    @Autowired
    protected EntitiesHadoopRepo entitiesHiveRepo;
}
