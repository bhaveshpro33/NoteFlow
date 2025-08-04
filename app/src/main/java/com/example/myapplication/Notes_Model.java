package com.example.myapplication;

public class Notes_Model {
    String date;
    String title;
    String note;

    String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Notes_Model() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Notes_Model(String date, String title, String note,String key) {
        this.date = date;
        this.title = title;
        this.note = note;
        this.key=key;
    }
}
