package com.poliana.core.votes.entities;

import org.mongodb.morphia.annotations.Embedded;

/**
 * @author David Gilmore
 * @date 11/15/13
 */
@Embedded
public class VoteAmendmentRef {
    private int number;
    private String purpose;
    private String type;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
