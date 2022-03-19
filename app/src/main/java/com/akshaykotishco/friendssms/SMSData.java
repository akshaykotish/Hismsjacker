package com.akshaykotishco.friendssms;

import java.util.List;

public class SMSData {

    // variables for storing our data.
    long id;
    long threadId;
    String address;
    String person;
    String date;
    String body;

    public SMSData() {
        // empty constructor
        // required for Firebase.
    }


    public SMSData(long id, long threadId, String address, String person, String date, String body) {
        this.id = id;
        this.threadId = threadId;
        this.address = address;
        this.person = person;
        this.date = date;
        this.body = body;
    }

    // getter methods for all variables.
    public long getId() {
        return id;
    }

    public long getthreadId() {
        return threadId;
    }

    public String getaddress() {
        return address;
    }

    public String getperson() {
        return person;
    }

    public String getdate() {
        return date;
    }

    public String getbody() {
        return body;
    }

    // setter method for all variables.
    public void setId(long id_) {
        this.id = id_;
    }

    public void setthreadId(long threadId_) {
        this.threadId = threadId_;
    }

    public void setaddress(String address_) {
        this.address = address_;
    }

    public void setperson(String person_) {
        this.person = person;
    }

    public void setdate(String date_) {
        this.date = date_;
    }

    public void setbody(String body_) {
        body = body_;
    }
}