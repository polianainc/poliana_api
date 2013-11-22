package com.poliana.campaignFinance.entities;

import com.google.code.morphia.annotations.*;
import com.poliana.entities.entities.Industry;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.List;

/**
 * @author David Gilmore
 * @date 11/15/13
 */
@SuppressWarnings("serial")
@Entity("industry_time_range_totals")
public class IndTimeRangeTotals {

    @Id
    private ObjectId id;

    @Reference
    private Industry industry;
    private int congress;
    @Property("republican_count")
    private int republicanCount;
    @Property("republican_count")
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
    @Embedded("state_averages")
    private HashMap<String,Recipient> stateAverages;

    public IndTimeRangeTotals() {}

    public IndTimeRangeTotals(IndTimeRangeTotals indTimeRangeTotals) {
        this.setIndustry(indTimeRangeTotals.getIndustry());
        this.setCongress(indTimeRangeTotals.getCongress());
        this.setRepublicanCount(indTimeRangeTotals.getRepublicanCount());
        this.setRepublicanSum(indTimeRangeTotals.getRepublicanSum());
        this.setDemocratCount(indTimeRangeTotals.getDemocratCount());
        this.setDemocratSum(indTimeRangeTotals.getDemocratSum());
        this.setIndependentCount(indTimeRangeTotals.getIndependentCount());
        this.setIndependentSum(indTimeRangeTotals.getIndependentSum());
        this.setTopRecipients(indTimeRangeTotals.getTopRecipients());
        this.setStateAverages(indTimeRangeTotals.getStateAverages());
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
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

    public HashMap<String, Recipient> getStateAverages() {
        return stateAverages;
    }

    public void setStateAverages(HashMap<String, Recipient> stateAverages) {
        this.stateAverages = stateAverages;
    }
}
