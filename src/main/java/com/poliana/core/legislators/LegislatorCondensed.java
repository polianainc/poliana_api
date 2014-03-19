package com.poliana.core.legislators;

import org.msgpack.annotation.Message;

import java.util.LinkedList;

/**
 * @author Grayson Carroll
 * @date 3/17/14
 */
@Message
public class LegislatorCondensed {

    private String bioguideId;
    private String firstName;
    private String lastName;
    private String gender;
    private String birthday;
    private String religion;
    private String party;
    private LinkedList<LegislatorCondensedTerm> terms;

    String getBioguideId() {
        return bioguideId;
    }

    void setBioguideId(String bioguideId) {
        this.bioguideId = bioguideId;
    }

    String getFirstName() {
        return firstName;
    }

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public LinkedList<LegislatorCondensedTerm> getTerms() {
        return terms;
    }

    public void setTerms(LinkedList<LegislatorCondensedTerm> terms) {
        this.terms = terms;
    }
}


