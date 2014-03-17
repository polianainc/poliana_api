package com.poliana.web.politicians;

import com.poliana.core.legislators.LegislatorCondenseService;
import com.poliana.core.legislators.LegislatorCondensed;
import com.poliana.web.common.AbstractBaseController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @author Grayson Carroll
 * @date 1/15/14
 */
@Controller
@RequestMapping(value = "/politicians", method = RequestMethod.GET)
public class PoliticianController extends AbstractBaseController {

    private LegislatorCondenseService legislatorCondenseService;

    private static final Logger logger = Logger.getLogger(PoliticianController.class);


    /**
     * Politician index returns condensed legislator objects
     *
     * @return
     */
    @RequestMapping(value = "")
    public @ResponseBody List<LegislatorCondensed> getRoot() {

        return legislatorCondenseService.getCondensedLegislators();
    }

    /**
     * Politician index returns condensed legislator objects in given time range
     *
     * @return
     */
    @RequestMapping(value = "", params={"start, end"})
    public @ResponseBody List<LegislatorCondensed> getRootByStartAndEnd (
            @RequestParam(value = "start") @DateTimeFormat(pattern = "MM-dd-yyyy") Date start,
            @RequestParam(value = "end") @DateTimeFormat(pattern = "MM-dd-yyyy") Date end) {

        return legislatorCondenseService.getCondensedLegislators(start.getTime()/1000L, end.getTime()/1000L);
    }


    @Autowired
    public void setLegislatorCondenseService(LegislatorCondenseService legislatorCondenseService) {
        this.legislatorCondenseService = legislatorCondenseService;
    }
}
