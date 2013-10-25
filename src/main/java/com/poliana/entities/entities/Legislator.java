package com.poliana.entities.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@SuppressWarnings("serial")
@Document(collection = "legislators")
public class Legislator {

    @Id
    private String bioguideId;

    private String firstName;
    private String lastName;
    private String officialFull;
    private String party;
    private String thomasId;
    private String opensecretsId;
    private String fecId;
    private String votesmartId;
    private String ballotpedia;
    private String lisId;
    private String wikipediaId;
    private String govtrackId;
    private String maplightId;
    private String icsprId;
    private String cspan;
    private String houseHistoryId;
    private String washingtonPostId;
    private String gender;
    private String birthday;
    private String religion;

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

    public String getOfficialFull() {
        return officialFull;
    }

    public void setOfficialFull(String officialFull) {
        this.officialFull = officialFull;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getThomasId() {
        return thomasId;
    }

    public void setThomasId(String thomasId) {
        this.thomasId = thomasId;
    }

    public String getOpensecretsId() {
        return opensecretsId;
    }

    public void setOpensecretsId(String opensecretsId) {
        this.opensecretsId = opensecretsId;
    }

    public String getFecId() {
        return fecId;
    }

    public void setFecId(String fecId) {
        this.fecId = fecId;
    }

    public String getVotesmartId() {
        return votesmartId;
    }

    public void setVotesmartId(String votesmartId) {
        this.votesmartId = votesmartId;
    }

    public String getBallotpedia() {
        return ballotpedia;
    }

    public void setBallotpedia(String ballotpedia) {
        this.ballotpedia = ballotpedia;
    }

    public String getLisId() {
        return lisId;
    }

    public void setLisId(String lisId) {
        this.lisId = lisId;
    }

    public String getWikipediaId() {
        return wikipediaId;
    }

    public void setWikipediaId(String wikipediaId) {
        this.wikipediaId = wikipediaId;
    }

    public String getGovtrackId() {
        return govtrackId;
    }

    public void setGovtrackId(String govtrackId) {
        this.govtrackId = govtrackId;
    }

    public String getMaplightId() {
        return maplightId;
    }

    public void setMaplightId(String maplightId) {
        this.maplightId = maplightId;
    }

    public String getIcsprId() {
        return icsprId;
    }

    public void setIcsprId(String icsprId) {
        this.icsprId = icsprId;
    }

    public String getCspan() {
        return cspan;
    }

    public void setCspan(String cspan) {
        this.cspan = cspan;
    }

    public String getHouseHistoryId() {
        return houseHistoryId;
    }

    public void setHouseHistoryId(String houseHistoryId) {
        this.houseHistoryId = houseHistoryId;
    }

    public String getWashingtonPostId() {
        return washingtonPostId;
    }

    public void setWashingtonPostId(String washingtonPostId) {
        this.washingtonPostId = washingtonPostId;
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
}