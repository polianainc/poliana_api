package com.poliana.bills.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
@Document(collection = "bill_votes")
public class BillVotes {

    @Id
    private String id;

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
    private String chamber;
    private int year;
    private int month;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
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

        String chamber = this.getChamber();
        if (null != embeddedList) {
            String[] voteStrings = embeddedList.split("\u0002");
            int len = voteStrings.length;
            List<BillVote> votes = new ArrayList<>(len);
            for(int i = 0; i < len; i++) {
                BillVote vote = new BillVote();
                String[] voteString = voteStrings[i].split(",");

                vote.setBioguideId(voteString[0]);
                int j = 1;
                String i1 = voteString[j];
                if (i1.contains(".") || i1.contains(" ")) {
                    vote.setDisplayName(i1);
                    j++;
                }
                vote.setFirstName(voteString[j++]);
                vote.setLastName(voteString[j++]);
                vote.setParty(voteString[j++]);
                vote.setState(voteString[j++]);
                System.out.println(voteString[0] + voteString[1] + voteString[2] + voteString[3] + voteString[4] + voteString[5]);
                if (null != this.chamber && chamber.equals("h"))
                    vote.setDistrict(new Integer(voteString[j++]).intValue());

                votes.add(vote);
            }
            return votes;
        }
        else
            return null;
    }
}
