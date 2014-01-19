package com.poliana.shell.controllers;

import com.poliana.core.politicianFinance.industries.PoliticianIndustryFinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.stereotype.Controller;

/**
 * @author David Gilmore
 * @date 12/26/13
 */
@Controller
public class PoliticianFinanceController implements CommandMarker {

    @Autowired
    private PoliticianIndustryFinanceService profileService;

}
