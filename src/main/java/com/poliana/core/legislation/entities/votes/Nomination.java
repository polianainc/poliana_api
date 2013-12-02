package com.poliana.core.legislation.entities.votes;

import com.google.code.morphia.annotations.Embedded;

/**
 * @author David Gilmore
 * @date 11/13/13
 */
@Embedded
public class Nomination {
    private String number;
    private String title;

    public String getNumber() {
        return number;
    }

    public void setNumString(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}