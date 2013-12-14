package com.poliana.core.entities.models;

import com.google.code.morphia.annotations.*;

import java.util.List;

/**
 * @author David Gilmore
 * @date 12/11/13
 */
@Entity("politician_profile")
public class PoliticianProfile {

    @Id
    private String id;

    @Indexed
    @Property("bioguide_id")
    private String bioguideId;
    @Property("first_name")
    private String firstName;
    @Property("last_name")
    private String lastName;

    @Reference
    private List<String> sessions;

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

    public List<String> getSessions() {
        return sessions;
    }

    public void setSessions(List<String> sessions) {
        this.sessions = sessions;
    }
}
