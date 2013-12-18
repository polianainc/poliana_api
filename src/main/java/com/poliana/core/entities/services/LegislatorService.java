package com.poliana.core.entities.services;

import com.poliana.core.entities.entities.Legislator;
import com.poliana.core.entities.models.PoliticianProfile;
import com.poliana.core.entities.models.TermTotals;
import com.poliana.core.entities.repositories.EntitiesHadoopRepo;
import com.poliana.core.entities.repositories.EntitiesMongoRepo;
import com.poliana.core.legislation.services.LegislationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LegislatorService {
    @Autowired
    protected EntitiesHadoopRepo entitiesHadoopRepo;
    @Autowired
    private EntitiesMongoRepo entitiesMongoRepo;
    @Autowired
    private LegislationService legislationService;

    private Map<String, List<Legislator>> lisCache;
    private Map<String, List<Legislator>> bioguideCache;
    private Map<String, List<Legislator>> thomasCache;


    /**
     * Given a bioguide id, check mongo for the correct PoliticianProfile.
     * If it's not been processed, fall back to impala.
     *
     *  TODO: big-O analysis of all methods used
     * @param bioguideId  {String}
     * @return
     *
     * @see               com.poliana.core.entities.models.PoliticianProfile
     */
    public PoliticianProfile getPoliticianProfile(String bioguideId) {
        PoliticianProfile profile = new PoliticianProfile();
        profile.setBioguideId(bioguideId);

        return profile;
    }

    /**
     * Given a list of legislators, return a list of corresponding term totals
     *
     * TODO: big-O analysis of all methods used
     * @see com.poliana.core.entities.models.TermTotals
     */
    public List<TermTotals> getTermTotals(List<Legislator> legislators) {
        List<TermTotals> termTotalsList = new LinkedList<>();

        for (Legislator legislator: legislators) {

        }

        return termTotalsList;
    }

    /**
     *
     * @param legislator
     * @return
     */
    public TermTotals getTermTotals(Legislator legislator) {
        TermTotals termTotals = new TermTotals();

        return termTotals;
    }

    /**
     * Given a lis, bioguide, or thomas id and a timestamp, return the corresponding legislator object.
     * Note: this method uses cached global hash maps for each type of id.
     *
     * @see com.poliana.core.entities.entities.Legislator
     */
    public Legislator legislatorByIdTimestamp(String id, int timestamp) {

        List<Legislator> legislators;

        //TODO: evaluate big-O, determine best solution
        int idLength = id.length();
        if(idLength == 4) {
            if (lisCache == null) { setCacheLis(); }
            legislators = lisCache.get(id);
        }
        else if (idLength == 7) {
            if (bioguideCache == null) { setCacheBioguide(); }
            legislators = bioguideCache.get(id);
        }
        else if (idLength == 5) {
            if (thomasCache == null) { setCacheThomas(); }
            legislators = thomasCache.get(id);
        }
        else {
            legislators = new LinkedList<>();
            Iterator<Legislator> iterator = entitiesMongoRepo.getLegislator(id);
            while (iterator.hasNext()) {
                Legislator legislator = iterator.next();
                legislators.add(legislator);
            }
        }

        int correctTerm = 0;
        int closestTimeStamp = 0;
        int diff;
        int termStart;
        int index = 0;

        if (legislators != null) {
            for (Legislator legislator: legislators) {
                termStart = legislator.getBeginTimestamp();
                diff = Math.abs(timestamp) - Math.abs(termStart);
                if ( timestamp > termStart && diff > (closestTimeStamp-termStart)) {
                    correctTerm = index;
                    closestTimeStamp = termStart;
                }
                index++;
            }

            return legislators.get(correctTerm);
        }

        return null;
    }

    /**
     * Useful method for mapping bioguide ids to object ids
     *
     * @param legislators  {List<Legislator>}
     *
     * @see com.poliana.core.entities.entities.Legislator
     */
    public HashMap<String,String> getLegislatorBioguideMap(List<Legislator> legislators) {
        HashMap<String,String> legislatorMap = new HashMap<>(500);

        for (Legislator legislator: legislators)
            legislatorMap.put(legislator.getBioguideId(), legislator.getId());

        return legislatorMap;
    }

/**********************************************************************************************************************/

/**********************************************************************************************************************/

    private void setCacheLis() {
        Iterator<Legislator> legislators =
                entitiesMongoRepo.allLegislatorTerms();

        lisCache = new HashMap<>(43000);

        String currLis;
        while (legislators.hasNext()) {
            Legislator legislator = legislators.next();
            List<Legislator> currLegislators;
            currLis = legislator.getLisId();
            if(lisCache.containsKey(currLis))
                currLegislators = lisCache.get(currLis);
            else
                currLegislators = new LinkedList<>();
            currLegislators.add(legislator);

            if (null != currLis)
                lisCache.put(currLis, currLegislators);
        }
    }

    private void setCacheBioguide() {
        Iterator<Legislator> legislators =
                entitiesMongoRepo.allLegislatorTerms();
        bioguideCache = new HashMap<>(43000);

        String currBioguide;
        while (legislators.hasNext()) {
            Legislator legislator = legislators.next();
            List<Legislator> currLegislators;
            currBioguide = legislator.getBioguideId();
            if(bioguideCache.containsKey(currBioguide))
                currLegislators = bioguideCache.get(currBioguide);
            else
                currLegislators = new LinkedList<>();
            currLegislators.add(legislator);

            bioguideCache.put(currBioguide, currLegislators);
        }
    }

    private void setCacheThomas() {
        Iterator<Legislator> legislators =
                entitiesMongoRepo.allLegislatorTerms();
        thomasCache = new HashMap<>(43000);

        String currThomas;
        while (legislators.hasNext()) {
            Legislator legislator = legislators.next();
            List<Legislator> currLegislators;
            currThomas = legislator.getThomasId();
            if(thomasCache.containsKey(currThomas))
                currLegislators = thomasCache.get(currThomas);
            else
                currLegislators = new LinkedList<>();
            currLegislators.add(legislator);

            thomasCache.put(currThomas, currLegislators);
        }
    }
}
