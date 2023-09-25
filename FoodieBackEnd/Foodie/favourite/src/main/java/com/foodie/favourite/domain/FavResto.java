package com.foodie.favourite.domain;

import org.springframework.data.annotation.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class FavResto {

    private int favRestoId;
    private String ownerId;
    private Restaurant restaurant;

    public FavResto(int favRestoId, String ownerId, Restaurant restaurant) {
        this.favRestoId = favRestoId;
        this.ownerId = ownerId;
        this.restaurant = restaurant;
    }

    public FavResto() {
    }

    public int getFavRestoId() {
        return favRestoId;
    }

    public void setFavRestoId(int favRestoId) {
        this.favRestoId = favRestoId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "FavResto{" +
                "favRestoId=" + favRestoId +
                ", ownerId='" + ownerId + '\'' +
                ", restaurant=" + restaurant +
                '}';
    }
}
