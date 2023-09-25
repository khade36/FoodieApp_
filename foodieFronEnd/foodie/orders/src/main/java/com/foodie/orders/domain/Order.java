package com.foodie.orders.domain;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Order {
    private int orderId;
    private String ownerId;
    private String restaurantName;
    private Address address;
    private List<Item> item;

    public Order(int orderId, String ownerId, String restaurantName, Address address, List<Item> item) {
        this.orderId = orderId;
        ownerId = ownerId;
        this.restaurantName = restaurantName;
        this.address = address;
        this.item = item;
    }

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Order{" +
                "OwnerId='" + ownerId + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", address=" + address +
                ", item=" + item +
                '}';
    }
}
