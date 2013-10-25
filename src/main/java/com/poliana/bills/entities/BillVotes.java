package com.poliana.bills.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
@Document(collection = "bill_votes")
public class BillVotes {

    @Id
    private String voteId;

    private String billId;
    private int yeaTotal;
    private int nayTotal;
    private int notVotingTotal;
    private int presentTotal;
    private List<BillVote> yeas;
    private List<BillVote> nays;
    private List<BillVote> notVoting;
    private List<BillVote> present;
    private int year;
    private int month;

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public int getYeaTotal() {
        return yeaTotal;
    }

    public void setYeaTotal(int yeaTotal) {
        this.yeaTotal = yeaTotal;
    }

    public int getNayTotal() {
        return nayTotal;
    }

    public void setNayTotal(int nayTotal) {
        this.nayTotal = nayTotal;
    }

    public int getNotVotingTotal() {
        return notVotingTotal;
    }

    public void setNotVotingTotal(int notVotingTotal) {
        this.notVotingTotal = notVotingTotal;
    }

    public int getPresentTotal() {
        return presentTotal;
    }

    public void setPresentTotal(int presentTotal) {
        this.presentTotal = presentTotal;
    }

    public List<BillVote> getYeas() {
        return yeas;
    }

    public void setYeas(String yeas) {
        this.yeas = explodeVotes(yeas);
    }

    public void setYeas(List<BillVote> yeas) {
        this.yeas = yeas;
    }

    public List<BillVote> getNays() {
        return nays;
    }

    public void setNays(String nays) {
        this.nays = explodeVotes(nays);
    }

    public void setNays(List<BillVote> nays) {
        this.nays = nays;
    }

    public List<BillVote> getNotVoting() {
        return notVoting;
    }

    public void setNotVoting(String notVoting) {
        this.notVoting = explodeVotes(notVoting);
    }

    public void setNotVoting(List<BillVote> notVoting) {
        this.notVoting = notVoting;
    }

    public List<BillVote> getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = explodeVotes(present);
    }

    public void setPresent(List<BillVote> present) {
        this.present = present;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    private List<BillVote> explodeVotes(String embeddedList) {
        if (null != embeddedList) {
            String[] voteStrings = embeddedList.split("\u0002");
            int len = voteStrings.length;
            List<BillVote> votes = new ArrayList<>(len);
            for(int i = 0; i < len; i++) {
                BillVote vote = new BillVote();
                String[] voteString = voteStrings[i].split(",");

                vote.setBioguideId(voteString[0]);
                vote.setDisplayName(voteString[1]);
                vote.setFirstName(voteString[2]);
                vote.setLastName(voteString[3]);
                vote.setParty(voteString[4]);
                vote.setState(voteString[5]);

                votes.add(vote);
            }
            return votes;
        }
        else
            return null;
    }
}
