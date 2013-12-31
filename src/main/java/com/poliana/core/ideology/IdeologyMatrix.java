package com.poliana.core.ideology;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;

import java.util.List;

/**
 * @author David Gilmore
 * @date 12/6/13
 */
@Entity("ideology_analysis")
public class IdeologyMatrix {

    private String chamber;

    @Indexed
    private int congress;

    @Indexed
    @Property("begin_timestamp")
    private int beginTimestamp;

    @Indexed
    @Property("end_timestamp")
    private int endTimestamp;
    @Property("ideology_values")
    private double[] ideologyValues;
    @Embedded
    private List<LegislatorIdeology> ideologies;

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public int getCongress() {
        return congress;
    }

    public void setCongress(int congress) {
        this.congress = congress;
    }

    public int getBeginTimestamp() {
        return beginTimestamp;
    }

    public void setBeginTimestamp(int beginTimestamp) {
        this.beginTimestamp = beginTimestamp;
    }

    public int getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(int endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public double[] getIdeologyValues() {
        return ideologyValues;
    }

    public void setIdeologyValues(double[] ideologyValues) {
        this.ideologyValues = ideologyValues;
    }

    public List<LegislatorIdeology> getIdeologies() {
        return ideologies;
    }

    public void setIdeologies(List<LegislatorIdeology> ideologies) {
        this.ideologies = ideologies;
    }
}
