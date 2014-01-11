package com.poliana.core.bills.entities;

import org.mongodb.morphia.annotations.Property;

/**
 * @author David Gilmore
 * @date 11/22/13
 */
public class BillSummary {
    private String as;
    @Property("date_ts")
    private long dateTs;
    @Property("date_string")
    private String dateString;
    private String text;

    public String getAs() {
        return as;
    }

    public void setAs(String as) {
        this.as = as;
    }

    public long getDateTs() {
        return dateTs;
    }

    public void setDateTs(long dateTs) {
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
