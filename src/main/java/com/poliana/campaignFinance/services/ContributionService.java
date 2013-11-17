package com.poliana.campaignFinance.services;

import com.poliana.campaignFinance.repositories.IndustryCRUDRepo;
import com.poliana.entities.models.Industry;
import com.poliana.entities.models.IndustryProfile;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

/**
 * @author David Gilmore
 * @date 11/15/13
 */
public class ContributionService {

    @Autowired
    private IndustryCRUDRepo industryCRUDRepo;

    private HashMap<String,Industry> industryMap;


//    public IndustryProfile

    public void setIndustryMap() {
        List<Industry> industryList = industryCRUDRepo.findAll();
        industryMap = new HashMap<String, Industry>(industryList.size());
        for (Industry industry : industryList) {
            industryMap.put(industry.getCategoryId(),industry);
        }
    }
}
