package com.poliana.jobs.batch;


import org.springframework.stereotype.Component;
import com.poliana.jobs.JobBase;
import com.poliana.contributions.models.InfluenceGraph;

@Component
public class BillParse extends JobBase {

    public InfluenceGraph populatePartyIndustryNames(String filename) {
        InfluenceGraph bill = parseBillJson(filename);
        System.out.println(bill.getId());
        return null;
    }

    public InfluenceGraph parseBillJson(String filename) {
        return null;
    }

}
