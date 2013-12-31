package com.poliana.core.politicianProfile;

import com.poliana.core.ideology.IdeologyService;
import com.poliana.core.industryFinance.entities.IndToPolContrTotals;
import com.poliana.core.industryFinance.repositories.IndustryContributionHadoopRepo;
import com.poliana.core.pacFinance.PacContributionRepo;
import com.poliana.core.pacFinance.PacPoliticianContrTotals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


/**
 * @author David Gilmore
 * @date 12/26/13
 */
@Service
public class PoliticianProfileService {

    @Autowired
    private PacContributionRepo pacContributionRepo;

    @Autowired
    private IndustryContributionHadoopRepo industryContributionHadoopRepo;

    @Autowired
    private IdeologyService ideologyService;


    /**
     * A Politician profile is an overview of contributions and ideology. This method checks for the
     * existence of a cached analysis in MongoDB. If the profile does not exist in Mongo, the method
     * will call constructProfile which use Impala as its data source and has high latency.
     *
     * @param bioguideId
     * @return
     */
    public PoliticianProfile getPoliticianProfile(String bioguideId) {

        PoliticianProfile profile = new PoliticianProfile();

        HashMap<Integer, TermTotals> termTotalsMap = new HashMap<>(30);
        setPacTotals(bioguideId, termTotalsMap);
        setIndustryTotals(bioguideId, termTotalsMap);

        return profile;
    }

    protected void setPacTotals(String bioguideId, HashMap<Integer, TermTotals> termTotalsMap) {

        HashMap<Integer, List<PacPoliticianContrTotals>> totalsHashMap =
                pacContributionRepo.allLegislatorReceivedPacTotals(bioguideId);

        for (Integer cycle: totalsHashMap.keySet()) {
            if (termTotalsMap.containsKey(cycle)) {
                termTotalsMap.get(cycle).setTopPACContributions(totalsHashMap.get(cycle));
            }
            else {
                TermTotals termTotals = new TermTotals();
                termTotals.setCongress(cycle);
                termTotals.setTopPACContributions(totalsHashMap.get(cycle));
                termTotalsMap.put(cycle, termTotals);
            }
        }

    }

    protected void setIndustryTotals (
            String bioguideId, HashMap<Integer, TermTotals> termTotalsMap) {

        HashMap<Integer, List<IndToPolContrTotals>> totalsHashMap =
                industryContributionHadoopRepo.allIndustryContributionsPerCongress(bioguideId);

        for (Integer cycle: totalsHashMap.keySet()) {
            if (termTotalsMap.containsKey(cycle)) {
                termTotalsMap.get(cycle).setTopIndustryContributions(totalsHashMap.get(cycle));
            }
            else {
                TermTotals termTotals = new TermTotals();
                termTotals.setCongress(cycle);
                termTotals.setTopIndustryContributions(totalsHashMap.get(cycle));
                termTotalsMap.put(cycle, termTotals);
            }
        }

    }
}
