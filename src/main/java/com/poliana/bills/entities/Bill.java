package com.poliana.bills.entities;

import com.google.code.morphia.annotations.*;
import com.poliana.entities.entities.Legislator;

import java.util.List;

/**
 * @author David Gilmore
 * @date 11/16/13
 */
@Entity("bills")
public class Bill {

    @Id
    private String id;

    @Indexed @Property("bill_id")
    private String billId;
    @Property("vote_id")
    private String voteId;
    @Property("official_title")
    private String officialTitle;
    @Property("popular_title")
    private String popularTitle;
    @Property("short_title")
    private String shortTitle;
    @Reference
    private Legislator sponsor;
    @Reference
    private List<Legislator> cosponsors;
    @Property("top_subject")
    private String topSubject;
    private List<String> subjects;
    private String summary;
    @Property("introduced_at")
    private int introducedAt;
    @Property("house_passage_result")
    private String housePassageResult;
    @Property("house_passage_result_at")
    private int housePassageResultAt;
    @Property("senate_cloture_result")
    private String senateClotureResult;
    @Property("senate_cloture_result_at")
    private int senateClotureResultAt;
    @Property("senate_passage_result")
    private String senatePassageResult;
    @Property("senate_passage_result_at")
    private int senatePassageResultAt;
    @Property("awaiting_signature")
    private boolean awaitingSignature;
    private boolean enacted;
    private boolean vetoed;
    @Property("enacted_at")
    private int enactedAt;
    private String status;
    @Property("status_at")
    private int statusAt;
    private int congress;
    @Property("bill_type")
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

    public Legislator getSponsor() {
        return sponsor;
    }

    public void setSponsor(Legislator sponsor) {
        this.sponsor = sponsor;
    }

    public List<Legislator> getCosponsors() {
        return cosponsors;
    }

    public void setCosponsors(List<Legislator> cosponsors) {
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