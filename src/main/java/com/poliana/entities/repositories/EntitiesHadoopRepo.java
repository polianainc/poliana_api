package com.poliana.entities.repositories;

import com.poliana.entities.entities.*;
import com.poliana.entities.mappers.*;
import com.poliana.entities.entities.Industry;
import com.poliana.entities.entities.Legislator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EntitiesHadoopRepo {
    @Autowired
    private JdbcTemplate hiveTemplate;
    @Autowired
    private JdbcTemplate impalaTemplate;


    public List<String> bioguideIds() {
        String query = "SELECT distinct(bioguide_id) FROM entities.legislators_flat_terms";
        return impalaTemplate.queryForList(query, String.class);
    }

    public List<String> senateBioguideIdsByYear(int year) {
        return impalaTemplate.queryForList("SELECT distinct(bioguide_id) FROM " +
                "entities.senate_terms WHERE " + year +
                " BETWEEN year(from_unixtime(term_start)) AND year(from_unixtime(term_start)) + 2" +
                " AND bioguide_id IS NOT NULL",
                String.class);
    }

    public List<Legislator> getAllLegistlators() {
        String query = "SELECT * FROM entities.legislators_flat_terms";
        return impalaTemplate.query(query, new LegislatorMapper());
    }

    public List<Legislator> getAllLegistlators(String condition) {
        String where = " WHERE " + condition;
        String query = "SELECT * FROM entities.legislators_flat_terms" + where;
        return impalaTemplate.query(query, new LegislatorMapper());
    }

    public List<Legislator> legislatorByLisId(String lisId) {
        String query = "SELECT * FROM entities.legislators_flat_terms " +
                "WHERE lis_id = \'" + lisId +"\'";

        return impalaTemplate.query(query, new LegislatorMapper());
    }

    public List<Industry> getIndustries() {
        return impalaTemplate.query("SELECT * FROM entities.industry_codes", new IndustryMapper());
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
