package com.foodie.favourite.domain;
import org.springframework.data.annotation.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class FavItem {

    private int favItemId;
    private String ownerId;
    private String restaurantName;
    private Item item;

    public FavItem(int favItemId, String ownerId, String restaurantName, Item item) {
        this.favItemId = favItemId;
        this.ownerId = ownerId;
        this.restaurantName = restaurantName;
        this.item = item;
    }

    public FavItem() {
    }

    public int getFavItemId() {
        return favItemId;
    }

    public void setFavItemId(int favItemId) {
        this.favItemId = favItemId;
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
                "favItemId=" + favItemId +
                ", ownerId='" + ownerId + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", item=" + item +
                '}';
    }
}
