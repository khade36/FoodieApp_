package com.foodie.restaurant.domain;

public class User {
    private String emailId;
    private String firstName;

    public User(String emailId, String firstName) {
        this.emailId = emailId;
        this.firstName = firstName;
    }

    public User() {
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "User{" +
                "emailId='" + emailId + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
