package com.poliana.core.legislators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * The Legislator Condense service takes the flattened term objects for all legislators and condenses them into
 * a more application friendly (as opposed to data science friendly) collection.
 *
 * @author Grayson Carroll
 * @date 3/17/14
 */
@Service
public class LegislatorCondenseService {

    private LegislatorMongoRepo legislatorMongoRepo;
    private LegislatorRedisRepo legislatorRedisRepo;

    /**
     * Return all legislators flattened by bioguideId
     * @return
     */
    public List<LegislatorCondensed> getCondensedLegislators() {

        List<LegislatorCondensed> legislatorsCondensed;

        if(!legislatorRedisRepo.getCondensedLegislatorsCached()) {
            // if there aren't any condensed legislators in redis, grab the flat
            // legislators from mongo, iterate over them
            Iterator<Legislator> legislatorIterator = legislatorMongoRepo.getAllLegislators();
            // and create the condensed legislators,
            List<LegislatorCondensed> legislatorCondensedList = condenseLegislators(legislatorIterator);

            // then, store them in redis
            for(LegislatorCondensed legislator : legislatorCondensedList) {
                legislatorRedisRepo.saveLegislatorCondensed(legislator);
            }
            legislatorRedisRepo.setCondensedLegislatorsCached(true);
        }

        legislatorsCondensed = legislatorRedisRepo.getCondensedLegislators();

        // regardless of how they're created, send 'em out
        return legislatorsCondensed;
    }

    /**
     * Return all legislators in the given time range flattened by bioguideId
     * @return
     */
    public List<LegislatorCondensed> getCondensedLegislators(long beginTimestamp, long endTimestamp) {

        legislatorMongoRepo.getLegislators(beginTimestamp, endTimestamp);

        //TODO: Implement Logic
        LegislatorCondensed legislatorCondensed = new LegislatorCondensed();
        List<LegislatorCondensed> legislatorList = new LinkedList<>();

        return legislatorList;
    }

    /**
     * Construct a list of LegislatorCondensed objects from a list of flat legislator (term) objects.
     *
     * @see LegislatorCondensed
     * @see Legislator
     *
     * @param legislatorIterator
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<LegislatorCondensed> condenseLegislators(Iterator<Legislator> legislatorIterator) {

        HashMap<String, LegislatorCondensed> legislatorCondensedHashMap = new HashMap<>(15000);

        while (legislatorIterator.hasNext()) {

            Legislator currLegislator = legislatorIterator.next();


            String currBioguide = currLegislator.getBioguideId();

            //If a LegislatorCondensed object exists in the map with the current bioguide ID, update it
            if (legislatorCondensedHashMap.containsKey(currBioguide))
                createOrUpdateLegislatorCondensed(legislatorCondensedHashMap.get(currBioguide), currLegislator);
            //Other wise we'll make a new one
            else {
                LegislatorCondensed legislatorCondensed = createOrUpdateLegislatorCondensed(new LegislatorCondensed(), currLegislator);
                legislatorCondensedHashMap.put(currBioguide, legislatorCondensed);
            }

        }

        return (List) legislatorCondensedHashMap.values();
    }

    private LegislatorCondensed createOrUpdateLegislatorCondensed(LegislatorCondensed legislatorCondensed, Legislator legislator) {

        legislatorCondensed.setBioguideId( (legislatorCondensed.getBioguideId() == null || legislatorCondensed.getBioguideId().equals("")) ? legislator.getBioguideId() : legislatorCondensed.getBioguideId() );
        legislatorCondensed.setFirstName( (legislatorCondensed.getFirstName() == null || legislatorCondensed.getFirstName().equals("")) ? legislator.getFirstName() : legislatorCondensed.getFirstName() );
        legislatorCondensed.setLastName( (legislatorCondensed.getLastName() == null || legislatorCondensed.getLastName().equals("")) ? legislator.getLastName() : legislatorCondensed.getLastName() );
        legislatorCondensed.setGender( (legislatorCondensed.getGender() == null || legislatorCondensed.getGender().equals("")) ? legislator.getGender() : legislatorCondensed.getGender() );
        legislatorCondensed.setBirthday( (legislatorCondensed.getBirthday() == null || legislatorCondensed.getBirthday().equals("")) ? legislator.getBirthday() : legislatorCondensed.getBirthday() );
        legislatorCondensed.setReligion( (legislatorCondensed.getReligion() == null || legislatorCondensed.getReligion().equals("")) ? legislator.getReligion() : legislatorCondensed.getReligion() );
        legislatorCondensed.setParty( (legislatorCondensed.getParty() == null || legislatorCondensed.getParty().equals("")) ? legislator.getParty() : legislatorCondensed.getParty() );


        LegislatorCondensedTerm term = new LegislatorCondensedTerm();

        term.setBeginTimestamp(legislator.getBeginTimestamp());
        term.setEndTimestamp(legislator.getEndTimestamp());
        term.setTermType(legislator.getTermType());

        Long currentTimestamp = new java.util.Date().getTime();
        // Handling incorrect end timestamps; no politician finished their term exactly on unix timestamp zero,
        // so we adjust there term ends based on the start of the term and the type of term it is.
        if(legislator.getEndTimestamp() == 0) {
            //TODO: Verify that this timestamp logic checks for politicians who have been elected within the past 6 years
            //HACK HACK HACK HACK HACK HACK
            if(term.getTermType().equals("sen") && (currentTimestamp - term.getBeginTimestamp() >  (31536000*6))) {
                term.setEndTimestamp(term.getBeginTimestamp() + (31536000*6));
            }

            else if(term.getTermType().equals("rep") && (currentTimestamp - term.getBeginTimestamp() >  (31536000*2))) {
                term.setEndTimestamp(term.getBeginTimestamp() + (31536000*2));
            }

            else {
                //They've been elected within the past congressional cycle and their timestamp is zero because they are
                //still in office. We can set a date in the future here or just leave it zero, not sure what to do.
                //TODO: Figure out what to do with end timestamps for politicians currently in office
            }
        }

        // Handling duplicate terms; throw 'em out!
        boolean keepTerm = true;
        for (LegislatorCondensedTerm currTerm : legislatorCondensed.getTerms())
            keepTerm = (term != currTerm);

        if (keepTerm)
            legislatorCondensed.getTerms().add(term);


        return legislatorCondensed;
    }

    @Autowired
    public void setLegislatorMongoRepo(LegislatorMongoRepo legislatorMongoRepo) {
        this.legislatorMongoRepo = legislatorMongoRepo;
    }

    @Autowired
    public void setLegislatorRedisRepo(LegislatorRedisRepo legislatorRedisRepo) {
        this.legislatorRedisRepo = legislatorRedisRepo;
    }
}
