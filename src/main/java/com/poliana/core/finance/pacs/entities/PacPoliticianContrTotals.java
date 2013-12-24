package com.poliana.core.finance.pacs.entities;

import com.google.code.morphia.annotations.Property;

import java.util.List;

/**
 * @author David Gilmore
 * @date 12/19/13
 */
public class PacPoliticianContrTotals {

    @Property("pac_id")
    public String pacId;
    @Property("pac_name")
    public List<String> pacNames;
    @Property("pac_city")
    public String pacCity;
    @Property("pac_state")
    public String pacState;
    public int amount;

    public String getPacId() {
        return pacId;
    }

    public void setPacId(String pacId) {
        this.pacId = pacId;
    }

    public List<String> getPacNames() {
        return pacNames;
    }

    public void setPacNames(List<String> pacNames) {
        this.pacNames = pacNames;
    }

    public String getPacCity() {
        return pacCity;
    }

    public void setPacCity(String pacCity) {
        this.pacCity = pacCity;
    }

    public String getPacState() {
        return pacState;
    }

    public void setPacState(String pacState) {
        this.pacState = pacState;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
