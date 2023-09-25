package com.foodie.favourite.service;

import com.foodie.favourite.domain.FavItem;
import com.foodie.favourite.domain.FavResto;
import com.foodie.favourite.domain.Item;
import com.foodie.favourite.domain.Restaurant;
import com.foodie.favourite.exception.RestaurantAlreadyExistException;
import com.foodie.favourite.exception.UserNotFoundExistException;

import java.util.List;

public interface UserFavService {
    public String saveFavResto(String emailId,FavResto favResto) throws UserNotFoundExistException, RestaurantAlreadyExistException;
    public String saveFavItem(String emailId,FavItem favItem) throws UserNotFoundExistException;
    public List<FavResto> getFavResto(String emailId) throws UserNotFoundExistException;
    public List<FavItem> getFavItem(String emailId) throws UserNotFoundExistException;

}
