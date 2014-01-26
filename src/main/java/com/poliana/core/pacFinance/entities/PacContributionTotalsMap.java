package com.poliana.core.pacFinance.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.util.HashMap;

/**
 * @author David Gilmore
 * @date 1/26/14
 */
@Entity("pac_contribution_totals")
@JsonIgnoreProperties({"id"})
public class PacContributionTotalsMap {

    @Id
    private String id;

    @Property("pac_id")
    private String pacId;

    @Property("pac_name")
    private String pacName;

    private String chamber;

    @Property("begin_timestamp")
    private Long beginTimestamp;

    @Property("end_timestamp")
    private Long endTimestamp;

    private Integer congress;

    @Embedded
    private HashMap<String, Integer> sums;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public Long getBeginTimestamp() {
        return beginTimestamp;
    }

    public void setBeginTimestamp(Long beginTimestamp) {
        this.beginTimestamp = beginTimestamp;
    }

    public Long getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public Integer getCongress() {
        return congress;
    }

    public void setCongress(Integer congress) {
        this.congress = congress;
    }

    public HashMap<String, Integer> getSums() {
        return sums;
    }

    public void setSums(HashMap<String, Integer> sums) {
        this.sums = sums;
    }
}
