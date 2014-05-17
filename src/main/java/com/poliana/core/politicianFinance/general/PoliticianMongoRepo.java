package com.poliana.core.politicianFinance.general;

import com.locke.olap.models.DataNode;
import com.mongodb.*;
import com.poliana.core.politicianFinance.financeProfile.SessionTotals;
import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author David Gilmore
 * @date 4/20/14
 */
@Repository
public class PoliticianMongoRepo {

    private DB mongoDb;
    private Datastore mongoStore;

    private static final Logger logger = Logger.getLogger(PoliticianMongoRepo.class);


    @SuppressWarnings("unchecked")
    public DataNode getPacAndIndustryTotals() {

        DBCollection coll = mongoDb.getCollection("industry_and_pac_totals");

        BasicDBObject baseQuery = new BasicDBObject("congress", new BasicDBObject("$exists", false));

        BasicDBObject fields = new BasicDBObject();
        fields.put("_id", 0);

        DBCursor cur = coll.find(baseQuery, fields);

        return new DataNode(cur.toArray());
    }

    @SuppressWarnings("unchecked")
    public DataNode getPacAndIndustryTotalsByCongress(int congress) {

        DBCollection coll = mongoDb.getCollection("industry_and_pac_totals");

        BasicDBObject baseQuery = new BasicDBObject("congress", congress);

        BasicDBObject fields = new BasicDBObject();
        fields.put("_id", 0);

        DBCursor cur = coll.find(baseQuery, fields);

        return new DataNode(cur.toArray());
    }

    /**
     * Save a list of industry to politician contribution objects
     * @param totalsList
     * @return
     */
    @SuppressWarnings("unchecked")
    public void saveIndustryAndPacContributions(List<Map<String, Object>> totalsList) {

        if (totalsList == null) return;

        DBObject[] objectArray = new DBObject[totalsList.size()];

        int i = 0;
        for (Map<String, Object> map: totalsList) {

            DBObject obj = new BasicDBObject();

            Iterator it = map.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                obj.put((String) entry.getKey(), entry.getValue());
            }

            objectArray[i++] = obj;
        }

        DBCollection mongoColl = mongoDb.getCollection("industry_and_pac_totals");

        mongoColl.insert(objectArray, WriteConcern.SAFE);

    }


    public List<SessionTotals> getSessionTotals(String bioguide) {

        Query<SessionTotals> query = mongoStore.find(SessionTotals.class);
        query.criteria("bioguideId").equal(bioguide);
        return query.asList();
    }

    @Autowired
    public void setMongoDb(DB mongoDb) {
        this.mongoDb = mongoDb;
    }

    @Autowired
    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }
}
