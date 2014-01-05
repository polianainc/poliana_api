package com.poliana.core.bills.entities;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import com.google.gson.annotations.Expose;
import org.bson.types.ObjectId;

@Entity("congressional_committees")
public class CongCommittee {

    @Id
    @Expose
    private ObjectId id;

    private String code;
    @Property("committee_name")
    private String committeeName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }
}
