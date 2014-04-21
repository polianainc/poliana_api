package com.poliana.core.politicianFinance.general;

import com.rollup.olap.models.DataNode;
import com.rollup.olap.HolapClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author David Gilmore
 * @date 4/15/14
 */
@Service
public class PoliticianFinanceService {

    private static final Logger logger = Logger.getLogger(PoliticianFinanceService.class);

    private HolapClient holapClient;
    private PoliticianFinanceRepository politicianFinanceRepository;


    /**
     * Get a list of pac and industry totals per congress for all time wrapper in a DataNode.
     * The return will be a list of Map<String, Object>
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public DataNode getPacAndIndustryTotals(String unit) {

        DataNode ret;

        if (unit != null && unit.equals("congress")) {

            List<Map<String, Object>> contributions = politicianFinanceRepository.getPacAndIndustryTotalsPerCongress();
            ret = new DataNode(holapClient.rollup(unit, contributions));
        }
        else {

            List<Map<String, Object>> contributions = politicianFinanceRepository.getPacAndIndustryTotals();
            ret = new DataNode(contributions);
        }

        return ret;
    }

    @Autowired
    public void setHolapClient(HolapClient holapClient) {
        this.holapClient = holapClient;
    }

    @Autowired
    public void setPoliticianFinanceRepository(PoliticianFinanceRepository politicianFinanceRepository) {
        this.politicianFinanceRepository = politicianFinanceRepository;
    }
}
