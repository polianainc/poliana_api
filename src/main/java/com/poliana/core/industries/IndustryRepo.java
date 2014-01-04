package com.poliana.core.industries;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.google.code.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

/**
 * @author David Gilmore
 * @date 12/26/13
 */
@Repository
public class IndustryRepo {

    private Datastore mongoStore;

    private JdbcTemplate impalaTemplate;

    /**
     *
     * @return
     */
    public Iterator<Industry> getIndustriesFromMongo() {
        Query<Industry> query = mongoStore.createQuery(Industry.class);
        return query.iterator();
    }

    /**
     *
     * @param industryId
     * @return
     */
    public Industry industry(String industryId) {
        return mongoStore.find(Industry.class).filter("industryId", industryId).get();
    }

    /**
     *
     * @param industries
     * @return
     */
    public Iterable<Key<Industry>> saveIndustries(List<Industry> industries) {
        return mongoStore.save(industries);
    }

    /**
     *
     * @return
     */
    public Iterable<Key<Industry>> loadIndustriesToMongo() {
        List<Industry> industries = getIndustries();
        return saveIndustries(industries);
    }

    /**
     *
     * @return
     */
    public List<Industry> getIndustries() {
        return impalaTemplate.query("SELECT * FROM entities.industry_codes", new IndustryMapper());
    }

    @Autowired
    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }

    @Autowired
    public void setImpalaTemplate(JdbcTemplate impalaTemplate) {
        this.impalaTemplate = impalaTemplate;
    }
}
