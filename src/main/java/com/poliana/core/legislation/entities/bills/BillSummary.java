package com.poliana.core.legislation.entities.bills;

import com.google.code.morphia.annotations.Property;

/**
 * @author David Gilmore
 * @date 11/22/13
 */
public class BillSummary {
    private String as;
    @Property("date_ts")
    private int dateTs;
    @Property("date_string")
    private String dateString;
    private String text;

    public String getAs() {
        return as;
    }

    public void setAs(String as) {
        this.as = as;
    }

    public int getDateTs() {
        return dateTs;
    }

    public void setDateTs(int dateTs) {
        this.dateTs = dateTs;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
