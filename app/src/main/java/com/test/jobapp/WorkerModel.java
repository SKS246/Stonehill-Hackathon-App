package com.test.jobapp;

public class WorkerModel {
    String Username, Bio, Prof, questionImage, AskedBy, UserNum, Publisher;
    WorkerModel()
    {

    }

    public WorkerModel(String username, String bio, String prof, String questionImage, String askedBy, String userNum, String publisher) {
        Username = username;
        Bio = bio;
        Prof = prof;
        this.questionImage = questionImage;
        AskedBy = askedBy;
        UserNum = userNum;
        Publisher = publisher;
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

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public String getProf() {
        return Prof;
    }

    public void setProf(String prof) {
        Prof = prof;
    }

    public String getQuestionImage() {
        return questionImage;
    }

    public void setQuestionImage(String questionImage) {
        this.questionImage = questionImage;
    }

    public String getAskedBy() {
        return AskedBy;
    }

    public void setAskedBy(String askedBy) {
        AskedBy = askedBy;
    }
}
