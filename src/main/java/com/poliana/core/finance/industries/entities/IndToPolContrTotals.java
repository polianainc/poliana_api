package com.poliana.core.finance.industries.entities;

/**
 * @author David Gilmore
 * @date 11/1/13
 */
public class IndToPolContrTotals {

    private String bioguideId;
    private String party;
    private String industryId;
    private int contributionsCount;
    private int contributionSum;
    private int timestamp;
    private int year;
    private int month;

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

    public int getContributionSum() {
        return contributionSum;
    }

    public void setContributionSum(int contributionSum) {
        this.contributionSum = contributionSum;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
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
