package com.poliana.bills.entities.VoteGT;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author David Gilmore
 * @date 11/12/13
 */
@SuppressWarnings("serial")
@Document(collection = "votes_govtrack")
public class VoteGT {

    @Id
    private String id;
    private BillRef bill;
    private AmendmentRef amendment;
    private String category;
    private String chamber;
    private String congress;
    private String date;
    private Nomination nomination;
    private String number;
    private String question;
    private String requires;
    private String result;
    private String result_text;
    private String session;
    private String source_url;
    private String subject;
    private String type;
    private String updated_at;
    private String vote_id;
    private Voters votes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BillRef getBill() {
        return bill;
    }

    public void setBill(BillRef bill) {
        this.bill = bill;
    }

    public AmendmentRef getAmendment() {
        return amendment;
    }

    public void setAmendment(AmendmentRef amendment) {
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

    public String getCongress() {
        return congress;
    }

    public void setCongress(String congress) {
        this.congress = congress;
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

    public void setNumString(String number) {
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
        return result_text;
    }

    public void setResultText(String result_text) {
        this.result_text = result_text;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSourceUrl() {
        return source_url;
    }

    public void setSourceUrl(String source_url) {
        this.source_url = source_url;
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
        return updated_at;
    }

    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getVoteId() {
        return vote_id;
    }

    public void setVoteId(String vote_id) {
        this.vote_id = vote_id;
    }

    public Voters getVotes() {
        return votes;
    }

    public void setVotes(Voters votes) {
        this.votes = votes;
    }
}
