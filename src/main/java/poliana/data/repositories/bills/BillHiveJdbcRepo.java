package poliana.data.repositories.bills;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import poliana.data.models.bill.*;

import java.util.List;

@Repository
public class BillHiveJdbcRepo {

    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected FileSystemResourceLoader resourceLoader;

    public BillHiveJdbcRepo() {}

    public BillHiveJdbcRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.resourceLoader = new FileSystemResourceLoader();
    }

    public List<ContributionTotals> contributionTotals(String billId) {
        List<ContributionTotals> table = jdbcTemplate.query("SELECT * FROM "
                + billId + "_trends", new ContributionTotalsMapper());
        return table;
    }

    public List<Contribution> contributions(String billId, String vote) {
        return contributions(billId, vote, 0);
    }

    public List<Contribution> contributions(String billId, String vote, int limit) {
        String lim = "";

        if (limit != 0)
            lim = " LIMIT " + limit;

        List<Contribution> table = jdbcTemplate.query("SELECT * FROM " +
                "industry_contr_" + billId + "_113_" + vote + "_6months" + lim, new ContributionsMapper());
        return table;
    }

    public List<Recipient> topRecipients(String billId, String industryId, String vote) {
        List<Recipient> topRecipients = jdbcTemplate.query("" +
                "SELECT industry_id, bioguide_id, first_name, last_name, sum(amount) AS sum " +
                "FROM industry_contr_" + billId + "_113_" + vote + "_6months WHERE industry_id = \'" + industryId + "\' " +
                "GROUP BY industry_id, bioguide_id, first_name, last_name ORDER BY sum DESC",
                new RecipientMapper());
        return topRecipients;
    }

//    SELECT industry_id, bioguide_id, first_name, last_name, sum(amount) as sum
//    FROM industry_contr_hr2397_113_nays_6months WHERE industry_id = 'X1200' GROUP BY industry_id, bioguide_id, first_name, last_name ORDER BY sum DESC;

    public int distinctCount(String billId, String vote) {
        return jdbcTemplate.queryForInt("SELECT count(distinct(bioguide_id)) FROM " +
                "industry_contr_" + billId + "_113_" + vote + "_6months");
    }

    public int getSum(String billId, String vote) {
        return jdbcTemplate.queryForInt("SELECT sum(" + vote + "_sum)FROM " + billId + "_trends");
    }

    public List<ContributionTotals> descOnTotalYea(String billId) {
        return descOnTotalYea(billId, 0);
    }

    public List<ContributionTotals> descOnTotalYea(String billId, int limit) {
        String lim = "";

        if (limit != 0)
            lim = " LIMIT " + limit;

        List<ContributionTotals> table = jdbcTemplate.query("SELECT * FROM " +
                billId + "_trends ORDER BY yea_total_count DESC" + lim, new ContributionTotalsMapper());
        return table;
    }

    public List<ContributionTotals> descOnTotalNay(String billId) {
        return descOnTotalYea(billId, 0);
    }

    public List<ContributionTotals> descOnTotalNay(String billId, int limit) {
        String lim = "";

        if (limit != 0)
            lim = " LIMIT " + limit;

        List<ContributionTotals> table = jdbcTemplate.query("SELECT * FROM " +
                billId + "_trends ORDER BY nay_total_count DESC" + lim, new ContributionTotalsMapper());
        return table;
    }

}
//TODO develop scalable, abstract naming convention for schema