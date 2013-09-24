package com.poliana.codes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.poliana.codes.entities.Industry;
import com.poliana.codes.repositories.CodesRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndustryService {
    @Autowired
    protected CodesRepo codesRepo;

    public Map<String, String> industryNameMap() {
        List<Industry> industries = codesRepo.getIndustries();
        Map<String, String> industryMap = new HashMap<>(industries.size());
        for(Industry industry : industries) {
            industryMap.put(industry.getCatCode(), industry.getIndustry());
        }
        return industryMap;
    }

    public Map<String, String> catNameMap() {
        List<Industry> industries = codesRepo.getIndustries();
        Map<String, String> industryMap = new HashMap<>(industries.size());
        for(Industry industry : industries) {
            industryMap.put(industry.getCatCode(), industry.getCatName());
        }
        return industryMap;
    }
}
