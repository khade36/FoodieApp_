package com.foodie.cart.domain;

public class Item {
    private String itemName;
    private int itemPrice;
    private String description;
    private int itemQty;

    public Item(String itemName, int itemPrice, String description, int itemQty) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.description = description;
        this.itemQty = itemQty;
    }

    public Item() {
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                ", description='" + description + '\'' +
                ", itemQty=" + itemQty +
                '}';
    }
}
