package com.poliana.core.entities.entities;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;

/**
 * @author David Gilmore
 * @date 11/16/13
 */
@Entity("legislators")
public class Legislator {

    @Id
    private String id;

    @Property("first_name")
    private String firstName;
    @Property("last_name")
    private String lastName;
    @Property("official_full")
    private String officialFull;
    private String party;
    @Indexed @Property("thomas_id")
    private String thomasId;
    @Indexed @Property("bioguide_id")
    private String bioguideId;
    @Property("opensecrets_id")
    private String opensecretsId;
    @Property("fec_id")
    private String fecId;
    @Property("votesmart_id")
    private String votesmartId;
    private String ballotpedia;
    @Indexed @Property("lis_id")
    private String lisId;
    @Property("wikipedia_id")
    private String wikipediaId;
    @Indexed @Property("govtrack_id")
    private String govtrackId;
    @Property("maplight_id")
    private String maplightId;
    @Property("icspr_id")
    private String icsprId;
    @Property("cspan_id")
    private String cspanId;
    @Property("house_history_id")
    private String houseHistoryId;
    @Property("washington_post_id")
    private String washingtonPostId;
    private String gender;
    private String birthday;
    private String religion;
    @Property("term_start")
    private int termStart;
    @Property("term_end")
    private int termEnd;
    @Property("term_state")
    private String termState;
    @Property("term_type")
    private String termType;
    private int district;
    @Property("term_state_rank")
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

    public String getTermStateRank() {
        return termStateRank;
    }

    public void setTermStateRank(String termStateRank) {
        this.termStateRank = termStateRank;
    }
}