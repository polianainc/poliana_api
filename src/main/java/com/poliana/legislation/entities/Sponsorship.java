package com.poliana.legislation.entities;

/**
 * @author David Gilmore
 * @date 11/27/13
 */
public class Sponsorship {

    private String chamber;
    private String sponsor;
    private String cosponsor;
    private int count;
    private int congress;

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getCosponsor() {
        return cosponsor;
    }

    public void setCosponsor(String cosponsor) {
        this.cosponsor = cosponsor;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCongress() {
        return congress;
    }

    public void setCongress(int congress) {
        this.congress = congress;
    }
}
