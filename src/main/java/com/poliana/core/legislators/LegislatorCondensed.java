package com.poliana.core.legislators;

import java.util.LinkedList;

/**
 * @author Grayson Carroll
 * @date 3/17/14
 */
public class LegislatorCondensed {

    private String bioguideId;
    private String firstName;
    private String gender;
    private String birthday;
    private String religion;
    private String party;
    private LinkedList<Term> terms;

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

    public LinkedList<Term> getTerms() {
        return terms;
    }

    public void setTerms(LinkedList<Term> terms) {
        this.terms = terms;
    }

    class Term {
        private String beginTimestamp;
        private String endTimestamp;
        private String termType;
        private LinkedList<Integer> congresses;

        String getBeginTimestamp() {
            return beginTimestamp;
        }

        void setBeginTimestamp(String beginTimestamp) {
            this.beginTimestamp = beginTimestamp;
        }

        String getEndTimestamp() {
            return endTimestamp;
        }

        void setEndTimestamp(String endTimestamp) {
            this.endTimestamp = endTimestamp;
        }

        String getTermType() {
            return termType;
        }

        void setTermType(String termType) {
            this.termType = termType;
        }

        LinkedList<Integer> getCongresses() {
            return congresses;
        }

        void setCongresses(LinkedList<Integer> congresses) {
            this.congresses = congresses;
        }
    }
}

