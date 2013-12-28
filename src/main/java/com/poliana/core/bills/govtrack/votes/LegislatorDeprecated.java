package com.poliana.core.bills.govtrack.votes;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Deprecated
@SuppressWarnings("serial")
@Document(collection = "legislators")
public class LegislatorDeprecated {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String officialFull;
    private String party;
    private String thomasId;
    private String bioguideId;
    private String opensecretsId;
    private String fecId;
    private String votesmartId;
    private String ballotpedia;
    private String lisId;
    private String wikipediaId;
    private String govtrackId;
    private String maplightId;
    private String icsprId;
    private String cspanId;
    private String houseHistoryId;
    private String washingtonPostId;
    private String gender;
    private String birthday;
    private String religion;
    private int termStart;
    private int termEnd;
    private String termState;
    private String termType;
    private int district;
    private String termStateRank;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getBioguideId() {
        return bioguideId;
    }

    public void setBioguideId(String bioguideId) {
        this.bioguideId = bioguideId;
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

    public String getCspanId() {
        return cspanId;
    }

    public void setCspanId(String cspanId) {
        this.cspanId = cspanId;
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

    public int getTermStart() {
        return termStart;
    }

    public void setTermStart(int termStart) {
        this.termStart = termStart;
    }

    public int getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(int termEnd) {
        this.termEnd = termEnd;
    }

    public String getTermState() {
        return termState;
    }

    public void setTermState(String termState) {
        this.termState = termState;
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public void setDistrict(String district) {
        try {
            this.district = Integer.getInteger(district).intValue();
        }
        catch (NullPointerException e) {
            this.district = 0;
        }

    }

    public String getTermStateRank() {
        return termStateRank;
    }

    public void setTermStateRank(String termStateRank) {
        this.termStateRank = termStateRank;
    }
}