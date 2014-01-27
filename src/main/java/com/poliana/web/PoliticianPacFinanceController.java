package com.poliana.web;

import com.poliana.core.politicianFinance.pacs.PoliticianPacFinanceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author David Gilmore
 * @date 1/26/14
 */
@Controller
public class PoliticianPacFinanceController {

    private PoliticianPacFinanceService politicianPacFinanceService;

    private static final Logger logger = Logger.getLogger(PoliticianPacFinanceController.class);




    @Autowired
    public void setPoliticianPacFinanceService(PoliticianPacFinanceService politicianPacFinanceService) {
        this.politicianPacFinanceService = politicianPacFinanceService;
    }
}
