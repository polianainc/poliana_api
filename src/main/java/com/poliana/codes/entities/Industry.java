package com.poliana.codes.entities;

public class Industry {
    private String catCode;
    private String catName;
    private String catOrder;
    private String industry;
    private String sector;
    private String sectorLong;

    public String getCatCode() {
        return catCode;
    }

    public void setCatCode(String catCode) {
        this.catCode = catCode;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatOrder() {
        return catOrder;
    }

    public void setCatOrder(String catOrder) {
        this.catOrder = catOrder;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getSectorLong() {
        return sectorLong;
    }

    public void setSectorLong(String sectorLong) {
        this.sectorLong = sectorLong;
    }
}
