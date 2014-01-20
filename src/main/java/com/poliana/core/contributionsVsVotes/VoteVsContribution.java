package com.poliana.core.contributionsVsVotes;

import org.mongodb.morphia.annotations.Property;

/**
 * @author David Gilmore
 * @date 1/20/14
 */
public class VoteVsContribution {

    @Property("bioguide_id")
    private String bioguideId;
    private Integer sum;
    private String party;
    private String state;

    public String getBioguideId() {
        return bioguideId;
    }

    public void setBioguideId(String bioguideId) {
        this.bioguideId = bioguideId;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
