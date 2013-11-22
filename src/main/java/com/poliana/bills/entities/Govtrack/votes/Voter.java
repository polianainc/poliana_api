package com.poliana.bills.entities.govtrack.votes;

/**
 * @author David Gilmore
 * @date 11/13/13
 */
public class Voter {
    private String display_name;
    private String first_name;
    private String politician_id;
    private String last_name;
    private String party;
    private String state;

    public String getDisplayName() {
        return display_name;
    }

    public void setDisplayName(String display_name) {
        this.display_name = display_name;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getPoliticianId() {
        return politician_id;
    }

    public void setPoliticianId(String politicianId) {
        this.politician_id = politicianId;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
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