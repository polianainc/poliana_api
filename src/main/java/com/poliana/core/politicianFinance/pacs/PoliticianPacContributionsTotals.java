package com.poliana.core.politicianFinance.pacs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

/**
 * @author David Gilmore
 * @date 1/26/14
 */
@Entity("pac_to_politician_contribution_totals")
@JsonIgnoreProperties({"id"})
public class PoliticianPacContributionsTotals {

    @Id
    private String id;

    @Property("bioguide_id")
    private String bioguideId;

    @Property("first_name")
    private String firstName;

    @Property("last_name")
    private String lastName;

    private String party;
    private String religion;

    @Property("pac_id")
    private String pacId;

    @Property("pac_name")
    private String pacName;

    @Property("contribution_count")
    private Integer contributionCount;

    @Property("contribution_sum")
    private Integer contributionSum;

    @Property("begin_timestamp")
    private Long beginTimestamp;

    @Property("end_timestamp")
    private Long endTimestamp;

    private Integer congress;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Integer getContributionCount() {
        return contributionCount;
    }

    public void setContributionCount(Integer contributionCount) {
        this.contributionCount = contributionCount;
    }

    public Integer getContributionSum() {
        return contributionSum;
    }

    public void setContributionSum(Integer contributionSum) {
        this.contributionSum = contributionSum;
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
}
