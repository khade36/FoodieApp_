package com.foodie.restaurant.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.List;

@Document
public class RestoOwner {
    @Id
    private String ownerId;
    private String ownerName;
    private List<Restaurant> restaurant;

    public RestoOwner(String ownerId, String ownerName, List<Restaurant> restaurant) {
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.restaurant = restaurant;
    }

    public RestoOwner() {
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public List<Restaurant> getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(List<Restaurant> restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "RestoOwner{" +
                "ownerId='" + ownerId + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", restaurant=" + restaurant +
                '}';
    }
}
