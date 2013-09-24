package com.poliana.contributions.entities;

public class IndustryContrTotals {

    private String industryId;
    private int totalYea;
    private int totalNay;
    private int distinctYea;
    private int distinctNay;
    private int sumYea;
    private int sumNay;
    private int totalDiff;
    private int distinctDiff;
    private int sumDiff;

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public int getTotalYea() {
        return totalYea;
    }

    public void setTotalYea(int totalYea) {
        this.totalYea = totalYea;
    }

    public int getTotalNay() {
        return totalNay;
    }

    public void setTotalNay(int totalNay) {
        this.totalNay = totalNay;
    }

    public int getDistinctYea() {
        return distinctYea;
    }

    public void setDistinctYea(int distinctYea) {
        this.distinctYea = distinctYea;
    }

    public int getDistinctNay() {
        return distinctNay;
    }

    public void setDistinctNay(int distinctNay) {
        this.distinctNay = distinctNay;
    }

    public int getSumYea() {
        return sumYea;
    }

    public void setSumYea(int sumYea) {
        this.sumYea = sumYea;
    }

    public int getSumNay() {
        return sumNay;
    }

    public void setSumNay(int sumNay) {
        this.sumNay = sumNay;
    }
    public int getTotalDiff() {
        return totalDiff;
    }

    public void setTotalDiff() {
        this.totalDiff = totalYea - totalNay;
    }

    public int getDistinctDiff() {
        return distinctDiff;
    }

    public void setDistinctDiff() {
        this.distinctDiff = distinctYea - distinctNay;
    }

    public int getSumDiff() {
        return sumDiff;
    }

    public void setSumDiff() {
        this.sumDiff = sumYea - sumNay;
    }


    public String toString() {
        return "" +
        "industryId: " + industryId + "\n" +
        "totalYea: " + totalYea + "\n" +
        "distinctYea: " + distinctYea + "\n" +
        "distinctNay: " + distinctNay + "\n" +
        "sumYea: " + sumYea + "\n" +
        "sumNay: " + sumNay + "\n" +
        "totalDiff" + totalDiff + "\n" +
        "distinctDiff" + distinctDiff + "\n" +
        "sumDiff" + sumDiff;
    }

}
