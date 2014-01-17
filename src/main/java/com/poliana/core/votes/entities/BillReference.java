package com.poliana.core.votes.entities;

import org.mongodb.morphia.annotations.Embedded;

/**
 * @author David Gilmore
 * @date 11/15/13
 */
@Embedded
public class BillReference {

    private String congress;
    private Integer number;
    private String title;
    private String type;

    public String getCongress() {
        return congress;
    }

    public void setCongress(String congress) {
        this.congress = congress;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
