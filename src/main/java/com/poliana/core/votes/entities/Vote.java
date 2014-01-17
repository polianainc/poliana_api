package com.poliana.core.votes.entities;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.springframework.data.annotation.Id;

/**
 * @author David Gilmore
 * @date 11/17/13
 */
@Entity("votes")
public class Vote {

    @Id
    private String id;

    @Embedded
    private BillReference bill;

    @Embedded
    private VoteAmendmentReference amendment;

    private String category;
    private String chamber;
    private int congress;
    private String date;

    @Embedded
    private Nomination nomination;

    private String number;
    private String question;
    private String requires;
    private String result;

    @Property("result_text")
    private String resultText;

    private String session;

    @Property("source_url")
    private String sourceUrl;

    private String subject;
    private String type;

    @Property("updated_at")
    private String updatedAt;

    @Property("vote_id")
    private String voteId;

    @Embedded
    private Voters votes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BillReference getBill() {
        return bill;
    }

    public void setBill(BillReference bill) {
        this.bill = bill;
    }

    public VoteAmendmentReference getAmendment() {
        return amendment;
    }

    public void setAmendment(VoteAmendmentReference amendment) {
        this.amendment = amendment;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public int getCongress() {
        return congress;
    }

    public void setCongress(String congress) {
        this.congress = Integer.getInteger(congress).intValue();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public Voters getVotes() {
        return votes;
    }

    public void setVotes(Voters votes) {
        this.votes = votes;
    }
}
