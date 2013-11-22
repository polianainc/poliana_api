package com.poliana.legislation.entities.deprecated;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@SuppressWarnings("serial")
@Document(collection = "voter")
public class BillVote {

    @Id
    private String id;
    private String bioguideId;
    private String displayName;
    private String firstName;
    private String lastName;
    private String party;
    private String state;
    private int district;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBioguideId() {
        return bioguideId;
    }

    public void setBioguideId(String bioguideId) {
        this.bioguideId = bioguideId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }
}
