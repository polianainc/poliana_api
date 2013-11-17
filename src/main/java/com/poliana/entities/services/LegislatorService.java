package com.poliana.entities.services;

import com.poliana.entities.models.Legislator;
import com.poliana.entities.repositories.EntitiesHadoopRepo;
import com.poliana.entities.repositories.LegislatorsCRUDRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class LegislatorService {
    @Autowired
    protected EntitiesHadoopRepo entitiesHadoopRepo;
    @Autowired
    protected LegislatorsCRUDRepo legislatorsCRUDRepo;

    private Map<String, List<Legislator>> lisCache;
    private Map<String, List<Legislator>> bioguideCache;
    private Map<String, List<Legislator>> thomasCache;

    public Map<String, String> politicianMap() {
//        List<Legislator> legislators = entitiesHadoopRepo.
//        Map<String, String> politicianMap = new HashMap<>(legislators.size());
//        for(Legislator legislator : legislators) {
//            politicianMap.put(legislator.getBioguideId(), legislator.getParty());
//        }
//        return politicianMap;
        return null;
    }

    public Legislator legislatorByIdTimestamp(String id, int timeStamp) {
        
        List<Legislator> legislators;

        if (lisCache == null) { setCacheLis(); }
        if (bioguideCache == null) { setCacheBioguide(); }
        if (thomasCache == null) { setCacheThomas(); }

        int idLength = id.length();
        if(idLength == 4)
            legislators = lisCache.get(id);
        else if (idLength == 7)
            legislators = bioguideCache.get(id);
        else if (idLength == 5)
            legislators = thomasCache.get(id);
        else
            return null;

        int correctTerm = 0;
        int closestTimeStamp = 0;
        int diff;
        int termStart;
        int index = 0;

        for (Legislator legislator: legislators) {
            termStart = legislator.getTermStart();
            diff = Math.abs(timeStamp) - Math.abs(termStart);
            if ( timeStamp > termStart && diff > (closestTimeStamp-termStart)) {
                correctTerm = index;
                closestTimeStamp = termStart;
            }
            index++;
        }
        return legislators.get(correctTerm);
    }

    public void setCacheLis() {
        List<Legislator> legislators = legislatorsCRUDRepo.findAll();

        lisCache = new HashMap<String, List<Legislator>>(legislators.size());

        String currLis;
        for (Legislator legislator: legislators) {
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
        List<Legislator> legislators = legislatorsCRUDRepo.findAll();
        bioguideCache = new HashMap<String, List<Legislator>>(legislators.size());

        String currBioguide;
        for (Legislator legislator: legislators) {
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
        List<Legislator> legislators = legislatorsCRUDRepo.findAll();
        thomasCache = new HashMap<String, List<Legislator>>(legislators.size());

        String currThomas;
        for (Legislator legislator: legislators) {
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
