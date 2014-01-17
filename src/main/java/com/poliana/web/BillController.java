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


    @ResponseBody
    @RequestMapping(value="", params = {"congress", "chamber"}, method = RequestMethod.GET)
    public String getBills(
            @RequestParam("congress") Integer congress) {


        return "bills root in congress " + congress;
    }

    @ResponseBody
    @RequestMapping(value="{bill_id}", params = {"congress", "chamber"}, method = RequestMethod.GET)
    public String getBill(
            @RequestParam("congress") Integer congress,
            @PathVariable("bill_id") String billId) {

        return "bills with billId " + billId + "in congress " + congress;
    }

    @ResponseBody
    @RequestMapping(value="{bill_id}/related", params = {"congress", "chamber"}, method = RequestMethod.GET)
    public String getRelatedBills(
            @RequestParam("congress") Integer congress,
            @PathVariable("bill_id") String billId) {

        return "bills related to bill with billId " + billId;
    }

}

