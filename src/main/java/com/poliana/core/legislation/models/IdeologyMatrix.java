package com.poliana.core.legislation.models;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Transient;
import org.apache.commons.math3.linear.SingularValueDecomposition;

import java.util.HashMap;

/**
 * @author David Gilmore
 * @date 12/6/13
 */
@Entity("ideology_analysis")
public class IdeologyMatrix {

    private String chamber;
    private int beginDate;
    private int endDate;
    private double[][] sponsorshipMatrix;
    private double[][] u;
    @Transient
    private SingularValueDecomposition svd;
    private HashMap<String,Integer> indices;

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public int getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(int beginDate) {
        this.beginDate = beginDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    public double[][] getSponsorshipMatrix() {
        return sponsorshipMatrix;
    }

    public void setSponsorshipMatrix(double[][] sponsorshipMatrix) {
        this.sponsorshipMatrix = sponsorshipMatrix;
    }

    public double[][] getU() {
        return u;
    }

    public void setU(double[][] u) {
        this.u = u;
    }

    public SingularValueDecomposition getSvd() {
        return svd;
    }

    public void setSvd(SingularValueDecomposition svd) {
        this.svd = svd;
    }

    public HashMap<String, Integer> getIndices() {
        return indices;
    }

    public void setIndices(HashMap<String, Integer> indices) {
        this.indices = indices;
    }
}
