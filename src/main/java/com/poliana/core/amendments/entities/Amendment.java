package com.poliana.core.amendments.entities;

import org.mongodb.morphia.annotations.*;
import com.poliana.core.bills.entities.Action;
import com.poliana.core.bills.entities.Sponsor;

import java.util.List;

/**
 * @author David Gilmore
 * @date 11/22/13
 */
@Entity("amendments")
public class Amendment {

    @Id
    private String id;

    @Embedded
    private List<Action> actionGts;

    @Property("amendment_id")
    private String amendmentId;

    @Property("amendment_type")
    private String amendmentType;

    @Reference("amends_amendment")
    private Amendment amendsAmendment;

    @Embedded("bill_reference")
    private AmendmentBillReference amendmentBillReference;

    @Embedded("amends_treaty")
    private TreatyRef treatyRef;

    @Embedded("amends_amendment")
    private AmendmentReference amendmentReference;

    private String chamber;
    private int congress;
    private String description;

    @Property("house_number")
    private int houseNumber;

    @Property("introduced_at")
    private int introducedAt;

    private int number;
    private String purpose;
    private Sponsor sponsor;
    private String status;

    @Property("status_at")
    private int statusAt;

    @Property("updated_at")
    private int updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Action> getActionGts() {
        return actionGts;
    }

    public void setActionGts(List<Action> actionGts) {
        this.actionGts = actionGts;
    }

    public AmendmentBillReference getAmendmentBillReference() {
        return amendmentBillReference;
    }

    public void setAmendmentBillReference(AmendmentBillReference amendmentBillReference) {
        this.amendmentBillReference = amendmentBillReference;
    }

    public AmendmentReference getAmendmentReference() {
        return amendmentReference;
    }

    public void setAmendmentReference(AmendmentReference amendmentReference) {
        this.amendmentReference = amendmentReference;
    }

    public String getAmendmentId() {
        return amendmentId;
    }

    public void setAmendmentId(String amendmentId) {
        this.amendmentId = amendmentId;
    }

    public String getAmendmentType() {
        return amendmentType;
    }

    public void setAmendmentType(String amendmentType) {
        this.amendmentType = amendmentType;
    }

    public Amendment getAmendsAmendment() {
        return amendsAmendment;
    }

    public void setAmendsAmendment(Amendment amendsAmendment) {
        this.amendsAmendment = amendsAmendment;
    }

    public TreatyRef getTreatyRef() {
        return treatyRef;
    }

    public void setTreatyRef(TreatyRef treatyRef) {
        this.treatyRef = treatyRef;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public int getCongress() {
        return congress;
    }

    public void setCongress(int congress) {
        this.congress = congress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getIntroducedAt() {
        return introducedAt;
    }

    public void setIntroducedAt(int introducedAt) {
        this.introducedAt = introducedAt;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusAt() {
        return statusAt;
    }

    public void setStatusAt(int statusAt) {
        this.statusAt = statusAt;
    }

    public int getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(int updatedAt) {
        this.updatedAt = updatedAt;
    }
}
