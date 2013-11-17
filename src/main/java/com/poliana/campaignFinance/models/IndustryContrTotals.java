package com.poliana.campaignFinance.models;

import com.poliana.campaignFinance.entities.IndToPolContrTotals;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

/**
 * @author David Gilmore
 * @date 11/15/13
 */
@SuppressWarnings("serial")
@Document(collection = "industry_contribution_totals")
public class IndustryContrTotals {

    @Id
    private String id;

    private int republicanCount;
    private int republicanSum;
    private int democratCount;
    private int demoncratSum;
    private int independentCount;
    private int independentSum;
    private List<IndToPolContrTotals> topRecipients;
    private Map<String,Integer> stateAverages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getDemoncratSum() {
        return demoncratSum;
    }

    public void setDemoncratSum(int demoncratSum) {
        this.demoncratSum = demoncratSum;
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
