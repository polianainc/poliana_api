package com.poliana.entities.services;

import com.poliana.entities.entities.Legislator;
import com.poliana.entities.repositories.EntitiesHadoopRepo;
import com.poliana.entities.repositories.EntitiesMongoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LegislatorService {
    @Autowired
    protected EntitiesHadoopRepo entitiesHadoopRepo;
    @Autowired
    private EntitiesMongoRepo entitiesMongoRepo;

    private Map<String, List<Legislator>> lisCache;
    private Map<String, List<Legislator>> bioguideCache;
    private Map<String, List<Legislator>> thomasCache;

    public Map<String, String> politicianMap() {
        return null;
    }

    public Legislator legislatorByIdTimestamp(String id, int timestamp) {

        List<Legislator> legislators;


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
                termStart = legislator.getTermStart();
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

    public void setCacheLis() {
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

    public void setCacheBioguide() {
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

    public void setCacheThomas() {
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
