package com.poliana.core.legislators;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LegislatorService {

    private LegislatorRepo legislatorRepo;

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

    @Autowired
    public void setLegislatorRepo(LegislatorRepo legislatorRepo) {
        this.legislatorRepo = legislatorRepo;
    }
}
