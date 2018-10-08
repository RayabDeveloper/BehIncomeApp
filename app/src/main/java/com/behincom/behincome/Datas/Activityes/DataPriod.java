package com.behincom.behincome.Datas.Activityes;

public class DataPriod {

    private int id, account_id;
    private String name, date_from, date_to, description, creation_date_time;

    public int id() {
        return id;
    }
    public void id(int id) {
        this.id = id;
    }

    public int account_id() {
        return account_id;
    }
    public void account_id(int account_id) {
        this.account_id = account_id;
    }

    public String name() {
        return name;
    }
    public void name(String name) {
        this.name = name;
    }

    public String date_from() {
        return date_from;
    }
    public void date_from(String date_from) {
        this.date_from = date_from;
    }

    public String date_to() {
        return date_to;
    }
    public void date_to(String date_to) {
        this.date_to = date_to;
    }

    public String description() {
        return description;
    }
    public void description(String description) {
        this.description = description;
    }

    public String creation_date_time() {
        return creation_date_time;
    }
    public void creation_date_time(String creation_date_time) {
        this.creation_date_time = creation_date_time;
    }

}
