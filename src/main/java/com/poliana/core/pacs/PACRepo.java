package com.poliana.core.pacs;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.google.code.morphia.query.Query;
import com.poliana.core.bills.mappers.CongCommitteeMapper;
import com.poliana.core.bills.entities.CongCommittee;
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
public class PACRepo {

    private JdbcTemplate hiveTemplate;
    private Datastore mongoStore;

    /**
     *
     * @return
     */
    public Iterator<CongCommittee> getCommittees() {
        Query<CongCommittee> query = mongoStore.createQuery(CongCommittee.class);
        return query.iterator();
    }

    /**
     *
     * @param congCommittee
     * @return
     */
    public Iterable<Key<CongCommittee>> saveCommittees(List<CongCommittee> congCommittee) {
        return mongoStore.save(congCommittee);
    }

    /**
     *
     */
    public Iterable<Key<CongCommittee>> loadCommitteesToMongo() {
        List<CongCommittee> committees = getCongCommitties();
        return saveCommittees(committees);
    }

    /**
     *
     * @return
     */
    public List<CongCommittee> getCongCommitties() {
        return hiveTemplate.query("SELECT * FROM entities.congressional_committee_ids",
                new CongCommitteeMapper());
    }

    @Autowired
    public void setHiveTemplate(JdbcTemplate hiveTemplate) {
        this.hiveTemplate = hiveTemplate;
    }

    @Autowired
    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }
}
