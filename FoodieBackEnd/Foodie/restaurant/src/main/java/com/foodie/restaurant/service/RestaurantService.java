package com.foodie.restaurant.service;



import com.foodie.restaurant.domain.Item;
import com.foodie.restaurant.domain.Restaurant;
import com.foodie.restaurant.domain.RestoOwner;
import com.foodie.restaurant.domain.User;
import com.foodie.restaurant.exception.*;

import java.util.List;

public interface RestaurantService {
    RestoOwner saveRestoOwnerToList(User restoOwner) throws RestaurantOwnerAlreadyExistException;
    Restaurant saveRestoToList(String ownerId, Restaurant restaurant) throws RestoOwnerNotFoundException, RestaurantAlreadyExistException;
    RestoOwner saveItemToList(String ownerId, String restaurantName, Item item) throws RestoOwnerNotFoundException, RestaurantNotFoundException, ItemAlreayExistException;
    boolean deleteRestoOwnerFromList(String ownerId) throws RestoOwnerNotFoundException;
    RestoOwner deleteRestoFromList(String ownerId,String restaurantName) throws RestoOwnerNotFoundException, RestaurantNotFoundException;
    boolean deleteItemFromList(String ownerId,String restaurantName,String itemName) throws RestoOwnerNotFoundException,RestaurantNotFoundException, ItemNotFoundException;
    List<RestoOwner> getAllRestoOwner();
    List<Restaurant> getAllResto(String ownerId) throws RestoOwnerNotFoundException;
    List<Item> getAllRestoItems(String ownerId,String restaurantName) throws RestoOwnerNotFoundException,RestaurantNotFoundException;
    List<Restaurant> getAllRestoForAll();
    List<Restaurant> getRestoFormAll(String restaurantName);
    List<Restaurant> getItemFormAll(String itemName);
    String updateItem(String ownerId,String restaurantName,String itemName,int newPrice) throws RestoOwnerNotFoundException, RestaurantNotFoundException, ItemNotFoundException;
    String changeRestoStatus(String emailId, String restaurantName) throws RestoOwnerNotFoundException, RestaurantNotFoundException;
}
