package com.foodie.cart.service;

import com.foodie.cart.domain.Cart;
import com.foodie.cart.domain.Item;
import com.foodie.cart.domain.User;
import com.foodie.cart.exception.ItemAlreayExistException;
import com.foodie.cart.exception.ItemNotFoundException;
import com.foodie.cart.exception.UserAlreayExistException;
import com.foodie.cart.exception.UserNotFoundException;

import java.util.List;

public interface UserCartService {
    User saveUser(User user) throws UserAlreayExistException;
    Cart saveItemToCart(String emailId,String ownerId,String restaurantName,Item item) throws UserNotFoundException, ItemAlreayExistException;
    boolean deleteItemFromCart(String emailId,String itemName) throws UserNotFoundException, ItemNotFoundException;
    Cart getCart(String emailId) throws UserNotFoundException;
    Cart updateQty(String emailId,String itemName,int itemQty) throws UserNotFoundException, ItemNotFoundException;
}
