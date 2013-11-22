package com.poliana.bills.models;

import com.poliana.bills.entities.govtrack.votes.VoteAmendmentRef;
import com.poliana.bills.entities.govtrack.votes.BillRef;
import com.poliana.bills.entities.govtrack.votes.Nomination;
import com.poliana.entities.entities.LegislatorDeprecated;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author David Gilmore
 * @date 11/13/13
 */
@SuppressWarnings("serial")
@Document(collection = "votes")
public class VoteDeprecated {

    @Id
    private String id;

    @DBRef
    private BillDeprecated bill;
    private String voteId;
    private String category;
    private String congress;
    private int date;
    private BillRef billInfo;
    private VoteAmendmentRef amendment;
    private Nomination nomination;
    private String number;
    private String question;
    private String requires;
    private String result;
    private String resultText;
    private String session;
    private String sourceUrl;
    private String subject;
    private String type;
    private String updatedAt;
    private String chamber;
    private int year;
    private int month;
    private int yeaTotal;
    private int nayTotal;
    private int notVotingTotal;
    private int presentTotal;

    @DBRef
    private List<LegislatorDeprecated> yeas;
    @DBRef
    private List<LegislatorDeprecated> nays;
    @DBRef
    private List<LegislatorDeprecated> notVoting;
    @DBRef
    private List<LegislatorDeprecated> present;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BillDeprecated getBill() {
        return bill;
    }

    public void setBill(BillDeprecated bill) {
        this.bill = bill;
    }

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCongress() {
        return congress;
    }

    public void setCongress(String congress) {
        this.congress = congress;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public BillRef getBillInfo() {
        return billInfo;
    }

    public void setBillInfo(BillRef billInfo) {
        this.billInfo = billInfo;
    }

    public VoteAmendmentRef getAmendment() {
        return amendment;
    }

    public void setAmendment(VoteAmendmentRef amendment) {
        this.amendment = amendment;
    }

    public Nomination getNomination() {
        return nomination;
    }

    public void setNomination(Nomination nomination) {
        this.nomination = nomination;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getRequires() {
        return requires;
    }

    public void setRequires(String requires) {
        this.requires = requires;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
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

    public int getYeaTotal() {
        return yeaTotal;
    }

    public void setYeaTotal(int yeaTotal) {
        this.yeaTotal = yeaTotal;
    }

    public int getNayTotal() {
        return nayTotal;
    }

    public void setNayTotal(int nayTotal) {
        this.nayTotal = nayTotal;
    }

    public int getNotVotingTotal() {
        return notVotingTotal;
    }

    public void setNotVotingTotal(int notVotingTotal) {
        this.notVotingTotal = notVotingTotal;
    }

    public int getPresentTotal() {
        return presentTotal;
    }

    public void setPresentTotal(int presentTotal) {
        this.presentTotal = presentTotal;
    }

    public List<LegislatorDeprecated> getYeas() {
        return yeas;
    }

    public void setYeas(List<LegislatorDeprecated> yeas) {
        this.yeas = yeas;
    }

    public List<LegislatorDeprecated> getNays() {
        return nays;
    }

    public void setNays(List<LegislatorDeprecated> nays) {
        this.nays = nays;
    }

    public List<LegislatorDeprecated> getNotVoting() {
        return notVoting;
    }

    public void setNotVoting(List<LegislatorDeprecated> notVoting) {
        this.notVoting = notVoting;
    }

    public List<LegislatorDeprecated> getPresent() {
        return present;
    }

    public void setPresent(List<LegislatorDeprecated> present) {
        this.present = present;
    }

}
