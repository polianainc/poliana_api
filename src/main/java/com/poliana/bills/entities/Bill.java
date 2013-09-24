package com.poliana.bills.entities;

import java.util.List;

public class Bill
{
    private String id;

    private String billType;
    private String shortTitle;
    private String popularTitle;
    private String officialTitle;
    private int congress;
    private int number;
    private String sponsor;
    private String enactedAs;
    private String introducedAt;
    private String status;
    private String statusAt;
    private List<String> subjects;
    private String topSubject;
    private BillSummary summary;
    private BillTitle title;
    private String updatedAt;
    private List<String> relatedBills;
    private List<BillAction> billActions;
    private List<BillAmendment> billAmendments;
    private List<BillCoSponsor> billCoSponsors;
    private List<BillCommittee> billCommittees;
    private List<BillVote> billVotes;
    private BillHistory billHistory;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getPopularTitle() {
        return popularTitle;
    }

    public void setPopularTitle(String popularTitle) {
        this.popularTitle = popularTitle;
    }

    public String getOfficialTitle() {
        return officialTitle;
    }

    public void setOfficialTitle(String officialTitle) {
        this.officialTitle = officialTitle;
    }

    public int getCongress() {
        return congress;
    }

    public void setCongress(int congress) {
        this.congress = congress;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getEnactedAs() {
        return enactedAs;
    }

    public void setEnactedAs(String enactedAs) {
        this.enactedAs = enactedAs;
    }

    public String getIntroducedAt() {
        return introducedAt;
    }

    public void setIntroducedAt(String introducedAt) {
        this.introducedAt = introducedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusAt() {
        return statusAt;
    }

    public void setStatusAt(String statusAt) {
        this.statusAt = statusAt;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public String getTopSubject() {
        return topSubject;
    }

    public void setTopSubject(String topSubject) {
        this.topSubject = topSubject;
    }

    public BillSummary getSummary() {
        return summary;
    }

    public void setSummary(BillSummary summary) {
        this.summary = summary;
    }

    public BillTitle getTitle() {
        return title;
    }

    public void setTitle(BillTitle title) {
        this.title = title;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getRelatedBills() {
        return relatedBills;
    }

    public void setRelatedBills(List<String> relatedBills) {
        this.relatedBills = relatedBills;
    }

    public List<BillAction> getBillActions() {
        return billActions;
    }

    public void setBillActions(List<BillAction> billActions) {
        this.billActions = billActions;
    }

    public List<BillAmendment> getBillAmendments() {
        return billAmendments;
    }

    public void setBillAmendments(List<BillAmendment> billAmendments) {
        this.billAmendments = billAmendments;
    }

    public List<BillCoSponsor> getBillCoSponsors() {
        return billCoSponsors;
    }

    public void setBillCoSponsors(List<BillCoSponsor> billCoSponsors) {
        this.billCoSponsors = billCoSponsors;
    }

    public List<BillCommittee> getBillCommittees() {
        return billCommittees;
    }

    public void setBillCommittees(List<BillCommittee> billCommittees) {
        this.billCommittees = billCommittees;
    }

    public List<BillVote> getBillVotes() {
        return billVotes;
    }

    public void setBillVotes(List<BillVote> billVotes) {
        this.billVotes = billVotes;
    }

    public BillHistory getBillHistory() {
        return billHistory;
    }

    public void setBillHistory(BillHistory billHistory) {
        this.billHistory = billHistory;
    }
}