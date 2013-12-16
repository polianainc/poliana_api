package com.poliana.web.legislation.legislators;

import com.poliana.core.entities.entities.Legislator;
import com.poliana.core.legislation.models.IdeologyMatrix;
import com.poliana.core.legislation.services.IdeologyAnalysis;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author David Gilmore
 * @date 12/15/13
 */
@Controller
@RequestMapping("/legislators/")
public class SponsorshipController {

    @Autowired
    private IdeologyAnalysis ideologyAnalysis;

    @RequestMapping(value="sponsorship-matrix/{chamber}/{begin}/{end}", headers="Accept=*/*",
            method = RequestMethod.GET)
    public @ResponseBody String ideology(
            @PathVariable("chamber") String chamber,
            @PathVariable("begin") Integer begin,
            @PathVariable("end") Integer end) {

        IdeologyMatrix ideologyMatrix = ideologyAnalysis.getSponsorshipMatrix(chamber,begin,end);

        JSONObject ideology = new JSONObject();

        try {
            ideology.put("chamber",ideologyMatrix.getChamber());
            ideology.put("beginTimestamp",ideologyMatrix.getBeginDate());
            ideology.put("endTimestamp",ideologyMatrix.getEndDate());
            ideology.put("sponsorshipMatrix",ideologyMatrix.getSponsorshipMatrix());
            ideology.put("idToIndex",ideologyMatrix.getIdToIndex());

            JSONArray legislators = new JSONArray();
            for (Legislator legislator: ideologyMatrix.getLegislators()) {
                legislators.put(new JSONObject(legislator));
            }
            ideology.put("legislators",legislators);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return ideology.toString();
    }

}
