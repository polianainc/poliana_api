package com.poliana.entities.services;

import com.poliana.entities.repositories.EntitiesHadoopRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PoliticianService {
    @Autowired
    protected EntitiesHadoopRepo entitiesHiveRepo;

    public Map<String, String> politicianMap() {
//        List<Legislator> legislators = entitiesHiveRepo.
//        Map<String, String> politicianMap = new HashMap<>(legislators.size());
//        for(Legislator legislator : legislators) {
//            politicianMap.put(legislator.getBioguideId(), legislator.getParty());
//        }
//        return politicianMap;
        return null;
    }

}