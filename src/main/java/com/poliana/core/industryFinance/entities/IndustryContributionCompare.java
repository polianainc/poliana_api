package com.poliana.core.industryFinance.entities;

/**
 * @author David Gilmore
 * @date 1/10/14
 */
public class IndustryContributionCompare {

    private String industry;
    private String legislator;
    private String party;
    private String religion;
    private double amount;
    private double compare1;
    private String compare1Metric;
    private double compare2;
    private String compare2Metric;

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getLegislator() {
        return legislator;
    }

    public void setLegislator(String legislator) {
        this.legislator = legislator;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getCompare1() {
        return compare1;
    }

    public void setCompare1(double compare1) {
        this.compare1 = compare1;
    }

    public String getCompare1Metric() {
        return compare1Metric;
    }

    public void setCompare1Metric(String compare1Metric) {
        this.compare1Metric = compare1Metric;
    }

    public double getCompare2() {
        return compare2;
    }

    public void setCompare2(double compare2) {
        this.compare2 = compare2;
    }

    public String getCompare2Metric() {
        return compare2Metric;
    }

    public void setCompare2Metric(String compare2Metric) {
        this.compare2Metric = compare2Metric;
    }
}
