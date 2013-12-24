package com.poliana.core.entities.models;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;


import java.util.HashMap;

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
    private HashMap<String,Integer> topIndustryContributions;
    @Property("top_pac_contributions")
    private HashMap<String,Integer> topPACContributions;

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

    public HashMap<String, Integer> getTopIndustryContributions() {
        return topIndustryContributions;
    }

    public void setTopIndustryContributions(HashMap<String, Integer> topIndustryContributions) {
        this.topIndustryContributions = topIndustryContributions;
    }

    public HashMap<String, Integer> getTopPACContributions() {
        return topPACContributions;
    }

    public void setTopPACContributions(HashMap<String, Integer> topPACContributions) {
        this.topPACContributions = topPACContributions;
    }
}
