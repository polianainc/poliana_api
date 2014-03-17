package com.poliana.core.legislators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

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


    /**
     * Return all legislators flattened by bioguideId
     * @return
     */
    public List<LegislatorCondensed> getCondensedLegislators() {

        legislatorMongoRepo.getAllLegislators();

        //TODO: Implement logic
        LegislatorCondensed legislatorCondensed = new LegislatorCondensed();
        List<LegislatorCondensed> legislatorList = new LinkedList<>();

        return legislatorList;
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

    @Autowired
    public void setLegislatorMongoRepo(LegislatorMongoRepo legislatorMongoRepo) {
        this.legislatorMongoRepo = legislatorMongoRepo;
    }
}
