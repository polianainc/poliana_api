package com.poliana.legislation.entities.amendments;

import com.google.code.morphia.annotations.Property;

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
