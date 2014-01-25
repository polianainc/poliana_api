package com.poliana.core.legislators;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LegislatorService {

    private LegislatorRepo legislatorRepo;

    private Map<String, List<Legislator>> lisCache;
    private Map<String, List<Legislator>> bioguideCache;
    private Map<String, List<Legislator>> thomasCache;

    private static final Logger logger = Logger.getLogger(LegislatorService.class);


    /**
     * Given a lis, bioguide, or thomas id and a timestamp, return the corresponding legislator object.
     * Note: this method uses cached global hash maps for each type of id.
     *
     * @see com.poliana.core.legislators.Legislator
     */
    public Legislator getLegislatorByIdTimestamp(String id, long timestamp) {

        List<Legislator> legislators = getLegislatorTermsById(id);
        return getCorrectTerm(legislators, timestamp);
    }

    /**
     * Determine the correct term for a legislator given a list of all terms he or she has served and a timestamp
     * @param legislators
     * @param timestamp
     * @return
     */
    public Legislator getCorrectTerm(List<Legislator> legislators, long timestamp) {

        int correctTerm = 0;
        long closestTimeStamp = 0;
        long diff;
        long termStart;
        int index = 0;

        if (legislators != null && legislators.size() > 0) {
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
     * Any of Thomas, LIS, Bioguide and Mongo IDs will be resolved and return a list of legislator terms.
     * @param id
     * @return
     */
    public List<Legislator> getLegislatorTermsById(String id) {

        List<Legislator> legislators;

        int idLength = id.length();
        if (idLength < 4) { // No IDs we use have a length under 4. The general explanation is a malformed Thomas Id
            try {
                int num = Integer.parseInt(id); //If it is a number, it is now most probable it's indeed a Thomas Id
                id = String.format("%05d", num); //Pad with 0s
                idLength = id.length(); //Update the length
            }
            catch (NumberFormatException e) {
                logger.error(e.getMessage());
            }
        }
        if(idLength == 4)
            legislators = legislatorRepo.getLegislatorTermsByLis(id);
        else if (idLength == 7)
            legislators = legislatorRepo.getLegislatorTermsByBioguide(id);
        else if (idLength == 5)
            legislators = legislatorRepo.getLegislatorTermsByThomas(id);
        else {
            legislators = new LinkedList<>();
            Iterator<Legislator> iterator = legislatorRepo.getLegislator(id);
            while (iterator.hasNext()) {
                Legislator legislator = iterator.next();
                legislators.add(legislator);
            }
        }
        return legislators;
    }

    /**
     * Use cached maps of legislators to resolved legislator ids to legislator objects. If the cache is not populated,
     * it will be upon its ID type being requested
     * @param id
     * @param timestamp
     * @return
     */
    public Legislator getCachedLegislatorByIdTimestamp(String id, long timestamp) {

        List<Legislator> legislators;


        int idLength = id.length();

        if (idLength < 4) { // No IDs we use have a length under 4. The general explanation is a malformed Thomas Id
            try {
                int num = Integer.parseInt(id); //If it is a number, it is now most probable it's indeed a Thomas Id
                id = String.format("%05d", num); //Pad with 0s
                idLength = id.length(); //Update the length
            }
            catch (NumberFormatException e) {
                logger.error(e.getMessage());
            }
        }
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
            Iterator<Legislator> iterator = legislatorRepo.getLegislator(id);
            while (iterator.hasNext()) {
                Legislator legislator = iterator.next();
                legislators.add(legislator);
            }
        }

        int correctTerm = 0;
        long closestTimeStamp = 0;
        long diff;
        long termStart;
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

    public void setCacheLis() {
        Iterator<Legislator> legislators =
                legislatorRepo.getAllLegislators();

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
                legislatorRepo.getAllLegislators();
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
                legislatorRepo.getAllLegislators();
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

    @Autowired
    public void setLegislatorRepo(LegislatorRepo legislatorRepo) {
        this.legislatorRepo = legislatorRepo;
    }
}
