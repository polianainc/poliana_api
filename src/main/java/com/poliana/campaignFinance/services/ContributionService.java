package com.poliana.campaignFinance.services;

import com.poliana.campaignFinance.entities.IndustryContrTotals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.poliana.campaignFinance.models.IndustryContributor;
import com.poliana.campaignFinance.models.Recipient;
import com.poliana.campaignFinance.repositories.ContributionRepo;
import com.poliana.entities.services.IndustryService;
import com.poliana.entities.services.PoliticianService;

import java.util.*;

@Service
public class ContributionService {
    @Autowired
    private ContributionRepo contributionRepo;
    @Autowired
    private IndustryService industryService;
    @Autowired
    private PoliticianService politicanService;

    private Map<String, String> industryNameMap;
    private Map<String, String> catNameMap;
    private Map<String, String> politicianMap;
    private List<Recipient> yeaRecipients;
    private List<Recipient> nayRecipients;
    private String currYeaBill = "";
    private String currNayBill = "";

    public int getMainTotal(String billId) {
        return contributionRepo.sumIndustryContributions(billId, "yea");
    }

    public int getMainTouched(String billId) {
        return contributionRepo.countDistinctIndustryContributions(billId, "yeas");
    }

    public int getOffTotal(String billId) {
        return contributionRepo.sumIndustryContributions(billId, "nay");
    }

    public int getOffTouched(String billId) {
        return contributionRepo.countDistinctIndustryContributions(billId, "nays");
    }

    public void setIndustryNameMap() {
        industryNameMap = industryService.industryNameMap();
    }

    public void setCatNameMap() {
        this.catNameMap = industryService.catNameMap();
    }

    public void setPoliticianMap() {
        politicianMap = politicanService.politicianMap();
    }

    public List<IndustryContributor> getYeasByTotalCount(String billId, int limit) {
        List<IndustryContrTotals> industries = contributionRepo.industryContributionTotals(billId);
        Collections.sort(industries, new Comparator<IndustryContrTotals>() {
            @Override
            public int compare(IndustryContrTotals industry1, IndustryContrTotals industry2) {
                return industry2.getTotalYea() - industry1.getTotalYea();
            }
        });
        if(limit > 0)
            return populateIndustryContributors(limitIndustries(industries, limit), billId);
        else
            return populateIndustryContributors(industries, billId);
    }

    public List<IndustryContributor> getYeasByTotalCount(String billId) {
        return getYeasByTotalCount(billId, 0);
    }

    public List<IndustryContributor> getNaysByTotalCount(String billId, int limit) {
        List<IndustryContrTotals> industries = contributionRepo.industryContributionTotals(billId);
        Collections.sort(industries, new Comparator<IndustryContrTotals>() {
            @Override
            public int compare(IndustryContrTotals industry1, IndustryContrTotals industry2) {
                return industry2.getTotalNay()- industry1.getTotalNay();
            }
        });
        if(limit > 0)
            return populateIndustryContributors(limitIndustries(industries, limit), billId);
        else
            return populateIndustryContributors(industries, billId);
    }

    public List<IndustryContributor> getNaysByTotalCount(String billId) {
        return getYeasByTotalCount(billId, 0);
    }

    public List<IndustryContributor> getYeasByTotalDiff(String billId, int limit) {
        List<IndustryContrTotals> industries = contributionRepo.industryContributionTotals(billId);
        Collections.sort(industries, new Comparator<IndustryContrTotals>() {
            @Override
            public int compare(IndustryContrTotals industry1, IndustryContrTotals industry2) {
                return industry2.getTotalDiff()- industry1.getTotalDiff();
            }
        });
        if(limit > 0)
            return populateIndustryContributors(limitIndustries(industries, limit), billId);
        else
            return populateIndustryContributors(industries, billId);
    }

    public List<IndustryContributor> getYeasByTotalDiff(String billId) {
        return getYeasByTotalDiff(billId, 0);
    }

    public List<IndustryContributor> getNaysByTotalDiff(String billId, int limit) {
        List<IndustryContrTotals> industries = contributionRepo.industryContributionTotals(billId);
        Collections.sort(industries, new Comparator<IndustryContrTotals>() {
            @Override
            public int compare(IndustryContrTotals industry1, IndustryContrTotals industry2) {
                return (-1 * industry2.getTotalDiff() + industry1.getTotalDiff());
            }
        });
        if(limit > 0)
            return populateIndustryContributors(limitIndustries(industries, limit), billId);
        else
            return populateIndustryContributors(industries, billId);
    }

    public List<IndustryContributor> getNaysByTotalDiff(String billId) {
        return getNaysByTotalDiff(billId, 0);
    }

    public List<IndustryContributor> getYeasByDistinctCount(String billId, int limit) {
        List<IndustryContrTotals> industries = contributionRepo.industryContributionTotals(billId);
        Collections.sort(industries, new Comparator<IndustryContrTotals>() {
            @Override
            public int compare(IndustryContrTotals industry1, IndustryContrTotals industry2) {
                return industry2.getDistinctYea() - industry1.getDistinctYea();
            }
        });
        if(limit > 0)
            return populateIndustryContributors(limitIndustries(industries, limit), billId);
        else
            return populateIndustryContributors(industries, billId);
    }

    public List<IndustryContributor> getYeasByDistinctCount(String billId) {
        return getYeasByDistinctCount(billId, 0);
    }

    public List<IndustryContributor> getNaysByDistinctCount(String billId, int limit) {
        List<IndustryContrTotals> industries = contributionRepo.industryContributionTotals(billId);
        Collections.sort(industries, new Comparator<IndustryContrTotals>() {
            @Override
            public int compare(IndustryContrTotals industry1, IndustryContrTotals industry2) {
                return industry2.getDistinctNay() - industry1.getDistinctNay();
            }
        });
        if(limit > 0)
            return populateIndustryContributors(limitIndustries(industries, limit), billId);
        else
            return populateIndustryContributors(industries, billId);
    }

    public List<IndustryContributor> getNaysByDistinctCount(String billId) {
        return getNaysByDistinctCount(billId, 0);
    }

    public List<IndustryContributor> getYeasByDistinctDiff(String billId, int limit) {
        List<IndustryContrTotals> industries = contributionRepo.industryContributionTotals(billId);
        Collections.sort(industries, new Comparator<IndustryContrTotals>() {
            @Override
            public int compare(IndustryContrTotals industry1, IndustryContrTotals industry2) {
                return industry2.getDistinctDiff() - industry1.getDistinctDiff();
            }
        });
        if(limit > 0)
            return populateIndustryContributors(limitIndustries(industries, limit), billId);
        else
            return populateIndustryContributors(industries, billId);
    }

    public List<IndustryContributor> getYeasByDistinctDiff(String billId) {
        return getYeasByDistinctDiff(billId, 0);
    }

    public List<IndustryContributor> getNaysByDistinctDiff(String billId, int limit) {
        List<IndustryContrTotals> industries = contributionRepo.industryContributionTotals(billId);
        Collections.sort(industries, new Comparator<IndustryContrTotals>() {
            @Override
            public int compare(IndustryContrTotals industry1, IndustryContrTotals industry2) {
                return (-1 * industry2.getDistinctDiff() + industry1.getDistinctDiff());
            }
        });
        if(limit > 0)
            return populateIndustryContributors(limitIndustries(industries, limit), billId);
        else
            return populateIndustryContributors(industries, billId);
    }

    public List<IndustryContributor> getNaysByDistinctDiff(String billId) {
        return getNaysByDistinctDiff(billId, 0);
    }

    public List<IndustryContributor> getYeasByTotalSum(String billId, int limit) {
        List<IndustryContrTotals> industries = contributionRepo.industryContributionTotals(billId);
        Collections.sort(industries, new Comparator<IndustryContrTotals>() {
            @Override
            public int compare(IndustryContrTotals industry1, IndustryContrTotals industry2) {
                return industry2.getSumYea() - industry1.getSumYea();
            }
        });
        if(limit > 0)
            return populateIndustryContributors(limitIndustries(industries, limit), billId);
        else
            return populateIndustryContributors(industries, billId);
    }

    public List<IndustryContributor> getYeasByTotalSum(String billId) {
        return getYeasByTotalSum(billId, 0);
    }

    public List<IndustryContributor> getNaysByTotalSum(String billId, int limit) {
        List<IndustryContrTotals> industries = contributionRepo.industryContributionTotals(billId);
        Collections.sort(industries, new Comparator<IndustryContrTotals>() {
            @Override
            public int compare(IndustryContrTotals industry1, IndustryContrTotals industry2) {
                return industry2.getSumNay() - industry1.getSumNay();
            }
        });
        if(limit > 0)
            return populateIndustryContributors(limitIndustries(industries, limit), billId);
        else
            return populateIndustryContributors(industries, billId);
    }

    public List<IndustryContributor> getNaysByTotalSum(String billId) {
        return getNaysByTotalSum(billId, 0);
    }

    public List<IndustryContributor> getYeasByInfluenceMetric(String billId, int limit) {
        List<IndustryContrTotals> industries = contributionRepo.industryContributionTotals(billId);
        final int votingYea = contributionRepo.countDistinctIndustryContributions(billId, "yeas");
        final int votingNay = contributionRepo.countDistinctIndustryContributions(billId, "nays");
        Collections.sort(industries, new Comparator<IndustryContrTotals>() {
            @Override
            public int compare(IndustryContrTotals industry1, IndustryContrTotals industry2) {
                double influence1 = influenceMetric(votingYea, votingNay, industry1);
                double influence2 = influenceMetric(votingYea, votingNay, industry2);
                if(influence2 - influence1 > 0)
                    return -1;
                else if(influence2 - influence1 < 0)
                    return 1;
                return 0;
            }
        });
        if(limit > 0)
            return populateIndustryContributors(limitIndustries(industries, limit), billId);
        else
            return populateIndustryContributors(industries, billId);
    }

    public List<IndustryContributor> getNaysByInfluenceMetric(String billId, int limit) {
        List<IndustryContrTotals> industries = contributionRepo.industryContributionTotals(billId);
        final int votingYea = contributionRepo.countDistinctIndustryContributions(billId, "yeas");
        final int votingNay = contributionRepo.countDistinctIndustryContributions(billId, "nays");
        Collections.sort(industries, new Comparator<IndustryContrTotals>() {
            @Override
            public int compare(IndustryContrTotals industry1, IndustryContrTotals industry2) {
                double influence1 = influenceMetric(votingYea, votingNay, industry1);
                double influence2 = influenceMetric(votingYea, votingNay, industry2);
                if(influence1 - influence2 > 0)
                    return 1;
                else if(influence1 - influence2 < 0)
                    return -1;
                return 0;
            }
        });
        if(limit > 0)
            return populateIndustryContributors(limitIndustries(industries, limit), billId);
        else
            return populateIndustryContributors(industries, billId);
    }

    public List<IndustryContributor> populateIndustryContributors(List<IndustryContrTotals> industries, String billId) {

        List<IndustryContributor> topContributors = new LinkedList<IndustryContributor>();
        String industryId;
        for(IndustryContrTotals industryContrTotals : industries) {

            industryId = industryContrTotals.getIndustryId();

            IndustryContributor industryContributor = new IndustryContributor();
            industryContributor.setIndustryId(industryId);
            industryContributor.setName(industryNameMap.get(industryId));
            industryContributor.setCatName(catNameMap.get(industryId));
            industryContributor.setMainTotal(industryContrTotals.getSumYea());
            industryContributor.setMainTouched(industryContrTotals.getDistinctYea());
            industryContributor.setMainChildren(yeaRecipients(billId, industryId, 5));
            industryContributor.setOffTotal(industryContrTotals.getSumNay());
            industryContributor.setOffTouched(industryContrTotals.getDistinctNay());
            industryContributor.setOffChildren(nayRecipients(billId, industryId, 5));
            topContributors.add(industryContributor);
        }
        return topContributors;
    }

    public List<Recipient> yeaRecipients(String billId, String industryId, int limit) {
        List<Recipient> tmpRecipients;
        if (currYeaBill == billId)
            tmpRecipients = this.yeaRecipients;
        else {
            currYeaBill = billId;
            tmpRecipients = this.yeaRecipients = contributionRepo.topIndustryRecipients(billId, "yeas");
        }
        return recipientsHelper(tmpRecipients, industryId, limit);
    }

    public List<Recipient> yeaRecipients(String billId, String industryId) {
        return yeaRecipients(billId, industryId, 0);
    }

    public List<Recipient> nayRecipients(String billId, String industryId, int limit) {
        List<Recipient> tmpRecipients;
        if (currNayBill == billId)
            tmpRecipients = this.nayRecipients;
        else {
            currNayBill = billId;
            tmpRecipients = this.nayRecipients = contributionRepo.topIndustryRecipients(billId, "nays");
        }
        return recipientsHelper(tmpRecipients, industryId, limit);
    }

    private List<Recipient> recipientsHelper(List<Recipient> tmpRecipients, String industryId, int limit) {
        tmpRecipients = filterByIndustry(tmpRecipients, industryId);
        tmpRecipients = sortedRecipients(tmpRecipients);
        if(limit > 0 && tmpRecipients.size() > limit)
            tmpRecipients = tmpRecipients.subList(0, limit);

        tmpRecipients = limitRecipients(tmpRecipients, limit);
        tmpRecipients = setParty(tmpRecipients);

        return tmpRecipients;
    }

    private List<Recipient> limitRecipients(List<Recipient> recipients, int limit) {
        List<Recipient> tmpRecipients = new ArrayList<>(limit);
        for(Recipient recipient : recipients) {
            if(limit <= 0)
                return tmpRecipients;
            if(recipient.getName() != null) {
                tmpRecipients.add(recipient);
                limit--;
            }
        }
        return tmpRecipients;
    }

    private List<IndustryContrTotals> limitIndustries(List<IndustryContrTotals> industries, int limit) {
        List<IndustryContrTotals> tmpIndustries = new ArrayList<>(limit);
        for(IndustryContrTotals industry : industries) {
            if(limit <= 0)
                return tmpIndustries;
            if(industryNameMap.get(industry.getIndustryId()) != null) {
                tmpIndustries.add(industry);
                limit--;
            }
        }
        return tmpIndustries;
    }

    private List<Recipient> filterByIndustry(List<Recipient> recipients, String industryId) {
        List<Recipient> industries = new ArrayList<>();
        for(Recipient recipient : recipients) {
            if(recipient.getIndustryId().equals(industryId))
                industries.add(recipient);
        }
        return industries;
    }

    private List<Recipient> setParty(List<Recipient> recipients) {
        for(Recipient recipient : recipients)
            recipient.setParty(politicianMap.get(recipient.getBioguideId()));
        return recipients;
    }

    private List<Recipient> sortedRecipients(List<Recipient> industryList) {
        List<Recipient> recipients = industryList;
        Collections.sort(recipients, new Comparator<Recipient>() {
            @Override
            public int compare(Recipient recipient1, Recipient recipient2) {
                if (recipient2.getContribution() > recipient1.getContribution())
                    return 1;
                if (recipient2.getContribution() < recipient1.getContribution())
                    return -1;
                return 0;
            }
        });
        return recipients;
    }

    public List<Recipient> nayRecipients(String billId, String industryId) {
        return nayRecipients(billId, industryId, 0);
    }

    private double influenceMetric(int votingYea, int votingNay, IndustryContrTotals contribution) {
        double percentTouchedYea = (double) contribution.getDistinctYea() / votingYea;
        double percentTouchedNay = (double) contribution.getDistinctYea() / votingNay;
        double biasMetric = percentTouchedYea - percentTouchedNay;
        double distinctYea = (double) contribution.getDistinctYea();
        double distinctNay = (double) contribution.getDistinctNay();
        double sumYea = contribution.getSumYea();
        double sumNay = contribution.getSumNay();
        double totalYea = (double) contribution.getTotalYea();
        double totalNay = (double) contribution.getTotalNay();
        double avgGivenYeas = sumYea / votingYea;
        double avgGivenNays = sumNay / votingNay;

        return biasMetric;
    }
}