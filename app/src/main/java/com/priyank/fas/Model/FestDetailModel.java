package com.priyank.fas.Model;

public class FestDetailModel {

    String itemName, date;
    int id, festId, credit, debit;

    public FestDetailModel(int id, int festId, String itemName, String date, int credit, int debit) {
        this.id = id;
        this.festId = festId;
        this.itemName = itemName;
        this.date = date;
        this.credit = credit;
        this.debit = debit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFestId() {
        return festId;
    }

    public void setFestId(int festId) {
        this.festId = festId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getDebit() {
        return debit;
    }

    public void setDebit(int debit) {
        this.debit = debit;
    }
}
