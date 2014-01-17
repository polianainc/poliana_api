package com.poliana.core.votes.entities;

import javax.jdo.annotations.Embedded;
import java.util.List;

/**
 * @author David Gilmore
 * @date 11/13/13
 */
public class Voters {

    @Embedded
    private List<Voter> nay;

    @Embedded
    private List<Voter> not_voting;

    @Embedded
    private List<Voter> present;

    @Embedded
    private List<Voter> yea;

    public List<Voter> getNay() {
        return nay;
    }

    public void setNay(List<Voter> nay) {
        this.nay = nay;
    }

    public List<Voter> getNotVoting() {
        return not_voting;
    }

    public void setNotVoting(List<Voter> not_voting) {
        this.not_voting = not_voting;
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