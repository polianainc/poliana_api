package com.poliana.entities.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.poliana.entities.entities.Legislator;
import com.poliana.entities.repositories.EntitiesHiveRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PoliticianService {
    @Autowired
    protected EntitiesHiveRepo entitiesHiveRepo;

    public Map<String, String> politicianMap() {
        List<Legislator> legislators = entitiesHiveRepo.getAllLegislators();
        Map<String, String> politicianMap = new HashMap<>(legislators.size());
        for(Legislator legislator : legislators) {
            politicianMap.put(legislator.getBioguideId(), legislator.getParty());
        }
        return politicianMap;
    }

}
