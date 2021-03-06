package com.poliana.core.bills.entities;

import org.mongodb.morphia.annotations.Property;

/**
 * @author David Gilmore
 * @date 11/22/13
 */
public class BillTitle {

    private String as;

    @Property("is_for_portion")
    private Boolean isForPortion;

    private String title;
    private String type;

    public String getAs() {
        return as;
    }

    public void setAs(String as) {
        this.as = as;
    }

    public Boolean getForPortion() {
        return isForPortion;
    }

    public void setForPortion(Boolean forPortion) {
        isForPortion = forPortion;
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
