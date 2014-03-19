package com.poliana.core.legislators;

import org.msgpack.annotation.Message;

import java.util.LinkedList;

/**
 * @author graysoncarroll
 * @date 3/19/14
 */
@Message
public class LegislatorCondensedTerm {
    private Long beginTimestamp;
    private Long endTimestamp;
    private String termType;
    private LinkedList<Integer> congresses;

    long getBeginTimestamp() {
        return beginTimestamp;
    }

    void setBeginTimestamp(long beginTimestamp) {
        this.beginTimestamp = beginTimestamp;
    }

    Long getEndTimestamp() {
        return endTimestamp;
    }

    void setEndTimestamp(Long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    String getTermType() {
        return termType;
    }

    void setTermType(String termType) {
        this.termType = termType;
    }

    LinkedList<Integer> getCongresses() {
        return congresses;
    }

    void setCongresses(LinkedList<Integer> congresses) {
        this.congresses = congresses;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        final LegislatorCondensedTerm other = (LegislatorCondensedTerm) obj;

        return this.beginTimestamp == other.getBeginTimestamp();
    }
}