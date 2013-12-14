package com.poliana.core.legislation.services;

import com.poliana.core.entities.entities.Legislator;
import com.poliana.core.entities.services.LegislatorService;
import com.poliana.core.legislation.entities.Sponsorship;
import com.poliana.core.legislation.entities.deprecated.BillAction;
import com.poliana.core.legislation.entities.govtrack.votes.Voter;
import com.poliana.core.legislation.models.IdeologyMatrix;
import com.poliana.core.legislation.repositories.BillHadoopRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Service
public class LegislationService {

    @Autowired
    private LegislatorService legislatorService;

    public List<Legislator> bioguideToLegislator(List<String> bioguideIds, int timestamp) {

        List<Legislator> legislators = new LinkedList<>();

        try{
            for (String bioguideId: bioguideIds) {
                Legislator legislator =
                        legislatorService.legislatorByIdTimestamp(bioguideId, timestamp);
                legislators.add(legislator);
            }
        }
        catch (NullPointerException e) {}

        return legislators;
    }

    public List<Legislator> gtVotersToLegislators(List<Voter> votersGt, int timestamp) {
        List<Legislator> legislators = new LinkedList<>();

        try {
            for (Voter voter : votersGt) {
                Legislator legislator =
                        legislatorService.legislatorByIdTimestamp(voter.getPoliticianId(), timestamp);
                legislators.add(legislator);
            }
        }
        catch (NullPointerException e) {}

        return legislators;
    }

    public List<Legislator> legislatorList(Iterator<Legislator> legislatorIterator) {
        List<Legislator> legislatorList = new LinkedList<>();
        while(legislatorIterator.hasNext()) {
            Legislator legislator = legislatorIterator.next();
            legislatorList.add(legislator);
        }
        return legislatorList;
    }

}
