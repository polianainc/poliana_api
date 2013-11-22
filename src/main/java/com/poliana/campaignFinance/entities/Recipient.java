package com.poliana.campaignFinance.entities;

import com.google.code.morphia.annotations.Property;
import com.google.code.morphia.annotations.Transient;

/**
 * @author David Gilmore
 * @date 11/20/13
 */
public class Recipient {

    @Property("bioguide_id")
    private String bioguideId;
    private String state;
    @Transient
    private String party;
    private int sum;
    private int count;
    @Property("series_average")
    private int seriesAverage;

    public String getBioguideId() {
        return bioguideId;
    }

    public void setBioguideId(String bioguideId) {
        this.bioguideId = bioguideId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSeriesAverage() {
        return seriesAverage;
    }

    public void setSeriesAverage(int seriesAverage) {
        this.seriesAverage = seriesAverage;
    }
}
