package com.poliana.core.pacFinance;

/**
 * @author David Gilmore
 * @date 12/19/13
 */
public class PacPoliticianContrTotals {

    private String pacId;
    private String pacName;
    private int cycle;
    private int amount;

    public String getPacId() {
        return pacId;
    }

    public void setPacId(String pacId) {
        this.pacId = pacId;
    }

    public String getPacName() {
        return pacName;
    }

    public void setPacName(String pacName) {
        this.pacName = pacName;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
