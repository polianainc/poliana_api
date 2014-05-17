package com.poliana.web.politicians.finance;

import com.poliana.core.politicianFinance.general.PoliticianFinanceService;
import com.poliana.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author David Gilmore
 * @date 4/15/14
 */
@Controller
@RequestMapping(value = "/politicians")
public class PoliticianFinanceController {

    private PoliticianFinanceService politicianFinanceService;

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/contributions", method = RequestMethod.GET)
    public @ResponseBody HttpEntity<Response> getPacAndIndustryTotals() {

        Response res = new Response(politicianFinanceService.getPacAndIndustryTotals().getData());

        res.add(linkTo(methodOn(PoliticianFinanceController.class).getPacAndIndustryTotals()).withSelfRel());

        res.add(
                linkTo(methodOn(PoliticianIndustryFinanceController.class).getAllIndustryToPoliticianTotalsAllTime())
                        .withRel("industry_contributions"));

        res.add(
                linkTo(methodOn(PoliticianPacFinanceController.class).getAllPacToPoliticianTotalsAllTime())
                        .withRel("pac_contributions")
        );

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

//    @SuppressWarnings("unchecked")
//    @RequestMapping(value = "/contributions", params = {"unit"}, method = RequestMethod.GET)
//    public @ResponseBody HttpEntity<Response> getPacAndIndustryTotals(
//            @RequestParam(value = "unit", required = true) String unit) {
//
//        Response res = new Response(politicianFinanceService.getPacAndIndustryTotals(unit).getData());
//
//        res.add(linkTo(methodOn(PoliticianFinanceController.class).getPacAndIndustryTotals(unit)).withSelfRel());
//
//        res.add(
//                linkTo(methodOn(PoliticianIndustryFinanceController.class).getAllIndustryToPoliticianTotalsAllTime())
//                        .withRel("industry_contributions"));
//
//        res.add(
//                linkTo(methodOn(PoliticianPacFinanceController.class).getAllPacToPoliticianTotalsAllTime())
//                        .withRel("pac_contributions")
//        );
//
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/contributions", params = {"congress"}, method = RequestMethod.GET)
    public @ResponseBody HttpEntity<Response> getPacAndIndustryTotalsByCongress(
            @RequestParam(value = "congress", required = true) Integer congress) {

        Response res = new Response(politicianFinanceService.getPacAndIndustryTotalsByCongress(congress).getData());

        res.add(linkTo(methodOn(PoliticianFinanceController.class).getPacAndIndustryTotals()).withSelfRel());

        res.add(
                linkTo(methodOn(PoliticianIndustryFinanceController.class).getAllIndustryToPoliticianTotalsAllTime())
                        .withRel("industry_contributions"));

        res.add(
                linkTo(methodOn(PoliticianPacFinanceController.class).getAllPacToPoliticianTotalsAllTime())
                        .withRel("pac_contributions")
        );

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Autowired
    public void setPoliticianFinanceService(PoliticianFinanceService politicianFinanceService) {
        this.politicianFinanceService = politicianFinanceService;
    }
}
