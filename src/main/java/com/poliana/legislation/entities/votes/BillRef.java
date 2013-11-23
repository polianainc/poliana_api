package com.poliana.legislation.entities.votes;

import com.google.code.morphia.annotations.Embedded;

/**
 * @author David Gilmore
 * @date 11/15/13
 */
@Embedded
public class BillRef {
    private String congress;
    private String number;
    private String title;
    private String type;

    public String getCongress() {
        return congress;
    }

    public void setCongress(String congress) {
        this.congress = congress;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
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
