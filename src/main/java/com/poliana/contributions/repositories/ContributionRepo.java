package com.poliana.contributions.repositories;

import com.poliana.contributions.entities.mappers.*;
import com.poliana.contributions.models.Bill;
import com.poliana.contributions.models.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.poliana.contributions.entities.*;

import java.util.List;

@Repository
public class ContributionRepo {

    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected FileSystemResourceLoader resourceLoader;

    private final String database = "local";
    private final String industryTable = "individual_contr";
    private final String individualsTable = "individual_contr";
    private final String fecTable = "fec_contributions";
    private final String pacToCandidateTable = "pac_to_candidate_contr";
    private final String pacToPacTable = "pac_to_pac_contr";

    public ContributionRepo() {}

    public ContributionRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.resourceLoader = new FileSystemResourceLoader();
    }

    public List<Bill> industryContributions(String billId, String vote) {
        return industryContributions(billId, vote, 0);
    }

    public List<Bill> industryContributions(String billId, String vote, int limit) {
        String lim = "";

        if (limit != 0)
            lim = " LIMIT " + limit;

        List<Bill> table = jdbcTemplate.query("SELECT * FROM " +
                "industry_contr_" + billId + "_113_" + vote + "_6months" + lim, new IndustryContributionMapper());
        return table;
    }

    public List<Recipient> topIndustryRecipients(String billId, String vote, int limit) {
        String lim = "";

        if (limit != 0)
            lim = " LIMIT " + limit;

        List<Recipient> topRecipients = jdbcTemplate.query("" +
                "SELECT industry_id, bioguide_id, first_name, last_name, sum(amount) AS sum " +
                "FROM industry_contr_" + billId + "_113_" + vote + "_6months" +
                " GROUP BY industry_id, bioguide_id, first_name, last_name" + lim,
                new RecipientMapper());
        return topRecipients;
    }

    public List<Recipient> topIndustryRecipients(String billId, String vote) {
        return topIndustryRecipients(billId, vote, 0);
    }

    public int countDistinctIndustryContributions(String billId, String vote) {
        return jdbcTemplate.queryForInt("SELECT count(distinct(bioguide_id)) FROM " +
                "industry_contr_" + billId + "_113_" + vote + "_6months");
    }

    public int sumIndustryContributions(String billId, String vote) {
        return jdbcTemplate.queryForInt("SELECT sum(" + vote + "_sum)FROM " + billId + "_trends");
    }

    public List<IndustryContrTotals> industryContributionTotals(String billId) {
        List<IndustryContrTotals> table = jdbcTemplate.query("SELECT * FROM "
                + billId + "_trends", new IndustryContrTotalsMapper());
        return table;
    }

    public List<FecContribution> fecContributions() {
        return jdbcTemplate.query("SELECT * FROM " + fecTable, new FecContributionMapper());
    }

    public List<IndividualContribution> individualContributions() {
        return jdbcTemplate.query("SELECT * FROM " + individualsTable, new IndividualContrMapper());
    }

    public List<PacToCandidateContribution> pacToCandidateContributions() {
        return jdbcTemplate.query("SELECT * FROM " + pacToCandidateTable, new PacToCandidateContrMapper());
    }

    public List<PacToPacContribution> pacToPacContributions() {
        return jdbcTemplate.query("SELECT * FROM " + pacToPacTable, new PacToPacContrMapper());
    }


}