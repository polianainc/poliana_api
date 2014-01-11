package com.poliana.core.amendments.entities;

import org.mongodb.morphia.annotations.Property;

/**
 * @author David Gilmore
 * @date 11/22/13
 */
public class AmendmentRef {
    @Property("amendment_id")
    private String amendmentId;
    @Property("amendment_type")
    private String amendmentType;
    private int congress;
    private int number;

    public String getAmendmentId() {
        return amendmentId;
    }

    public void setAmendmentId(String amendmentId) {
        this.amendmentId = amendmentId;
    }

    public String getAmendmentType() {
        return amendmentType;
    }

    public void setAmendmentType(String amendmentType) {
        this.amendmentType = amendmentType;
    }

    public int getCongress() {
        return congress;
    }

    public void setCongress(int congress) {
        this.congress = congress;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
