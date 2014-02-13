package com.poliana.web.common.jobs.streaming;

import com.poliana.core.common.streaming.CrpStreamingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author David Gilmore
 * @date 2/12/14
 */
@Controller
@RequestMapping("/admin/")
public class CrpStreamingController {

    private CrpStreamingService crpStreamingService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getFilesToBeSynced() {

        return "views/endpoints";
    }

    @Autowired
    public void setCrpStreamingService(CrpStreamingService crpStreamingService) {
        this.crpStreamingService = crpStreamingService;
    }
}
