package com.poliana.codes.repositories;

import com.poliana.codes.entities.*;
import com.poliana.codes.entities.mappers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CodesRepo {
    @Autowired
    protected JdbcTemplate hiveTemplate;

    public List<Industry> getIndustries() {
        return hiveTemplate.query("SELECT * FROM industry_codes", new IndustryMapper());
    }

    public List<CongCommittee> getCongCommitties() {
        return hiveTemplate.query("SELECT * FROM cong_cmte_codes", new CongCommitteeMapper());
    }

    public List<CandidateIds> getCandidateIds() {
        return hiveTemplate.query("SELECT * FROM candidate_ids", new CandidateIdsMapper());
    }

    public List<CandidateContributor> getCandidateContributors() {
        return hiveTemplate.query("SELECT * FROM candidate_contr", new CandidateContributorMapper());
    }

    public List<CommitteeContributor> getCommitteeContributors() {
        return hiveTemplate.query("SELECT * FROM committee_contr", new CommitteeContributorMapper());
    }
}
