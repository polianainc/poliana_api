package com.poliana.core.voteVsContributions;

import java.util.Date;

/**
 * @author David Gilmore
 * @date 1/21/14
 */
public class VoteVsContributionTotals {

    private String voteId;
    private String industryId;
    private String industryName;

    private int yeaContributionSums;
    private int yeaContributionCount;
    private double yeaContributionAvg;

    private int nayContributionSums;
    private int nayContributionCount;
    private double nayContributionAvg;

    private int notVotingContributionSums;
    private int notVotingContributionCount;
    private double notVotingContributionAvg;

    private int presentContributionSums;
    private int presentContributionCount;
    private double presentContributionAvg;

    private Date beginDate;
    private Date endDate;
    private Date voteDate;

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public int getYeaContributionSums() {
        return yeaContributionSums;
    }

    public void setYeaContributionSums(int yeaContributionSums) {
        this.yeaContributionSums = yeaContributionSums;
    }

    public int getYeaContributionCount() {
        return yeaContributionCount;
    }

    public void setYeaContributionCount(int yeaContributionCount) {
        this.yeaContributionCount = yeaContributionCount;
    }

    public double getYeaContributionAvg() {
        return yeaContributionAvg;
    }

    public void setYeaContributionAvg(double yeaContributionAvg) {
        this.yeaContributionAvg = yeaContributionAvg;
    }

    public int getNayContributionSums() {
        return nayContributionSums;
    }

    public void setNayContributionSums(int nayContributionSums) {
        this.nayContributionSums = nayContributionSums;
    }

    public int getNayContributionCount() {
        return nayContributionCount;
    }

    public void setNayContributionCount(int nayContributionCount) {
        this.nayContributionCount = nayContributionCount;
    }

    public double getNayContributionAvg() {
        return nayContributionAvg;
    }

    public void setNayContributionAvg(double nayContributionAvg) {
        this.nayContributionAvg = nayContributionAvg;
    }

    public int getNotVotingContributionSums() {
        return notVotingContributionSums;
    }

    public void setNotVotingContributionSums(int notVotingContributionSums) {
        this.notVotingContributionSums = notVotingContributionSums;
    }

    public int getNotVotingContributionCount() {
        return notVotingContributionCount;
    }

    public void setNotVotingContributionCount(int notVotingContributionCount) {
        this.notVotingContributionCount = notVotingContributionCount;
    }

    public double getNotVotingContributionAvg() {
        return notVotingContributionAvg;
    }

    public void setNotVotingContributionAvg(double notVotingContributionAvg) {
        this.notVotingContributionAvg = notVotingContributionAvg;
    }

    public int getPresentContributionSums() {
        return presentContributionSums;
    }

    public void setPresentContributionSums(int presentContributionSums) {
        this.presentContributionSums = presentContributionSums;
    }

    public int getPresentContributionCount() {
        return presentContributionCount;
    }

    public void setPresentContributionCount(int presentContributionCount) {
        this.presentContributionCount = presentContributionCount;
    }

    public double getPresentContributionAvg() {
        return presentContributionAvg;
    }

    public void setPresentContributionAvg(double presentContributionAvg) {
        this.presentContributionAvg = presentContributionAvg;
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

    public Date getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(Date voteDate) {
        this.voteDate = voteDate;
    }
}
