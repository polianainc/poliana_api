package com.poliana.legislation.entities.deprecated;


public class BillAction {
    private String billId;
    private String actedAt;
    private String committee;
    private String how;
    private String roll;
    private String status;
    private String text;
    private String type;
    private String voteType;
    private String location;
    private String congress;
    private String billType;

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getActedAt() {
        return actedAt;
    }

    public void setActedAt(String actedAt) {
        this.actedAt = actedAt;
    }

    public String getCommittee() {
        return committee;
    }

    public void setCommittee(String committee) {
        this.committee = committee;
    }

    public String getHow() {
        return how;
    }

    public void setHow(String how) {
        this.how = how;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVoteType() {
        return voteType;
    }

    public void setVoteType(String voteType) {
        this.voteType = voteType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCongress() {
        return congress;
    }

    public void setCongress(String congress) {
        this.congress = congress;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }
}
