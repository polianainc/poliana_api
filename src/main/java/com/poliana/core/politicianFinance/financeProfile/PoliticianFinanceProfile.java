package com.poliana.core.politicianFinance.financeProfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.mongodb.morphia.annotations.*;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * @author David Gilmore
 * @date 12/11/13
 */
@Entity("politician_finance_profile")
@JsonIgnoreProperties({"id"})
public class PoliticianFinanceProfile {

    @Id
    @Expose
    private String id;

    @Indexed
    @Property("bioguide_id")
    private String bioguideId;
    @Property("first_name")
    private String firstName;
    @Property("last_name")
    private String lastName;

    @Embedded
    private List<SessionTotals> sessions;

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

    public List<SessionTotals> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionTotals> sessions) {
        this.sessions = sessions;
    }
}