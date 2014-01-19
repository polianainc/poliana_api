package com.poliana.core.industryFinance.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import com.google.gson.annotations.Expose;

import java.util.HashMap;
import java.util.List;

/**
 * @author David Gilmore
 * @date 11/15/13
 */
@Entity("industry_monthly_totals")
@JsonIgnoreProperties({"id"})
public class IndustryPartyAndGeoTotals {

    @Id
    private String id;

    private String industry;
    private int congress;
    @Property("republican_count")
    private int republicanCount;
    @Property("republican_sum")
    private int republicanSum;
    @Property("democrat_count")
    private int democratCount;
    @Property("democrat_sum")
    private int democratSum;
    @Property("independent_count")
    private int independentCount;
    @Property("independent_sum")
    private int independentSum;
    @Embedded("top_recipients")
    private List<Recipient> topRecipients;
    @Embedded("bottom_recipients")
    private List<Recipient> bottomRecipients;
    @Embedded("states")
    private HashMap<String,Recipient> states;

    public IndustryPartyAndGeoTotals() {}

    public IndustryPartyAndGeoTotals(IndustryPartyAndGeoTotals industryPartyAndGeoTotals) {
        this.setIndustry(industryPartyAndGeoTotals.getIndustry());
        this.setCongress(industryPartyAndGeoTotals.getCongress());
        this.setRepublicanCount(industryPartyAndGeoTotals.getRepublicanCount());
        this.setRepublicanSum(industryPartyAndGeoTotals.getRepublicanSum());
        this.setDemocratCount(industryPartyAndGeoTotals.getDemocratCount());
        this.setDemocratSum(industryPartyAndGeoTotals.getDemocratSum());
        this.setIndependentCount(industryPartyAndGeoTotals.getIndependentCount());
        this.setIndependentSum(industryPartyAndGeoTotals.getIndependentSum());
        this.setTopRecipients(industryPartyAndGeoTotals.getTopRecipients());
        this.setStates(industryPartyAndGeoTotals.getStates());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public int getCongress() {
        return congress;
    }

    public void setCongress(int congress) {
        this.congress = congress;
    }

    public int getRepublicanCount() {
        return republicanCount;
    }

    public void setRepublicanCount(int republicanCount) {
        this.republicanCount = republicanCount;
    }

    public int getRepublicanSum() {
        return republicanSum;
    }

    public void setRepublicanSum(int republicanSum) {
        this.republicanSum = republicanSum;
    }

    public int getDemocratCount() {
        return democratCount;
    }

    public void setDemocratCount(int democratCount) {
        this.democratCount = democratCount;
    }

    public int getDemocratSum() {
        return democratSum;
    }

    public void setDemocratSum(int democratSum) {
        this.democratSum = democratSum;
    }

    public int getIndependentCount() {
        return independentCount;
    }

    public void setIndependentCount(int independentCount) {
        this.independentCount = independentCount;
    }

    public int getIndependentSum() {
        return independentSum;
    }

    public void setIndependentSum(int independentSum) {
        this.independentSum = independentSum;
    }

    public List<Recipient> getTopRecipients() {
        return topRecipients;
    }

    public void setTopRecipients(List<Recipient> topRecipients) {
        this.topRecipients = topRecipients;
    }

    public List<Recipient> getBottomRecipients() {
        return bottomRecipients;
    }

    public void setBottomRecipients(List<Recipient> bottomRecipients) {
        this.bottomRecipients = bottomRecipients;
    }

    public HashMap<String, Recipient> getStates() {
        return states;
    }

    public void setStates(HashMap<String, Recipient> states) {
        this.states = states;
    }
}
