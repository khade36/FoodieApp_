package com.foodie.cart.domain;

import java.util.List;

public class Cart {
    private String ownerId;
    private String restaurantName;
    private List<Item> item;

    public Cart(String ownerId, String restaurantName, List<Item> item) {
        this.ownerId = ownerId;
        this.restaurantName = restaurantName;
        this.item = item;
    }

    public Cart() {
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "ownerId='" + ownerId + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", item=" + item +
                '}';
    }
}
