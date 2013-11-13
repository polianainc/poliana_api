package com.poliana.campaignFinance.entities;

import org.springframework.data.annotation.Id;

/**
 * @author David Gilmore
 * @date 11/1/13
 */
public class IndToPolContrTotals {

    @Id
    private String id;
    private String bioguideId;
    private String party;
    private String industryId;
    private int contributionsCount;
    private int contributionTotal;
    private int year;
    private int month;

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

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public int getContributionsCount() {
        return contributionsCount;
    }

    public void setContributionsCount(int contributionsCount) {
        this.contributionsCount = contributionsCount;
    }

    public int getContributionTotal() {
        return contributionTotal;
    }

    public void setContributionTotal(int contributionTotal) {
        this.contributionTotal = contributionTotal;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
