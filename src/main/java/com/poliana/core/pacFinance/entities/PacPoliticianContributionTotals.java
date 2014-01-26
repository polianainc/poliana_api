package com.poliana.core.pacFinance.entities;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import com.google.gson.annotations.Expose;

/**
 * @author David Gilmore
 * @date 12/19/13
 */
@Entity("pac_to_politician_contribution_sums")
public class PacPoliticianContributionTotals {

    @Id
    private transient String id;

    @Property("bioguide_id")
    private String bioguideId;

    @Property("pac_id")
    private String pacId;

    @Property("pac_name")
    private String pacName;

    private int cycle;
    private int amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBioguideId() {
        return bioguideId;
    }

    public void setBioguideId(String bioguideId) {
        this.bioguideId = bioguideId;
    }

    public String getPacId() {
        return pacId;
    }

    public void setPacId(String pacId) {
        this.pacId = pacId;
    }

    public String getPacName() {
        return pacName;
    }

    public void setPacName(String pacName) {
        this.pacName = pacName;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
