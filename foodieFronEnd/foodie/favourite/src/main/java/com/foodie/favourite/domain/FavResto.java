package com.foodie.favourite.domain;

public class FavResto {
    private String ownerId;
    private Restaurant restaurant;

    public FavResto(String ownerId, Restaurant restaurant) {
        this.ownerId = ownerId;
        this.restaurant = restaurant;
    }

    public FavResto() {
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
                "ownerId='" + ownerId + '\'' +
                ", restaurant=" + restaurant +
                '}';
    }
}
