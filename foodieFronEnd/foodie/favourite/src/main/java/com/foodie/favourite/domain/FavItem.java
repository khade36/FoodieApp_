package com.foodie.favourite.domain;

import java.util.PrimitiveIterator;

public class FavItem {
    private String ownerId;
    private String restaurantName;
    private Item item;

    public FavItem(String ownerId, String restaurantName, Item item) {
        this.ownerId = ownerId;
        this.restaurantName = restaurantName;
        this.item = item;
    }

    public FavItem() {
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "FavItem{" +
                "ownerId='" + ownerId + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", item=" + item +
                '}';
    }
}
