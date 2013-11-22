package com.poliana.entities.jobs;

import com.google.code.morphia.Key;
import com.poliana.entities.entities.CongCommittee;
import com.poliana.entities.entities.Industry;
import com.poliana.entities.entities.Legislator;
import com.poliana.entities.repositories.EntitiesHadoopRepo;
import com.poliana.entities.repositories.EntitiesMongoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author David Gilmore
 * @date 11/22/13
 */
@Component
public class EntityJobs {

    @Autowired
    private EntitiesHadoopRepo entitiesHadoopRepo;

    @Autowired
    private EntitiesMongoRepo entitiesMongoRepo;

    public Iterable<Key<Industry>> loadIndustriesToMongo() {
        List<Industry> industries = entitiesHadoopRepo.getIndustries();
        return entitiesMongoRepo.saveIndustries(industries);
    }

    public Iterable<Key<Legislator>> loadLegislatorsToMongo() {
        List<Legislator> legislators = entitiesHadoopRepo.getAllLegistlators();
        return entitiesMongoRepo.saveLegislators(legislators);
    }

    public Iterable<Key<CongCommittee>> loadCommitteesToMongo() {
        List<CongCommittee> committees = entitiesHadoopRepo.getCongCommitties();
        return entitiesMongoRepo.saveCommittees(committees);
    }
}
