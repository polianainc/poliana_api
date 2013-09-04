package poliana.data.services.bills;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import poliana.data.models.bill.*;
import poliana.data.repositories.bills.BillHiveJdbcRepo;

import java.util.LinkedList;
import java.util.List;

@Service
public class ContributionInfluence {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BillHiveJdbcRepo billRepo;

    public List<Industry> getMainIndustries(String billId) {
        return getMainIndustries(billId, 0);
    }

    public List<Industry> getMainIndustries(String billId, int limit) {
        List<Industry> topContributors = new LinkedList<Industry>();

        List<ContributionTotals> mostYeaContributions = billRepo.descOnTotalYea(billId, limit);
        for(ContributionTotals contributionTotals : mostYeaContributions) {
            Industry industry = new Industry();
            industry.setIndustryId(contributionTotals.getIndustryId());
            industry.setMainTotal(contributionTotals.getSumYea());
            industry.setMainTouched(contributionTotals.getDistinctYea());
            industry.setMainChildren(yeaRecipients(billId, contributionTotals.getIndustryId()));
            industry.setOffTotal(contributionTotals.getSumNay());
            industry.setOffTouched(contributionTotals.getDistinctNay());
            industry.setOffChildren(nayRecipients(billId, contributionTotals.getIndustryId()));
            topContributors.add(industry);
        }
        return topContributors;
    }

    public List<Industry> getOffIndustries(String billId) {
        return getMainIndustries(billId, 0);
    }

    public List<Industry> getOffIndustries(String billId, int limit) {
        List<Industry> topContributors = new LinkedList<Industry>();

        List<ContributionTotals> mostNayContributions = billRepo.descOnTotalNay(billId, limit);
        for(ContributionTotals contributionTotals : mostNayContributions) {
            Industry industry = new Industry();
            industry.setIndustryId(contributionTotals.getIndustryId());
            industry.setMainTotal(contributionTotals.getSumYea());
            industry.setMainTouched(contributionTotals.getDistinctYea());
            industry.setMainChildren(yeaRecipients(billId, contributionTotals.getIndustryId()));
            industry.setOffTotal(contributionTotals.getSumNay());
            industry.setOffTouched(contributionTotals.getDistinctNay());
            industry.setOffChildren(nayRecipients(billId, contributionTotals.getIndustryId()));
            topContributors.add(industry);
        }
        return topContributors;
    }

    public List<Recipient> yeaRecipients(String billId, String industryId) {
        return billRepo.topRecipients(billId, industryId, "yeas");
    }

    public List<Recipient> nayRecipients(String billId, String industryId) {
        return billRepo.topRecipients(billId, industryId, "nays");
    }

    public int getMainTotal(String billId) {
        return billRepo.getSum(billId, "yea");
    }

    private int getMainTotal(List<Contributor> contributorTotals) {
        return 1;
    }

    public int getMainTouched(String billId) {
        return billRepo.distinctCount(billId, "yeas");
    }

    public List<Contributor> getOffContributors(String billId) {


        return null;
    }
    public int getOffTotal(String billId) {
        return billRepo.getSum(billId, "nay");
    }

    private int getOffTotal(List<Contributor> offChildren) {
        return 1;
    }

    public int getOffTouched(String billId) {
        return billRepo.distinctCount(billId, "nays");
    }
}