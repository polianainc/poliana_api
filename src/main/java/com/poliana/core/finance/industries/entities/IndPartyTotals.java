package com.poliana.core.finance.industries.entities;

/**
 * @author David Gilmore
 * @date 11/12/13
 */
public class IndPartyTotals {
    private String industryId;
    private int count;
    private int sum;
    private int average;
    private int year;
    private int month;

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }

    public void setAverage(int sum, int count) {
        this.average = sum/count;
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
