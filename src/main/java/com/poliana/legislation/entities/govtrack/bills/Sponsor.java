package com.poliana.legislation.entities.govtrack.bills;

import com.google.code.morphia.annotations.Property;

/**
 * @author David Gilmore
 * @date 11/22/13
 */
public class Sponsor {
    private String district;
    private String name;
    @Property("sponsored_at")
    private String sponsoredAt;
    private String state;
    private String id;
    private String title;
    @Property("withdrawn_at")
    private String withdrawnAt;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSponsoredAt() {
        return sponsoredAt;
    }

    public void setSponsoredAt(String sponsoredAt) {
        this.sponsoredAt = sponsoredAt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWithdrawnAt() {
        return withdrawnAt;
    }

    public void setWithdrawnAt(String withdrawnAt) {
        this.withdrawnAt = withdrawnAt;
    }
}
