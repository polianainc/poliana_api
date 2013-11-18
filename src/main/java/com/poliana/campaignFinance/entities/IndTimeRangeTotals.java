package com.poliana.campaignFinance.entities;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Property;
import com.google.code.morphia.annotations.Reference;
import com.poliana.entities.entities.Industry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

/**
 * @author David Gilmore
 * @date 11/15/13
 */
@SuppressWarnings("serial")
@Document(collection = "industry_time_range_totals")
public class IndTimeRangeTotals {

    @Id
    private ObjectId id;

    @Reference
    private Industry industry;
    private int congress;
    @Property("range_length")
    private int rangeLength;
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
    @Reference("top_recipients")
    private List<IndToPolContrTotals> topRecipients;
    @Embedded("state_averages")
    private Map<String,Integer> stateAverages;

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

    public int getRangeLength() {
        return rangeLength;
    }

    public void setRangeLength(int rangeLength) {
        this.rangeLength = rangeLength;
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

    public List<IndToPolContrTotals> getTopRecipients() {
        return topRecipients;
    }

    public void setTopRecipients(List<IndToPolContrTotals> topRecipients) {
        this.topRecipients = topRecipients;
    }

    public Map<String, Integer> getStateAverages() {
        return stateAverages;
    }

    public void setStateAverages(Map<String, Integer> stateAverages) {
        this.stateAverages = stateAverages;
    }
}
