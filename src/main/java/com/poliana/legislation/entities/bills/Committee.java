package com.poliana.legislation.entities.bills;

import com.google.code.morphia.annotations.Property;

import java.util.List;

/**
 * @author David Gilmore
 * @date 11/22/13
 */
public class Committee {
    private List<String> activity;
    private String committee;
    @Property("committee_id")
    private String committeeId;
    private String subcommittee;
    @Property("subcommittee_id")
    private String subcommitteeId;

    public List<String> getActivity() {
        return activity;
    }

    public void setActivity(List<String> activity) {
        this.activity = activity;
    }

    public String getCommittee() {
        return committee;
    }

    public void setCommittee(String committee) {
        this.committee = committee;
    }

    public String getCommitteeId() {
        return committeeId;
    }

    public void setCommitteeId(String committeeId) {
        this.committeeId = committeeId;
    }

    public String getSubcommittee() {
        return subcommittee;
    }

    public void setSubcommittee(String subcommittee) {
        this.subcommittee = subcommittee;
    }

    public String getSubcommitteeId() {
        return subcommitteeId;
    }

    public void setSubcommitteeId(String subcommitteeId) {
        this.subcommitteeId = subcommitteeId;
    }
}
