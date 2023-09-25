package com.foodie.favourite.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class User {
    @Id
    private String emailId;
    private List<FavResto> favResto;
    private List<FavItem> favItem;

    public User(String emailId, List<FavResto> favResto, List<FavItem> favItem) {
        this.emailId = emailId;
        this.favResto = favResto;
        this.favItem = favItem;
    }

    public User() {
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public List<FavResto> getFavResto() {
        return favResto;
    }

    public void setFavResto(List<FavResto> favResto) {
        this.favResto = favResto;
    }

    public List<FavItem> getFavItem() {
        return favItem;
    }

    public void setFavItem(List<FavItem> favItem) {
        this.favItem = favItem;
    }

    @Override
    public String toString() {
        return "User{" +
                "emailId='" + emailId + '\'' +
                ", favResto=" + favResto +
                ", favItem=" + favItem +
                '}';
    }
}
