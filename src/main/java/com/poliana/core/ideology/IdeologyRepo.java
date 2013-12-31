package com.poliana.core.ideology;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.google.code.morphia.query.Query;
import com.poliana.core.common.util.TimeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author David Gilmore
 * @date 12/29/13
 */
@Repository
public class IdeologyRepo {

    @Autowired
    private Datastore mongoStore;

    private static final Logger logger = Logger.getLogger(IdeologyRepo.class);


    public Key<IdeologyMatrix> saveIdeologyMatrix(IdeologyMatrix ideologyMatrix) {

        return mongoStore.save(ideologyMatrix);
    }

    public IdeologyMatrix getIdeologyMatrix(String chamber, int congress) {

        Query<IdeologyMatrix> query = mongoStore.find(IdeologyMatrix.class);
        int[] timestamps = TimeUtils.yearTimestamps(congress);
        query.or(
                query.criteria("chamber").equal(chamber),
                query.criteria("beginTimestamp").equal(timestamps[0]),
                query.criteria("endTimestamp").equal(timestamps[1]));
        return query.get();
    }
}
