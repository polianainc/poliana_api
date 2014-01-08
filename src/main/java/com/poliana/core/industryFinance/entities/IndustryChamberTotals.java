package com.poliana.core.industryFinance.entities;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.util.HashMap;

/**
 * @author David Gilmore
 * @date 1/6/14
 */
@Entity("industry_chamber_totals")
public class IndustryChamberTotals {

    @Id
    private String id;

    @Property("industry_id")
    private String industryId;

    @Property("category_id")
    private String categoryId;

    @Property("name")
    private String name;

    private String chamber;
    private int congress;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public int getCongress() {
        return congress;
    }

    public void setCongress(int congress) {
        this.congress = congress;
    }

    public HashMap<String, Integer> getSums() {
        return sums;
    }

    public void setSums(HashMap<String, Integer> sums) {
        this.sums = sums;
    }
}
