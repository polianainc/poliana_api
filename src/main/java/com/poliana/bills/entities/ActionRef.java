package com.poliana.bills.entities;

public class ActionRef {
    private String reference;
    private String type;

    public ActionRef(String reference, String type) {
        this.reference = reference;
        this.type = type;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
