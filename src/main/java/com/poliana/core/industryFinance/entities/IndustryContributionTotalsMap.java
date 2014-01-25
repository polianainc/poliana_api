package com.poliana.core.industryFinance.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.util.HashMap;

/**
 * @author David Gilmore
 * @date 1/6/14
 */
@Entity("industry_contribution_totals")
@JsonIgnoreProperties({"id"})
public class IndustryContributionTotalsMap {

    @Id
    private String id;

    @Property("industry_id")
    private String industryId;

    @Property("category_id")
    private String categoryId;

    @Property("industry_name")
    private String industryName;

    private String sector;

    @Property("sector_long")
    private String sectorLong;

    @Property("category_name")
    private String categoryName;

    private String chamber;
    private Integer congress;

    @Property("begin_timestamp")
    private Long beginTimestamp;

    @Property("end_timestamp")
    private Long endTimestamp;

    @Embedded
    private HashMap<String, Integer> sums;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public Integer getCongress() {
        return congress;
    }

    public void setCongress(Integer congress) {
        this.congress = congress;
    }

    public Long getBeginTimestamp() {
        return beginTimestamp;
    }

    public void setBeginTimestamp(Long beginTimestamp) {
        this.beginTimestamp = beginTimestamp;
    }

    public Long getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public HashMap<String, Integer> getSums() {
        return sums;
    }

    public void setSums(HashMap<String, Integer> sums) {
        this.sums = sums;
    }
}
