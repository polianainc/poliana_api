package com.poliana.core.industries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class IndustryService {

    @Autowired
    protected IndustryRepo industryRepo;

    private HashMap<String, Industry> industryMap;

    public Map<String, String> catNameMap() {
        List<Industry> industries = industryRepo.getIndustries();
        Map<String, String> industryMap = new HashMap<>(industries.size());
        for(Industry industry : industries) {
            industryMap.put(industry.getIndustryId(), industry.getName());
        }
        return industryMap;
    }

    public void setIndustryMap() {
        Iterator<Industry> industryList = industryRepo.getIndustriesFromMongo();
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
