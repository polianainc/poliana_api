package com.poliana.web;

import com.poliana.core.bills.BillService;
import com.poliana.core.bills.entities.Bill;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.poliana.core.time.TimeService.CURRENT_CONGRESS;

/**
 * @author Grayson Carroll
 * @date 1/15/14
 */
@Controller
@RequestMapping("/bills/")
public class BillController extends AbstractBaseController {

    private static final Logger logger = Logger.getLogger(BillController.class);

    private BillService billService;


    /**
     * Get the /bill index
     * @param congress
     * @return
     */
    @ResponseBody
    @RequestMapping(value="", params = {"congress", "chamber"}, method = RequestMethod.GET)
    public String getBills(
            @RequestParam("congress") Integer congress) {


        return "bills root in congress " + congress;
    }

    /**
     * Get a bill by its Id and congress. If no congress is provided, it will default to the current congress
     * @param billId
     * @param congress
     * @return
     */
    @ResponseBody
    @RequestMapping(value="{bill_id}", method = RequestMethod.GET)
    public Bill getBill(
            @PathVariable("bill_id") String billId,
            @RequestParam(value = "congress", defaultValue = CURRENT_CONGRESS) Integer congress) {

        Bill bill = billService.getBill(billId, congress);

        return bill;
    }

    /**
     * Get a list of Ids for related bills
     * @param billId
     * @param congress
     * @return
     */
    @ResponseBody
    @RequestMapping(value="{bill_id}/related", method = RequestMethod.GET)
    public List<String> getRelatedBills(
            @PathVariable("bill_id") String billId,
            @RequestParam(value = "congress", defaultValue = CURRENT_CONGRESS) Integer congress) {

        return null;
    }

    @Autowired
    public void setBillService(BillService billService) {
        this.billService = billService;
    }
}

