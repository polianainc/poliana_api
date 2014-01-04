package com.poliana.shell.controllers;

import com.poliana.core.sponsorship.SponsorshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author David Gilmore
 * @date 12/31/13
 */
@Controller
public class SponsorshipController {

    @Autowired
    private SponsorshipService sponsorshipService;


}
