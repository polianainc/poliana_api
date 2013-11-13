package com.poliana.entities.repositories;

import com.poliana.entities.entities.*;
import com.poliana.entities.mappers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EntitiesHadoopRepo {
    @Autowired
    protected JdbcTemplate hiveTemplate;
    @Autowired
    protected JdbcTemplate impalaTemplate;


    public List<String> bioguideIds() {
        return impalaTemplate.queryForList("SELECT distinct(bioguide_id) FROM " +
                "entities.legislators_flat_terms", String.class);
    }

    public List<String> senateBioguideIdsByYear(int year) {
        return impalaTemplate.queryForList("SELECT distinct(bioguide_id) FROM " +
                "entities.senate_terms WHERE " + year +
                " BETWEEN year(from_unixtime(term_start)) AND year(from_unixtime(term_start)) + 2" +
                " AND bioguide_id IS NOT NULL",
                String.class);
    }

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
