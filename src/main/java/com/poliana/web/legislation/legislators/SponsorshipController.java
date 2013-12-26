package com.poliana.web.legislation.legislators;

import com.poliana.core.legislators.Legislator;
import com.poliana.core.ideology.IdeologyMatrix;
import com.poliana.core.ideology.IdeologyAnalysis;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
            ideology.put("begin_timestamp",ideologyMatrix.getBeginDate());
            ideology.put("end_timestamp",ideologyMatrix.getEndDate());
            ideology.put("sponsorship_matrix",ideologyMatrix.getSponsorshipMatrix());
            ideology.put("id_to_index",ideologyMatrix.getIdToIndex());
            ideology.put("legislators",legislatorsFormat(ideologyMatrix.getLegislators()));
        } catch (JSONException e) {}

        return ideology.toString();
    }


    private JSONArray legislatorsFormat(List<Legislator> legislatorsList) {

        JSONArray legislatorsJson = new JSONArray();
        for (Legislator legislator: legislatorsList) {
            JSONObject jsonLegislator = new JSONObject();
            try {
                jsonLegislator.put("bioguide_id", legislator.getBioguideId());
                jsonLegislator.put("name", legislator.getFirstName() + " " + legislator.getLastName());
                jsonLegislator.put("party", legislator.getParty());
                jsonLegislator.put("religion", legislator.getReligion());
                jsonLegislator.put("index", legislator.getIndex());
            } catch (JSONException e) {}
            legislatorsJson.put(jsonLegislator);
        }
        return legislatorsJson;
    }
}
