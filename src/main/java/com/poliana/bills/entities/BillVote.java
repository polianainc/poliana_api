package com.poliana.bills.entities;

public class BillVote {
    private String billId;
    private String sponsorId;
    private String result;
    private String congress;
    private String inttroducedOn;
    private String bioguideId;
    private String vote;

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(String sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCongress() {
        return congress;
    }

    public void setCongress(String congress) {
        this.congress = congress;
    }

    public String getInttroducedOn() {
        return inttroducedOn;
    }

    public void setIntroducedOn(String inttroducedOn) {
        this.inttroducedOn = inttroducedOn;
    }

    public String getBioguideId() {
        return bioguideId;
    }

    public void setBioguideId(String bioguideId) {
        this.bioguideId = bioguideId;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }
}
