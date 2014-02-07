package com.poliana.web.amendments;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author David Gilmore
 * @date 1/16/14
 */
@Controller
@RequestMapping("/bills/amendments/")
public class BillAmendmentsController {

    @ResponseBody
    @RequestMapping(value="{amendment_id}", method = RequestMethod.GET)
    public String getBillAmendments(
            @PathVariable("amendment_id") String amendmentId) {

        return "bill amendments with billId " + amendmentId;
    }
}
