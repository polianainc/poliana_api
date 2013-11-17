package com.poliana.bills.services;

import com.poliana.bills.entities.BillAction;
import com.poliana.bills.models.Vote;
import com.poliana.bills.repositories.BillHadoopRepo;
import com.poliana.bills.repositories.VotesCRUDRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class BillService {
    @Autowired
    private BillHadoopRepo billHadoopRepo;
    @Autowired
    private VotesCRUDRepo votesCRUDRepo;

    private HashMap<String,Vote> voteHashMap;

    public List<BillAction> billActions(String billId) {
        return billHadoopRepo.billActions(billId);
    }

    public Vote getVoteByVoteId(String voteId) {
        if(voteHashMap == null)
            setVoteHashMap();
        return voteHashMap.get(voteId);
    }

    public void setVoteHashMap() {
        List<Vote> votes = votesCRUDRepo.findAll();
        System.out.println("Fetching votes");
        voteHashMap = new HashMap<String,Vote>(votes.size());
        for (Vote vote: votes) {
            System.out.print(".");
            voteHashMap.put(vote.getVoteId(),vote);
        }
    }

}
