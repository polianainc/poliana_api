package com.poliana.entities.models;

import com.poliana.campaignFinance.entities.IndustryContrTotals;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Map;

/**
 * @author David Gilmore
 * @date 11/15/13
 */
@SuppressWarnings("serial")
@Document(collection = "industry_profiles")
public class IndustryProfile {

    @Id
    private String id;

    @DBRef
    private Industry meta;
    @DBRef
    private Map<String, IndustryContrTotals> contributionTotals;

}
