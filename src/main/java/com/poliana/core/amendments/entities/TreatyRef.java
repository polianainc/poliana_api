package com.poliana.core.amendments.entities;

import org.mongodb.morphia.annotations.Property;

/**
 * @author David Gilmore
 * @date 11/22/13
 */
public class TreatyRef {
    private int congress;
    private int number;
    @Property("treaty_id")
    private String treatyId;
}
