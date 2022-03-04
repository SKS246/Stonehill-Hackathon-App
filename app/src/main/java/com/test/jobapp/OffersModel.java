package com.test.jobapp;

public class OffersModel {
    String Add, AskedFor, Msg, Publisher, UserNum, Username;
    OffersModel()
    {

    }

    public OffersModel(String add, String askedFor, String msg, String publisher, String userNum, String username) {
        Add = add;
        AskedFor = askedFor;
        Msg = msg;
        Publisher = publisher;
        UserNum = userNum;
        Username = username;
    }

    public String getAdd() {
        return Add;
    }

    public void setAdd(String add) {
        Add = add;
    }

    public String getAskedFor() {
        return AskedFor;
    }

    public void setAskedFor(String askedFor) {
        AskedFor = askedFor;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getUserNum() {
        return UserNum;
    }

    public void setUserNum(String userNum) {
        UserNum = userNum;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
