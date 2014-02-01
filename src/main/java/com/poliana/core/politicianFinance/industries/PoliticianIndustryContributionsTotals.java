package com.poliana.core.politicianFinance.industries;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

/**
 * The Industry to Politician Contribution Totals object is mapped to SQL queries and a MongoDB document. It is
 * used to store totals contribution sums from a industry to a politician over a certain time range.
 *
 * @author David Gilmore
 * @date 11/1/13
 */
@Entity("industry_to_politician_contribution_totals")
@JsonIgnoreProperties({"id"})
public class PoliticianIndustryContributionsTotals {

    @Id
    private String id;

    @Property("bioguide_id")
    private transient String bioguideId;

    @Property("first_name")
    private String firstName;

    @Property("last_name")
    private String lastName;

    private String party;
    private String religion;

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

    @Property("contribution_count")
    private Integer contributionCount;

    @Property("contribution_sum")
    private Integer contributionSum;

    @Property("begin_timestamp")
    private Long beginTimestamp;

    @Property("end_timestamp")
    private Long endTimestamp;

    private Integer congress;
    private Integer year;
    private Integer month;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getBioguideId() {
        return bioguideId;
    }

    public void setBioguideId(String bioguideId) {
        this.bioguideId = bioguideId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
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

    public String getSectorLong() {
        return sectorLong;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setSectorLong(String sectorLong) {
        this.sectorLong = sectorLong;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public Integer getContributionCount() {
        return contributionCount;
    }

    public void setContributionCount(Integer contributionsCount) {
        this.contributionCount = contributionsCount;
    }

    public Integer getContributionSum() {
        return contributionSum;
    }

    public void setContributionSum(Integer contributionSum) {
        this.contributionSum = contributionSum;
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

    public Integer getCongress() {
        return congress;
    }

    public void setCongress(Integer congress) {
        this.congress = congress;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }
}
