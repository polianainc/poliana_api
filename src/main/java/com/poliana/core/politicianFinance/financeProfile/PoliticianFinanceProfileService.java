package com.poliana.core.politicianFinance.financeProfile;

import com.poliana.core.ideology.IdeologyService;
import com.poliana.core.ideology.LegislatorIdeology;
import com.poliana.core.industryFinance.services.IndustryContributionService;
import com.poliana.core.legislators.Legislator;
import com.poliana.core.legislators.LegislatorService;
import com.poliana.core.pacFinance.PacContributionService;
import com.poliana.core.pacFinance.PacPoliticianContrTotals;
import com.poliana.core.politicianFinance.industries.IndustryPoliticianContributionTotals;
import com.poliana.core.politicianFinance.industries.PoliticianIndustryFinanceService;
import com.poliana.core.politicianFinance.industries.PoliticianIndustryMongoRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * The PoliticianFinanceProfile Service aggregates PAC contribution sums, individual contribution sums, and ideology
 * analysis for every congressional cycle a given politician has been in Federal office as a representative or senator.
 * @author David Gilmore
 * @date 12/26/13
 */
@Service
public class PoliticianFinanceProfileService {

    private PacContributionService pacContributionService;
    private IndustryContributionService industryContributionService;
    private IdeologyService ideologyService;
    private LegislatorService legislatorService;
    private PoliticianIndustryFinanceService politicianIndustryFinanceService;
    private PoliticianIndustryMongoRepo politicianIndustryMongoRepo;

    private static final Logger logger = Logger.getLogger(PoliticianFinanceProfileService.class);


    /**
     * A Politician profile is an overview of industry and PAC contributions. This method checks for the
     * existence of a cached analysis in MongoDB. If the profile does not exist in Mongo, the method
     * will call constructProfile which use Impala as its data source and has high latency.
     * @param bioguideId
     * @return
     */
    public PoliticianFinanceProfile getPoliticianProfile(String bioguideId) {

        PoliticianFinanceProfile profile = new PoliticianFinanceProfile();

        //This method calls MongoDB and looks for cached SessionTotals. If they exist, we'll use them and return.
        HashMap<Integer, SessionTotals> sessionTotalsMap = getSessionTotalsByCongressMongo(bioguideId);

        if (sessionTotalsMap != null && !sessionTotalsMap.isEmpty()) {

            List<SessionTotals> sessionTotals = new LinkedList<>();

            for (SessionTotals sessions: sessionTotalsMap.values())
                sessionTotals.add(sessions);

            Legislator legislator;

            try {
                legislator = legislatorService.getLegislatorTermsById(bioguideId).get(0);
                profile.setFirstName(legislator.getFirstName());
                profile.setLastName(legislator.getLastName());
            }
            catch (NullPointerException e) {
                logger.error(e.getMessage());
            }

            profile.setBioguideId(bioguideId);
            profile.setSessions(sessionTotals);

            return profile;
        }

        sessionTotalsMap = new HashMap<>(30);

        setPacTotals(bioguideId, sessionTotalsMap);
        setIndustryTotals(bioguideId, sessionTotalsMap);
        setIdeologyScores(bioguideId, sessionTotalsMap);

        politicianIndustryMongoRepo.saveSessionTotals(sessionTotalsMap.values());

        return profile;
    }

    /**
     * Use MongoDB to search for cached SessionTotals objects. If any are returned, transform them to a HashMap keyed by
     * congressional cycle
     * @param bioguide
     * @return
     * @see SessionTotals
     */
    public HashMap<Integer, SessionTotals> getSessionTotalsByCongressMongo(String bioguide) {

        List<SessionTotals> termTotalsList = politicianIndustryMongoRepo.getSessionTotals(bioguide);

        int numEl = termTotalsList.size();

        if (numEl > 0) {
            HashMap<Integer, SessionTotals> termTotalsHashMap = new HashMap<>(numEl);

            for (SessionTotals termTotals: termTotalsList) {

                //Make sure only SessionTotals calculated by congressional cycle are added to our map
                if (termTotals.getCongress() != 0)
                    termTotalsHashMap.put(termTotals.getCongress(), termTotals);
            }

            return termTotalsHashMap;
        }

        return null;
    }

    /**
     * Functional helper method that compartmentalizes the logic for updating SessionTotals with PAC contributions.
     * @param bioguideId
     * @param termTotalsMap
     */
    private void setPacTotals(String bioguideId, HashMap<Integer, SessionTotals> termTotalsMap) {

        HashMap<Integer, List<PacPoliticianContrTotals>> totalsHashMap = pacContributionService.getPacTotalsAllTime(bioguideId);

        //Get an iterator for the values in the hash map
        Iterator it = totalsHashMap.entrySet().iterator();
        Map.Entry pairs;

        //Iterate through all entry pairs in the map and update the SessionTotalsMap with the values.
        while (it.hasNext()) {
            pairs = (Map.Entry) it.next();

            if (termTotalsMap.containsKey(pairs.getKey()))
                termTotalsMap.get(pairs.getKey()).setTopPACContributions((List<PacPoliticianContrTotals>)pairs.getValue());
            else {
                //If the termTotalsMap doesn't have the current cycle, make a new object for it
                SessionTotals termTotals = new SessionTotals();
                termTotals.setTopPACContributions((List<PacPoliticianContrTotals>) pairs.getValue());
                termTotalsMap.put((Integer)pairs.getKey(), termTotals);
            }
        }

    }

    /**
     * Functional helper method that compartmentalizes the logic for updating SessionTotals with Industry contributions.
     * @param bioguideId
     * @param termTotalsMap
     */
    private void setIndustryTotals(String bioguideId, HashMap<Integer, SessionTotals> termTotalsMap) {

        HashMap<Integer, List<IndustryPoliticianContributionTotals>> totalsHashMap = politicianIndustryFinanceService.getIndustryTotalsAllTime(bioguideId);

        for (Integer cycle: totalsHashMap.keySet()) {
            if (termTotalsMap.containsKey(cycle)) {
                termTotalsMap.get(cycle).setTopIndustryContributions(totalsHashMap.get(cycle));
            }
            else {
                SessionTotals termTotals = new SessionTotals();
                termTotals.setCongress(cycle);
                termTotals.setTopIndustryContributions(totalsHashMap.get(cycle));
                termTotalsMap.put(cycle, termTotals);
            }
        }
    }

    /**
     * Functional helper method that compartmentalizes the logic for updating SessionTotals with ideology scores.
     * @param bioguideId
     * @param termTotalsMap
     */
    private void setIdeologyScores(String bioguideId, HashMap<Integer, SessionTotals> termTotalsMap) {

        //Get an iterator for the values in the hash map
        Iterator it = termTotalsMap.entrySet().iterator();
        Map.Entry pairs;

        while (it.hasNext()) {

            pairs = (Map.Entry)it.next();
            LegislatorIdeology ideology = ideologyService.getLegislatorIdeology(bioguideId, (Integer) pairs.getKey());

            if (ideology != null) {

                SessionTotals totals = ((SessionTotals) pairs.getValue());

                totals.setChamber(ideology.getChamber());
                totals.setIdeologyScore(ideology.getIdeology());
                totals.setReligion(ideology.getReligion());
            }
        }
    }

    @Autowired
    public void setPacContributionService(PacContributionService pacContributionService) {
        this.pacContributionService = pacContributionService;
    }

    @Autowired
    public void setIndustryContributionService(IndustryContributionService industryContributionService) {
        this.industryContributionService = industryContributionService;
    }

    @Autowired
    public void setIdeologyService(IdeologyService ideologyService) {
        this.ideologyService = ideologyService;
    }

    @Autowired
    public void setLegislatorService(LegislatorService legislatorService) {
        this.legislatorService = legislatorService;
    }

    @Autowired
    public void setPoliticianIndustryFinanceService(PoliticianIndustryFinanceService politicianIndustryFinanceService) {
        this.politicianIndustryFinanceService = politicianIndustryFinanceService;
    }

    @Autowired
    public void setPoliticianIndustryMongoRepo(PoliticianIndustryMongoRepo politicianIndustryMongoRepo) {
        this.politicianIndustryMongoRepo = politicianIndustryMongoRepo;
    }
}

