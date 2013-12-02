package com.poliana.legislation.entities.bills;

/**
 * @author David Gilmore
 * @date 11/22/13
 */
public class BillSummary {
    private String as;
    private int date;
    private String text;

    public String getAs() {
        return as;
    }

    public void setAs(String as) {
        this.as = as;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
