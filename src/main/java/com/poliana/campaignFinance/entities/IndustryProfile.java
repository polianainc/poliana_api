package com.poliana.campaignFinance.entities;

import com.google.code.morphia.annotations.Reference;
import com.poliana.campaignFinance.entities.IndustryContrTotals;
import com.poliana.entities.entities.Industry;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * @author David Gilmore
 * @date 11/15/13
 */

public class IndustryProfile {

    @Id
    private ObjectId id;

    @Reference
    private Industry meta;
    @Reference("contribution_totals")
    private IndustryContrTotals contributionTotals;

}
