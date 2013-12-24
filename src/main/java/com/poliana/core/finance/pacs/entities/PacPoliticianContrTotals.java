package com.poliana.core.finance.pacs.entities;

import com.google.code.morphia.annotations.Property;

/**
 * @author David Gilmore
 * @date 12/19/13
 */
public class PacPoliticianContrTotals {

    @Property("pac_id")
    public String pacId;
    @Property("pac_name")
    public String pacName;
    @Property("pac_party")
    public String pacParty;
    @Property("pac_type")
    public String pacType;
    @Property("pac_org")
    public String pacOrg;
    @Property("org_type")
    public String orgType;
    public int amount;

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

    public String getPacParty() {
        return pacParty;
    }

    public void setPacParty(String pacParty) {
        this.pacParty = pacParty;
    }

    public String getPacType() {
        return pacType;
    }

    public void setPacType(String pacType) {
        this.pacType = pacType;
    }

    public String getPacOrg() {
        return pacOrg;
    }

    public void setPacOrg(String pacOrg) {
        this.pacOrg = pacOrg;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
