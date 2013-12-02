package com.poliana.legislation.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.poliana.entities.entities.LegislatorDeprecated;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author David Gilmore
 * @date 11/14/13
 */
@SuppressWarnings("serial")
@Document(collection = "bills")
public class BillDeprecated implements Serializable
{
    @Id
    private String id;

    @DBRef
    private VoteDeprecated votes;
    private String billId;
    private String voteId;
    private String officialTitle;
    private String popularTitle;
    private String shortTitle;
    @DBRef
    private LegislatorDeprecated sponsor;
    @DBRef
    private List<LegislatorDeprecated> cosponsors;
    private String topSubject;
    private List<String> subjects;
    private String summary;
    private int introducedAt;
    private String housePassageResult;
    private int housePassageResultAt;
    private String senateClotureResult;
    private int senateClotureResultAt;
    private String senatePassageResult;
    private int senatePassageResultAt;
    private boolean awaitingSignature;
    private boolean enacted;
    private boolean vetoed;
    private int enactedAt;
    private String status;
    private int statusAt;
    private int congress;
    private String billType;
    private int year;
    private int month;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public VoteDeprecated getVotes() {
        return votes;
    }

    public void setVotes(VoteDeprecated votes) {
        this.votes = votes;
    }

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public String getOfficialTitle() {
        return officialTitle;
    }

    public void setOfficialTitle(String officialTitle) {
        this.officialTitle = officialTitle;
    }

    public String getPopularTitle() {
        return popularTitle;
    }

    public void setPopularTitle(String popularTitle) {
        this.popularTitle = popularTitle;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public LegislatorDeprecated getSponsor() {
        return sponsor;
    }

    public void setSponsor(LegislatorDeprecated sponsor) {
        this.sponsor = sponsor;
    }



    public List<LegislatorDeprecated> getCosponsors() {
        return cosponsors;
    }

    public void setCosponsors(List<LegislatorDeprecated> cosponsors) {
        this.cosponsors = cosponsors;
    }

    public String getTopSubject() {
        return topSubject;
    }

    public void setTopSubject(String topSubject) {
        this.topSubject = topSubject;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public void setSubjectsJson(String subjects) {
        try {
            JSONArray jsonArray = new JSONArray(subjects);
            List<String> list = new LinkedList<>();
            for (int i=0; i<jsonArray.length(); i++) {
                list.add( jsonArray.getString(i) );
            }
            this.subjects = list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setSubjectsDelim(String subjects) {
        this.subjects = new ArrayList<>(Arrays.asList(subjects.split("\u0002")));
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getIntroducedAt() {
        return introducedAt;
    }

    public void setIntroducedAt(int introducedAt) {
        this.introducedAt = introducedAt;
    }

    public String getHousePassageResult() {
        return housePassageResult;
    }

    public void setHousePassageResult(String housePassageResult) {
        this.housePassageResult = housePassageResult;
    }

    public int getHousePassageResultAt() {
        return housePassageResultAt;
    }

    public void setHousePassageResultAt(int housePassageResultAt) {
        this.housePassageResultAt = housePassageResultAt;
    }

    public String getSenateClotureResult() {
        return senateClotureResult;
    }

    public void setSenateClotureResult(String senateClotureResult) {
        this.senateClotureResult = senateClotureResult;
    }

    public int getSenateClotureResultAt() {
        return senateClotureResultAt;
    }

    public void setSenateClotureResultAt(int senateClotureResultAt) {
        this.senateClotureResultAt = senateClotureResultAt;
    }

    public String getSenatePassageResult() {
        return senatePassageResult;
    }

    public void setSenatePassageResult(String senatePassageResult) {
        this.senatePassageResult = senatePassageResult;
    }

    public int getSenatePassageResultAt() {
        return senatePassageResultAt;
    }

    public void setSenatePassageResultAt(int senatePassageResultAt) {
        this.senatePassageResultAt = senatePassageResultAt;
    }

    public boolean isAwaitingSignature() {
        return awaitingSignature;
    }

    public void setAwaitingSignature(boolean awaitingSignature) {
        this.awaitingSignature = awaitingSignature;
    }

    public boolean isEnacted() {
        return enacted;
    }

    public void setEnacted(boolean enacted) {
        this.enacted = enacted;
    }

    public boolean isVetoed() {
        return vetoed;
    }

    public void setVetoed(boolean vetoed) {
        this.vetoed = vetoed;
    }

    public int getEnactedAt() {
        return enactedAt;
    }

    public void setEnactedAt(int enactedAt) {
        this.enactedAt = enactedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusAt() {
        return statusAt;
    }

    public void setStatusAt(int statusAt) {
        this.statusAt = statusAt;
    }

    public int getCongress() {
        return congress;
    }

    public void setCongress(int congress) {
        this.congress = congress;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}

