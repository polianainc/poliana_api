package com.poliana.entities.entities;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Property;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * @author David Gilmore
 * @date 11/12/13
 */
@Entity("industries")
public class Industry {

    @Id
    private ObjectId id;

    @Property("category_id")
    private String categoryId;
    private String name;
    private String order;
    private String industry;
    private String sector;
    @Property("sector_long")
    private String sectorLong;

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

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
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
