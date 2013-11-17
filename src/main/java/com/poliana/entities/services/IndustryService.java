package com.poliana.entities.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.poliana.entities.models.Industry;
import com.poliana.entities.repositories.EntitiesHadoopRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndustryService {
    @Autowired
    protected EntitiesHadoopRepo entitiesHadoopRepo;

    public Map<String, String> industryNameMap() {
        List<Industry> industries = entitiesHadoopRepo.getIndustries();
        Map<String, String> industryMap = new HashMap<>(industries.size());
        for(Industry industry : industries) {
            industryMap.put(industry.getCategoryId(), industry.getIndustry());
        }
        return industryMap;
    }

    public Map<String, String> catNameMap() {
        List<Industry> industries = entitiesHadoopRepo.getIndustries();
        Map<String, String> industryMap = new HashMap<>(industries.size());
        for(Industry industry : industries) {
            industryMap.put(industry.getCategoryId(), industry.getName());
        }
        return industryMap;
    }
}
