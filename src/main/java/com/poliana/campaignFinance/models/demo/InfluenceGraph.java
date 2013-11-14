package com.poliana.campaignFinance.models.demo;

public class InfluenceGraph {
    private String id;
    private String winner;
    private IndustryInfl data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public IndustryInfl getData() {
        return data;
    }

    public void setData(IndustryInfl data) {
        this.data = data;
    }
}
