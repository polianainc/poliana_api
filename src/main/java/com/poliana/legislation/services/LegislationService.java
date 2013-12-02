package com.poliana.legislation.services;

import com.poliana.entities.entities.Legislator;
import com.poliana.entities.services.LegislatorService;
import com.poliana.legislation.entities.Sponsorship;
import com.poliana.legislation.entities.deprecated.BillAction;
import com.poliana.legislation.entities.govtrack.votes.Voter;
import com.poliana.legislation.repositories.BillHadoopRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class LegislationService {

    @Autowired
    private BillHadoopRepo billHadoopRepo;

    @Autowired
    private LegislatorService legislatorService;

    public void ideologyAnalysis(String chamber, int congress) {
        List<Sponsorship> sponsorships = billHadoopRepo.getSponsorships(chamber,congress);

    }

    public List<BillAction> billActions(String billId) {
        return billHadoopRepo.billActions(billId);
    }

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

}
