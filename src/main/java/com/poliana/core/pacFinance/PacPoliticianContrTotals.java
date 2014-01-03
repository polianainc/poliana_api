package com.poliana.core.pacFinance;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Property;

/**
 * @author David Gilmore
 * @date 12/19/13
 */
@Entity("pac_to_politician_contribution_sums")
public class PacPoliticianContrTotals {

    @Id
    private String id;

    @Property("pac_id")
    private String pacId;

    @Property("pac_name")
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
