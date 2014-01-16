package com.poliana.web;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @author Grayson Carroll
 * @date 1/15/14
 */
@Controller
@RequestMapping("/bills/")
public class BillController extends AbstractBaseController {

    private static final Logger logger = Logger.getLogger(BillController.class);


    @RequestMapping(value="", headers="Accept=*/*", params = {"congress", "chamber"}, method = RequestMethod.GET)
    public @ResponseBody
    String getBills(
            @RequestParam("congress") Integer congress) {


        return "bills root in congress " + congress;
    }

    @RequestMapping(value="/{bill_id}", headers="Accept=*/*", params = {"congress", "chamber"}, method = RequestMethod.GET)
    public @ResponseBody
    String getBill(
            @RequestParam("congress") Integer congress,
            @PathVariable("bill_id") String billId) {

        return "bills with billId " + billId + "in congress " + congress;
    }

    @RequestMapping(value="/{bill_id}/amendments/", headers="Accept=*/*", params = {"congress", "chamber"}, method = RequestMethod.GET)
    public @ResponseBody
    String getBillAmendments(
            @RequestParam("congress") Integer congress,
            @PathVariable("bill_id") String billId) {

        return "bill amendments with billId " + billId;
    }

    @RequestMapping(value="/{bill_id}/votes/", headers="Accept=*/*", params = {"congress", "chamber"}, method = RequestMethod.GET)
    public @ResponseBody
    String getVotes(
            @RequestParam("congress") Integer congress,
            @PathVariable("bill_id") String billId) {

        return "bill votes with billId " + billId;
    }

    @RequestMapping(value="/{bill_id}/related/", headers="Accept=*/*", params = {"congress", "chamber"}, method = RequestMethod.GET)
    public @ResponseBody
    String getRelatedBills(
            @RequestParam("congress") Integer congress,
            @PathVariable("bill_id") String billId) {

        return "bills related to bill with billId " + billId;
    }

}

