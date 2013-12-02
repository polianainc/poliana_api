package com.poliana.legislation.entities.bills;

import com.google.code.morphia.annotations.Property;

/**
 * @author David Gilmore
 * @date 11/22/13
 */
public class BillTitle {
    private String as;
    @Property("is_for_portion")
    private String isForPortion;
    private String title;
    private String type;

    public String getAs() {
        return as;
    }

    public void setAs(String as) {
        this.as = as;
    }

    public String getForPortion() {
        return isForPortion;
    }

    public void setForPortion(String forPortion) {
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
