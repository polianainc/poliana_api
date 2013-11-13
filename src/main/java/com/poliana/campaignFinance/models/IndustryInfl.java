package com.poliana.campaignFinance.models;

import java.util.List;

public class IndustryInfl {
    private int mainTotal;
    private int mainTouched;
    private double offTotal;
    private int offTouched;
    private List<IndustryContributor> mainChildren;
    private List<IndustryContributor> offChildren;

    public int getMainTotal() {
        return mainTotal;
    }

    public void setMainTotal(int mainTotal) {
        this.mainTotal = mainTotal;
    }

    public int getMainTouched() {
        return mainTouched;
    }

    public void setMainTouched(int mainTouched) {
        this.mainTouched = mainTouched;
    }

    public List<IndustryContributor> getMainChildren() {
        return mainChildren;
    }

    public void setMainChildren(List<IndustryContributor> mainChildren) {
        this.mainChildren = mainChildren;
    }

    public double getOffTotal() {
        return offTotal;
    }

    public void setOffTotal(double offTotal) {
        this.offTotal = offTotal;
    }

    public int getOffTouched() {
        return offTouched;
    }

    public void setOffTouched(int offTouched) {
        this.offTouched = offTouched;
    }

    public List<IndustryContributor> getOffChildren() {
        return offChildren;
    }

    public void setOffChildren(List<IndustryContributor> offChildren) {
        this.offChildren = offChildren;
    }
}
