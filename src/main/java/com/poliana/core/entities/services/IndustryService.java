package com.poliana.core.entities.services;

import com.poliana.core.entities.repositories.EntitiesMongoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.poliana.core.entities.entities.Industry;
import com.poliana.core.entities.repositories.EntitiesHadoopRepo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class IndustryService {
    @Autowired
    protected EntitiesHadoopRepo entitiesHadoopRepo;
    @Autowired
    private EntitiesMongoRepo entitiesMongoRepo;

    private HashMap<String, Industry> industryMap;

    public Map<String, String> catNameMap() {
        List<Industry> industries = entitiesHadoopRepo.getIndustries();
        Map<String, String> industryMap = new HashMap<>(industries.size());
        for(Industry industry : industries) {
            industryMap.put(industry.getIndustryId(), industry.getName());
        }
        return industryMap;
    }

    public void setIndustryMap() {
        Iterator<Industry> industryList = entitiesMongoRepo.getIndustries();
        industryMap = new HashMap<String, Industry>(500);
        while (industryList.hasNext()) {
            Industry industry = industryList.next();
            industryMap.put(industry.getIndustryId(),industry);
        }
    }

    public HashMap<String, Industry> getIndustryMap() {
        if (industryMap == null)
            setIndustryMap();
        return industryMap;
    }
}
