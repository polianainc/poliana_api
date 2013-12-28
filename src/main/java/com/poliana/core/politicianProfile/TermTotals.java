package com.poliana.core.politicianProfile;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;
import com.poliana.core.industryFinance.entities.IndToPolContrTotals;
import com.poliana.core.pacFinance.PacPoliticianContrTotals;


import java.util.HashMap;
import java.util.List;

/**
 * @author David Gilmore
 * @date 12/11/13
 */
@Entity("term_totals")
public class TermTotals {

    @Id
    private String id;

    @Indexed
    private int congress;
    private String chamber;
    @Property("term_start")
    private int termStart;
    private String religion;
    @Property("ideology_score")
    private double ideologyScore;
    @Property("leadership_score")
    private double leadershipScore;
    @Property("top_industry_contributions")
    private List<IndToPolContrTotals> topIndustryContributions;
    @Property("top_pac_contributions")
    private List<PacPoliticianContrTotals> topPACContributions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCongress() {
        return congress;
    }

    public void setCongress(int congress) {
        this.congress = congress;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public int getTermStart() {
        return termStart;
    }

    public void setTermStart(int termStart) {
        this.termStart = termStart;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public double getIdeologyScore() {
        return ideologyScore;
    }

    public void setIdeologyScore(double ideologyScore) {
        this.ideologyScore = ideologyScore;
    }

    public double getLeadershipScore() {
        return leadershipScore;
    }

    public void setLeadershipScore(double leadershipScore) {
        this.leadershipScore = leadershipScore;
    }

    public List<IndToPolContrTotals> getTopIndustryContributions() {
        return topIndustryContributions;
    }

    public void setTopIndustryContributions(List<IndToPolContrTotals> topIndustryContributions) {
        this.topIndustryContributions = topIndustryContributions;
    }

    public List<PacPoliticianContrTotals> getTopPACContributions() {
        return topPACContributions;
    }

    public void setTopPACContributions(List<PacPoliticianContrTotals> topPACContributions) {
        this.topPACContributions = topPACContributions;
    }
}
