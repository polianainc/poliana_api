package com.poliana.web.serialize;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author David Gilmore
 * @date 4/7/14
 */
@JsonIgnoreProperties( {
          "id"
        , "bioguideId"
        , "firstName"
        , "lastName"
        , "party"
        , "religion"
        , "congress"
        , "beginTimestamp"
        , "endTimestamp"
})
@JsonPropertyOrder(value={
          ""
        , "pacId"
        , "pacName"
        , "contributionCount"
        , "contributionSum"
})
public interface PacSummaryFilter {}
