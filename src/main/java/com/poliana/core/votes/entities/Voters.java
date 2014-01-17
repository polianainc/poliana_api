package com.poliana.core.votes.entities;

import org.mongodb.morphia.annotations.Embedded;

import java.util.List;

/**
 * @author David Gilmore
 * @date 11/13/13
 */
public class Voters {

    @Embedded("Nay")
    private List<Voter> nay;

    @Embedded("Not Voting")
    private List<Voter> notVoting;

    @Embedded("Present")
    private List<Voter> present;

    @Embedded("Yea")
    private List<Voter> yea;

    public List<Voter> getNay() {
        return nay;
    }

    public void setNay(List<Voter> nay) {
        this.nay = nay;
    }

    public List<Voter> getNotVoting() {
        return notVoting;
    }

    public void setNotVoting(List<Voter> not_voting) {
        this.notVoting = not_voting;
    }

    public List<Voter> getPresent() {
        return present;
    }

    public void setPresent(List<Voter> present) {
        this.present = present;
    }

    public List<Voter> getYea() {
        return yea;
    }

    public void setYea(List<Voter> yea) {
        this.yea = yea;
    }

}