package com.poliana.core.votes.entities;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

/**
 * @author David Gilmore
 * @date 11/13/13
 */
@Entity("voter")
public class Voter {

    @Id
    private String id;

    @Property("display_name")
    private String displayName;

    @Property("first_name")
    private String firstName;

    @Property("id")
    private String bioguideId;

    @Property("last_name")
    private String lastName;

    private String party;
    private String state;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String display_name) {
        this.displayName = display_name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String first_name) {
        this.firstName = first_name;
    }

    public String getBioguideId() {
        return bioguideId;
    }

    public void setBioguideId(String bioguideId) {
        this.bioguideId = bioguideId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String last_name) {
        this.lastName = last_name;
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