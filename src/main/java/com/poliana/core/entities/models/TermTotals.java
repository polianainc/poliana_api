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
    @Property("ideology_score")
    private double ideologyScore;
    @Property("leadership_score")
    private double leadershipScore;
    @Property("top_industry_contributions")
    private HashMap<String,Integer> topIndustryContributions;
    @Property("top_pac_contributions")
    private HashMap<String,Integer> topPACContributions;

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
