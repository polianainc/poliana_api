package com.poliana.core.ideology;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;

/**
 * @author David Gilmore
 * @date 12/31/13
 */
@Entity("legislator_ideology")
public class LegislatorIdeology {

    @Id
    private String id;

    @Indexed
    @Property("bioguide_id")
    private String bioguideId;
    private String name;

    @Indexed
    private int congress;

    @Indexed
    @Property("begin_timestamp")
    private int beginTimestamp;

    @Indexed
    @Property("end_timestamp")
    private int endTimestamp;

    private double ideology;
    private int index;
    private String chamber;
    private String party;
    private String religion;
    private String metric;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCongress() {
        return congress;
    }

    public void setCongress(int congress) {
        this.congress = congress;
    }

    public int getBeginTimestamp() {
        return beginTimestamp;
    }

    public void setBeginTimestamp(int beginTimestamp) {
        this.beginTimestamp = beginTimestamp;
    }

    public int getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(int endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public double getIdeology() {
        return ideology;
    }

    public void setIdeology(double ideology) {
        this.ideology = ideology;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }
}
