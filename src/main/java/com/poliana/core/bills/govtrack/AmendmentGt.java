package com.poliana.core.bills.govtrack;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import com.google.gson.annotations.Expose;
import com.poliana.core.amendments.entities.AmendmentRef;
import com.poliana.core.amendments.entities.BillRef;
import com.poliana.core.amendments.entities.TreatyRef;
import com.poliana.core.bills.entities.Action;
import com.poliana.core.bills.govtrack.bills.Sponsor;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author David Gilmore
 * @date 11/22/13
 */
@Entity("amendments_govtrack")
public class AmendmentGt {

    @Id
    @Expose
    private ObjectId id;

    @Embedded
    private List<Action> actionGts;
    @Property("amendment_id")
    private String amendmentId;
    @Property("amendment_type")
    private String amendmentType;
    @Property("amends_amendment")
    private String amendsAmendment;
    @Embedded("amends_bill")
    private BillRef amendsBill;
    @Embedded("amends_treaty")
    private TreatyRef treatyRef;
    @Embedded("amends_amendment")
    private AmendmentRef amendmentRef;
    private String chamber;
    private int congress;
    private String description;
    @Property("house_number")
    private int houseNumber;
    @Property("introduced_at")
    private String introducedAt;
    private int number;
    private String purpose;
    private Sponsor sponsor;
    private String status;
    @Property("status_at")
    private String statusAt;
    @Property("updated_at")
    private String updatedAt;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public List<Action> getActionGts() {
        return actionGts;
    }

    public void setActionGts(List<Action> actionGts) {
        this.actionGts = actionGts;
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

    public String getAmendsAmendment() {
        return amendsAmendment;
    }

    public void setAmendsAmendment(String amendsAmendment) {
        this.amendsAmendment = amendsAmendment;
    }

    public BillRef getAmendsBill() {
        return amendsBill;
    }

    public void setAmendsBill(BillRef amendsBill) {
        this.amendsBill = amendsBill;
    }

    public TreatyRef getTreatyRef() {
        return treatyRef;
    }

    public void setTreatyRef(TreatyRef treatyRef) {
        this.treatyRef = treatyRef;
    }

    public AmendmentRef getAmendmentRef() {
        return amendmentRef;
    }

    public void setAmendmentRef(AmendmentRef amendmentRef) {
        this.amendmentRef = amendmentRef;
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

    public void setCongress(String congress) {
        this.congress = Integer.getInteger(congress).intValue();
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

    public String getIntroducedAt() {
        return introducedAt;
    }

    public void setIntroducedAt(String introducedAt) {
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

    public String getStatusAt() {
        return statusAt;
    }

    public void setStatusAt(String statusAt) {
        this.statusAt = statusAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
