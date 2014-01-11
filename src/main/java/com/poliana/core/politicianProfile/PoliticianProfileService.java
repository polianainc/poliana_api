package com.poliana.core.politicianProfile;

import com.poliana.core.ideology.IdeologyService;
import com.poliana.core.ideology.LegislatorIdeology;
import com.poliana.core.industryFinance.IndustryContributionService;
import com.poliana.core.industryFinance.entities.IndustryPoliticianContributions;
import com.poliana.core.legislators.Legislator;
import com.poliana.core.legislators.LegislatorService;
import com.poliana.core.pacFinance.PacContributionService;
import com.poliana.core.pacFinance.PacPoliticianContrTotals;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * The PoliticianProfile Service aggregates PAC contribution sums, individual contribution sums, and getIdeologyMatrix
 * analysis for every congressional cycle a given politician has been in Federal office as a representative or senator.
 * @author David Gilmore
 * @date 12/26/13
 */
@Service
public class PoliticianProfileService {

    private PacContributionService pacContributionService;
    private IndustryContributionService industryContributionService;
    private IdeologyService ideologyService;
    private LegislatorService legislatorService;
    private PoliticianProfileRepo politicianProfileRepo;

    private static final Logger logger = Logger.getLogger(PoliticianProfileService.class);


    /**
     * A Politician profile is an overview of contributions and getAllPacContributions. This method checks for the
     * existence of a cached analysis in MongoDB. If the profile does not exist in Mongo, the method
     * will call constructProfile which use Impala as its data source and has high latency.
     * @param bioguideId
     * @return
     */
    public PoliticianProfile getPoliticianProfile(String bioguideId) {

        PoliticianProfile profile = new PoliticianProfile();

        //This method calls MongoDB and looks for cached TermTotals. If they exist, we'll use them and return.
        HashMap<Integer, TermTotals> termTotalsMap = getTermTotalsByCongressMongo(bioguideId);

        if (termTotalsMap != null && !termTotalsMap.isEmpty()) {

            List<String> termTotalsRefs = new LinkedList<>();

            for (TermTotals termTotals: termTotalsMap.values())
                termTotalsRefs.add(termTotals.getId());

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
            profile.setSessions(termTotalsRefs);

            return profile;
        }

        termTotalsMap = new HashMap<>(30);

        setPacTotals(bioguideId, termTotalsMap);
        setIndustryTotals(bioguideId, termTotalsMap);
        setIdeologyScores(bioguideId, termTotalsMap);

        politicianProfileRepo.saveTermTotals(termTotalsMap.values());

        return profile;
    }

    /**
     * Use MongoDB to search for cached TermTotals objects. If any are returned, transform them to a HashMap keyed by
     * congressional cycle
     * @param bioguide
     * @return
     * @see TermTotals
     */
    public HashMap<Integer, TermTotals> getTermTotalsByCongressMongo(String bioguide) {

        List<TermTotals> termTotalsList = politicianProfileRepo.getTermTotals(bioguide);

        int numEl = termTotalsList.size();

        if (numEl > 0) {
            HashMap<Integer, TermTotals> termTotalsHashMap = new HashMap<>(numEl);

            for (TermTotals termTotals: termTotalsList) {

                //Make sure only TermTotals calculated by congressional cycle are added to our map
                if (termTotals.getCongress() != 0)
                    termTotalsHashMap.put(termTotals.getCongress(), termTotals);
            }

            return termTotalsHashMap;
        }

        return null;
    }

    /**
     * Functional helper method that compartmentalizes the logic for updating TermTotals with PAC contributions.
     * @param bioguideId
     * @param termTotalsMap
     */
    private void setPacTotals(String bioguideId, HashMap<Integer, TermTotals> termTotalsMap) {

        HashMap<Integer, List<PacPoliticianContrTotals>> totalsHashMap = pacContributionService.getPacTotalsAllTime(bioguideId);

        //Get an iterator for the values in the hash map
        Iterator it = termTotalsMap.entrySet().iterator();
        Map.Entry pairs;

        //Iterate through all entry pairs in the map and update the TermTotalsMap with the values.
        while (it.hasNext()) {
            pairs = (Map.Entry) it.next();

            if (termTotalsMap.containsKey(pairs.getKey()))
                termTotalsMap.get(pairs.getKey()).setTopPACContributions((List<PacPoliticianContrTotals>)pairs.getValue());
            else {
                //If the termTotalsMap doesn't have the current cycle, make a new object for it
                TermTotals termTotals = new TermTotals();
                termTotals.setTopPACContributions((List<PacPoliticianContrTotals>) pairs.getValue());
                termTotalsMap.put((Integer)pairs.getKey(), termTotals);
            }
        }

    }

    /**
     * Functional helper method that compartmentalizes the logic for updating TermTotals with Industry contributions.
     * @param bioguideId
     * @param termTotalsMap
     */
    private void setIndustryTotals(String bioguideId, HashMap<Integer, TermTotals> termTotalsMap) {

        HashMap<Integer, List<IndustryPoliticianContributions>> totalsHashMap = industryContributionService.getIndustryTotalsAllTime(bioguideId);

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

    /**
     * Functional helper method that compartmentalizes the logic for updating TermTotals with ideology scores.
     * @param bioguideId
     * @param termTotalsMap
     */
    private void setIdeologyScores(String bioguideId, HashMap<Integer, TermTotals> termTotalsMap) {

        //Get an iterator for the values in the hash map
        Iterator it = termTotalsMap.entrySet().iterator();
        Map.Entry pairs;

        while (it.hasNext()) {

            pairs = (Map.Entry)it.next();
            LegislatorIdeology ideology = ideologyService.getLegislatorIdeology(bioguideId, (Integer) pairs.getKey());

            if (ideology != null) {

                TermTotals totals = ((TermTotals) pairs.getValue());

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
    public void setPoliticianProfileRepo(PoliticianProfileRepo politicianProfileRepo) {
        this.politicianProfileRepo = politicianProfileRepo;
    }
}
