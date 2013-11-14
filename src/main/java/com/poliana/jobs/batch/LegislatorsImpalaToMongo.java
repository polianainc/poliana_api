package com.poliana.jobs.batch;


import com.poliana.entities.entities.Legislator;
import com.poliana.entities.repositories.EntitiesHadoopRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author David Gilmore
 * @date 11/13/13
 */
@Component
public class LegislatorsImpalaToMongo {

    @Autowired
    private EntitiesHadoopRepo entitiesHadoopRepo;


}
