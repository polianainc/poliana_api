package com.poliana.core.industryFinance.entities;

/**
 * @author David Gilmore
 * @date 11/1/13
 */
public class IndToPolContrTotals {

    private String bioguideId;
    private String party;
    private String industryId;
    private String industryName;
    private String sector;
    private String sectorLong;
    private int contributionsCount;
    private int contributionSum;
    private int timestamp;
    private int cycle;
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

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getSectorLong() {
        return sectorLong;
    }

    public void setSectorLong(String sectorLong) {
        this.sectorLong = sectorLong;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
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

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
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
