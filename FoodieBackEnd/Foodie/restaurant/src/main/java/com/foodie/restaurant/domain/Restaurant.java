package com.foodie.restaurant.domain;

import javax.persistence.Id;
import java.util.List;

public class Restaurant {
    @Id
    private String restaurantName;
    private double rating;
    private String location;
    private boolean status;
    private List<Item> item;
    private String restoImage;

    public Restaurant(String restaurantName, double rating, String location, boolean status, List<Item> item, String restoImage) {
        this.restaurantName = restaurantName;
        this.rating = rating;
        this.location = location;
        this.status = status;
        this.item = item;
        this.restoImage = restoImage;
    }

    public Restaurant() {
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public String getRestoImage() {
        return restoImage;
    }

    public void setRestoImage(String restoImage) {
        this.restoImage = restoImage;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantName='" + restaurantName + '\'' +
                ", rating=" + rating +
                ", location='" + location + '\'' +
                ", status=" + status +
                ", item=" + item +
                ", restoImage='" + restoImage + '\'' +
                '}';
    }
}
