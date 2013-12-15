package com.poliana.core.finance.general.entities;

import com.google.code.morphia.annotations.Property;

/**
 * @author David Gilmore
 * @date 11/20/13
 */
public class Recipient {

    @Property("bioguide_id")
    private String bioguideId;
    private String state;
    private String party;
    @Property("first_name")
    private String firstName;
    @Property("last_name")
    private String lastName;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
