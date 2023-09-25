package com.foodie.restaurant.domain;

public class User {
    private String emailId;

    public User(String emailId) {
        this.emailId = emailId;
    }

    public User() {
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Override
    public String toString() {
        return "User{" +
                "emailId='" + emailId + '\'' +
                '}';
    }
}
