package com.poliana.core.industryFinance.entities;

import org.mongodb.morphia.annotations.Property;

/**
 * @author David Gilmore
 * @date 12/14/13
 */
public class IndustryPoliticianContributions {

    @Property("industry_id")
    private String industryId;
    private String catName;
    private String industry;
    @Property("sector_long")
    private String sectorLong;
    private int amount;

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getSectorLong() {
        return sectorLong;
    }

    public void setSectorLong(String sectorLong) {
        this.sectorLong = sectorLong;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
