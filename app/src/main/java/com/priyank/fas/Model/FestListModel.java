package com.priyank.fas.Model;

public class FestListModel {

    String festName, festDate;
    int festId;

    public FestListModel(int festId, String festName, String festDate) {
        this.festId = festId;
        this.festName = festName;
        this.festDate = festDate;
    }

    public int getFestId() {
        return festId;
    }

    public void setFestId(int festId) {
        this.festId = festId;
    }

    public String getFestName() {
        return festName;
    }

    public void setFestName(String festName) {
        this.festName = festName;
    }

    public String getFestDate() {
        return festDate;
    }

    public void setFestDate(String festDate) {
        this.festDate = festDate;
    }

}
