package com.poliana.core.sponsorship;

import com.poliana.core.legislators.Legislator;

import java.util.HashMap;
import java.util.List;

/**
 * @author David Gilmore
 * @date 12/30/13
 */
public class SponsorshipMatrix {

    private String chamber;
    private long beginTimestamp;
    private long endTimestamp;
    private HashMap<String, Integer> indices;
    private HashMap<String, Legislator> legislatorHashMap;
    private double[][] matrix;
    private List<Legislator> legislatorList;

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public long getBeginTimestamp() {
        return beginTimestamp;
    }

    public void setBeginTimestamp(long beginTimestamp) {
        this.beginTimestamp = beginTimestamp;
    }

    public long getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public HashMap<String, Integer> getIndices() {
        return indices;
    }

    public void setIndices(HashMap<String, Integer> indices) {
        this.indices = indices;
    }

    public HashMap<String, Legislator> getLegislatorHashMap() {
        return legislatorHashMap;
    }

    public void setLegislatorHashMap(HashMap<String, Legislator> legislatorHashMap) {
        this.legislatorHashMap = legislatorHashMap;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public List<Legislator> getLegislatorList() {
        return legislatorList;
    }

    public void setLegislatorList(List<Legislator> legislatorList) {
        this.legislatorList = legislatorList;
    }
}
