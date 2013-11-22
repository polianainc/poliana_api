package com.poliana.legislation.entities.govtrack.votes;

import java.util.List;

/**
 * @author David Gilmore
 * @date 11/13/13
 */
public class Voters {
    private List<Voter> nay;
    private List<Voter> not_voting;
    private List<Voter> present;
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