package com.poliana.legislation.services;

import com.poliana.entities.entities.Legislator;
import com.poliana.entities.services.LegislatorService;
import com.poliana.legislation.entities.deprecated.BillAction;
import com.poliana.legislation.entities.govtrack.votes.Voter;
import com.poliana.legislation.repositories.BillHadoopRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class LegislationService {

    @Autowired
    private BillHadoopRepo billHadoopRepo;

    @Autowired
    private LegislatorService legislatorService;

    public List<BillAction> billActions(String billId) {
        return billHadoopRepo.billActions(billId);
    }

    public List<Legislator> bioguideToLegislator(List<String> bioguideIds, int timeStamp) {

        List<Legislator> legislators = new LinkedList<>();

        try{
            for (String bioguideId: bioguideIds) {
                Legislator legislator =
                        legislatorService.legislatorByIdTimestamp(bioguideId, timeStamp);
                legislators.add(legislator);
            }
        }
        catch (NullPointerException e) {}

        return legislators;
    }

    public List<Legislator> gtVoterToLegislator(List<Voter> votersGt, int timeStamp) {
        List<Legislator> legislators = new LinkedList<>();

        try {
            for (Voter voter : votersGt) {
                Legislator legislator =
                        legislatorService.legislatorByIdTimestamp(voter.getPoliticianId(), timeStamp);
                legislators.add(legislator);
            }
        }
        catch (NullPointerException e) {}

        return legislators;
    }

    public int getTimestamp(String dateString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            Date date = formatter.parse(dateString);
            long time = date.getTime();
            return (int) (time/1000L);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
