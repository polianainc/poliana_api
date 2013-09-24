package com.poliana.politicitans.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.poliana.politicitans.entities.Politician;
import com.poliana.politicitans.repositories.PoliticianHiveJdbcRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PoliticianService {
    @Autowired
    protected PoliticianHiveJdbcRepo politicianHiveJdbcRepo;

    public Map<String, String> politicianMap() {
        List<Politician> politicians = politicianHiveJdbcRepo.getPoliticians();
        Map<String, String> politicianMap = new HashMap<>(politicians.size());
        for(Politician politician : politicians) {
            politicianMap.put(politician.getBioguideId(), politician.getParty());
        }
        return politicianMap;
    }

}
