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
    private String religion;
    @Reference
    private List<String> terms;

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

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public List<String> getTerms() {
        return terms;
    }

    public void setTerms(List<String> terms) {
        this.terms = terms;
    }
}
