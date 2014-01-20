package com.poliana.core.contributionsVsVotes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.util.Date;
import java.util.List;

/**
 * @author David Gilmore
 * @date 1/19/14
 */
@Entity("vote_vs_industry_contributions")
@JsonIgnoreProperties({"id"})
public class VoteVsIndustryContributions {

    @Id
    private String id;

    @Property("vote_id")
    private String voteId;

    private Integer congress;

    @Property("vote_date")
    private Date voteDate;

    @Property("begin_date")
    private Date beginDate;

    @Property("end_date")
    private Date endDate;

    @Embedded
    private List<VoteVsContribution> yeas;

    @Embedded
    private List<VoteVsContribution> nays;

    @Embedded("not_voting")
    private List<VoteVsContribution> notVoting;

    @Embedded
    private List<VoteVsContribution> absent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public Integer getCongress() {
        return congress;
    }

    public void setCongress(Integer congress) {
        this.congress = congress;
    }

    public Date getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(Date voteDate) {
        this.voteDate = voteDate;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<VoteVsContribution> getYeas() {
        return yeas;
    }

    public void setYeas(List<VoteVsContribution> yeas) {
        this.yeas = yeas;
    }

    public List<VoteVsContribution> getNays() {
        return nays;
    }

    public void setNays(List<VoteVsContribution> nays) {
        this.nays = nays;
    }

    public List<VoteVsContribution> getNotVoting() {
        return notVoting;
    }

    public void setNotVoting(List<VoteVsContribution> notVoting) {
        this.notVoting = notVoting;
    }

    public List<VoteVsContribution> getAbsent() {
        return absent;
    }

    public void setAbsent(List<VoteVsContribution> absent) {
        this.absent = absent;
    }
}
