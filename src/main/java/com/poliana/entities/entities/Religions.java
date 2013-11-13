package com.poliana.entities.entities;

import java.util.HashMap;

/**
 * @author David Gilmore
 * @date 11/4/13
 */
public class Religions {
    private HashMap<String, String> religions;

    public Religions() {
        this.religions = new HashMap<String, String>(40);

        this.religions.put("Reformed Latter Day Saint","");
        this.religions.put("Nazarene","");
        this.religions.put("Unknown","");
        this.religions.put("Methodist","");
        this.religions.put("Episcopalian","");
        this.religions.put("Protestant","");
        this.religions.put("United Church of Christ","");
        this.religions.put("Assembly of God","");
        this.religions.put("Roman Catholic","");
        this.religions.put("Lutheran","");
        this.religions.put("African M\"ethodist Episcopal","");
        this.religions.put("SeventhDay Adventist","");
        this.religions.put("Episcopal","");
        this.religions.put("NonDenominational","");
        this.religions.put("Unitarian Universalist","");
        this.religions.put("Second Baptist","");
        this.religions.put("Southern Baptist","");
        this.religions.put("Baptist","");
        this.religions.put("U\"nited Methodist\"","");
        this.religions.put("Greek Orthodox","");
        this.religions.put("Christian Reformed","");
        this.religions.put("Reformed Church in America","");
        this.religions.put("Catholic","");
        this.religions.put("United Methodist","");
        this.religions.put("Presbyterian","");
        this.religions.put("First Christian Church (Disciples of Christ","");
        this.religions.put("Christian Scientist","");
        this.religions.put("United Brethren in Christ","");
        this.religions.put("Zion Lutheran","");
        this.religions.put("Unitarian","");
        this.religions.put("Jewish","");
        this.religions.put("African Methodist Episcopal","");
        this.religions.put("Christian","");
        this.religions.put("Latter Day Saints","");
        this.religions.put("Seventh Day Adventist","");
        this.religions.put("Church of Christ","");
        this.religions.put("Congregationalist","");
        this.religions.put("Moravian","");

    }

    public HashMap<String, String> getReligions() {
        return religions;
    }

    public void setReligions(HashMap<String, String> religions) {
        this.religions = religions;
    }
}
