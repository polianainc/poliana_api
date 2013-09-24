package com.poliana.bills.entities;

public class BillCoSponsor {
    private String district;
    private String name;
    private String sponsoredAt;
    private String state;
    private String thomasId;
    private String title;
    private String withdrawnAt;

    public BillCoSponsor() {}

    public BillCoSponsor(String name) {
        this.name = name;
    }

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

    public String getThomasId() {
        return thomasId;
    }

    public void setThomasId(String thomasId) {
        this.thomasId = thomasId;
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
