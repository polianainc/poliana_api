package com.poliana.contributions.entities;

public class FecContribution {
    private String id;
    private String importReferenceId;
    private String cycle;
    private String transactionNamespace;
    private String transactionId;
    private String transactionType;
    private String filingId;
    private String isAmendment;
    private double amount;
    private String dates;
    private String contributorName;
    private String contributorExtId;
    private String contributorType;
    private String contributorOccupation;
    private String contributorEmployer;
    private String contributorGender;
    private String contributorAddress;
    private String contributorCity;
    private String contributorState;
    private String contributorZipcode;
    private String contributorCategory;
    private String organizationName;
    private String organizationExtId;
    private String parentOrganizationName;
    private String parentOrganizationExtId;
    private String recipientName;
    private String recipientExtId;
    private String recipientParty;
    private String recipientType;
    private String recipientState;
    private String recipientStateHeld;
    private String recipientCategory;
    private String committeeName;
    private String committeeExtId;
    private String committeeParty;
    private String candidacyStatus;
    private String district;
    private String districtHeld;
    private String seat;
    private String seatHeld;
    private String seatStatus;
    private String seatResult;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImportReferenceId() {
        return importReferenceId;
    }

    public void setImportReferenceId(String importReferenceId) {
        this.importReferenceId = importReferenceId;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getTransactionNamespace() {
        return transactionNamespace;
    }

    public void setTransactionNamespace(String transactionNamespace) {
        this.transactionNamespace = transactionNamespace;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getFilingId() {
        return filingId;
    }

    public void setFilingId(String filingId) {
        this.filingId = filingId;
    }

    public String getAmendment() {
        return isAmendment;
    }

    public void setAmendment(String amendment) {
        isAmendment = amendment;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        try {
            this.amount = new Double(amount).doubleValue();
        }
        catch (Exception e) {
            this.amount = 0;
        }
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getContributorName() {
        return contributorName;
    }

    public void setContributorName(String contributorName) {
        this.contributorName = contributorName;
    }

    public String getContributorExtId() {
        return contributorExtId;
    }

    public void setContributorExtId(String contributorExtId) {
        this.contributorExtId = contributorExtId;
    }

    public String getContributorType() {
        return contributorType;
    }

    public void setContributorType(String contributorType) {
        this.contributorType = contributorType;
    }

    public String getContributorOccupation() {
        return contributorOccupation;
    }

    public void setContributorOccupation(String contributorOccupation) {
        this.contributorOccupation = contributorOccupation;
    }

    public String getContributorEmployer() {
        return contributorEmployer;
    }

    public void setContributorEmployer(String contributorEmployer) {
        this.contributorEmployer = contributorEmployer;
    }

    public String getContributorGender() {
        return contributorGender;
    }

    public void setContributorGender(String contributorGender) {
        this.contributorGender = contributorGender;
    }

    public String getContributorAddress() {
        return contributorAddress;
    }

    public void setContributorAddress(String contributorAddress) {
        this.contributorAddress = contributorAddress;
    }

    public String getContributorCity() {
        return contributorCity;
    }

    public void setContributorCity(String contributorCity) {
        this.contributorCity = contributorCity;
    }

    public String getContributorState() {
        return contributorState;
    }

    public void setContributorState(String contributorState) {
        this.contributorState = contributorState;
    }

    public String getContributorZipcode() {
        return contributorZipcode;
    }

    public void setContributorZipcode(String contributorZipcode) {
        this.contributorZipcode = contributorZipcode;
    }

    public String getContributorCategory() {
        return contributorCategory;
    }

    public void setContributorCategory(String contributorCategory) {
        this.contributorCategory = contributorCategory;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationExtId() {
        return organizationExtId;
    }

    public void setOrganizationExtId(String organizationExtId) {
        this.organizationExtId = organizationExtId;
    }

    public String getParentOrganizationName() {
        return parentOrganizationName;
    }

    public void setParentOrganizationName(String parentOrganizationName) {
        this.parentOrganizationName = parentOrganizationName;
    }

    public String getParentOrganizationExtId() {
        return parentOrganizationExtId;
    }

    public void setParentOrganizationExtId(String parentOrganizationExtId) {
        this.parentOrganizationExtId = parentOrganizationExtId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientExtId() {
        return recipientExtId;
    }

    public void setRecipientExtId(String recipientExtId) {
        this.recipientExtId = recipientExtId;
    }

    public String getRecipientParty() {
        return recipientParty;
    }

    public void setRecipientParty(String recipientParty) {
        this.recipientParty = recipientParty;
    }

    public String getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(String recipientType) {
        this.recipientType = recipientType;
    }

    public String getRecipientState() {
        return recipientState;
    }

    public void setRecipientState(String recipientState) {
        this.recipientState = recipientState;
    }

    public String getRecipientStateHeld() {
        return recipientStateHeld;
    }

    public void setRecipientStateHeld(String recipientStateHeld) {
        this.recipientStateHeld = recipientStateHeld;
    }

    public String getRecipientCategory() {
        return recipientCategory;
    }

    public void setRecipientCategory(String recipientCategory) {
        this.recipientCategory = recipientCategory;
    }

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }

    public String getCommitteeExtId() {
        return committeeExtId;
    }

    public void setCommitteeExtId(String committeeExtId) {
        this.committeeExtId = committeeExtId;
    }

    public String getCommitteeParty() {
        return committeeParty;
    }

    public void setCommitteeParty(String committeeParty) {
        this.committeeParty = committeeParty;
    }

    public String getCandidacyStatus() {
        return candidacyStatus;
    }

    public void setCandidacyStatus(String candidacyStatus) {
        this.candidacyStatus = candidacyStatus;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrictHeld() {
        return districtHeld;
    }

    public void setDistrictHeld(String districtHeld) {
        this.districtHeld = districtHeld;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getSeatHeld() {
        return seatHeld;
    }

    public void setSeatHeld(String seatHeld) {
        this.seatHeld = seatHeld;
    }

    public String getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(String seatStatus) {
        this.seatStatus = seatStatus;
    }

    public String getSeatResult() {
        return seatResult;
    }

    public void setSeatResult(String seatResult) {
        this.seatResult = seatResult;
    }
}
