package com.foodie.favourite.service;

import com.foodie.favourite.domain.*;
import com.foodie.favourite.exception.RestaurantAlreadyExistException;
import com.foodie.favourite.exception.UserAlreadyExistsException;
import com.foodie.favourite.exception.UserNotFoundExistException;
import com.foodie.favourite.repositary.UserFavRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserFavServiceImpl implements UserFavService
{
private UserFavRepositary userFavRepositary;
     @Autowired
    public UserFavServiceImpl(UserFavRepositary userFavRepositary) {
        this.userFavRepositary = userFavRepositary;
    }

    public UserFavServiceImpl() {
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
        if(userFavRepositary.findById(user.getEmailId()).isPresent())
        {
            throw new UserAlreadyExistsException();
        }
        userFavRepositary.save(user);
        System.out.println("FavDone");
        return user;
    }


    @Override
    public String saveFavResto(String emailId, FavResto favResto1) throws UserNotFoundExistException, RestaurantAlreadyExistException {
        String ownerId=favResto1.getOwnerId();
        Restaurant restaurant=favResto1.getRestaurant();
         if(userFavRepositary.findById(emailId).isEmpty())
        {
            throw new UserNotFoundExistException();
        }
        User user=userFavRepositary.findById(emailId).get();
        System.out.println(user);

        if(user.getFavResto()==null || user.getFavResto().size()==0)
        {

            FavResto favResto=new FavResto();
            favResto.setFavRestoId(1);
            favResto.setOwnerId(ownerId);
            favResto.setRestaurant(restaurant);
            userFavRepositary.delete(user);
            user.setFavResto(Arrays.asList(favResto));
            userFavRepositary.save(user);
            return "Restaurant Added Successfully";
        }
        else
        {
            List<FavResto> favRestoList = user.getFavResto();
            int lastId=0;
            lastId = (favRestoList.get(favRestoList.size() - 1)).getFavRestoId();
            favResto1.setFavRestoId(lastId+1);
            boolean flag=true;
            for(FavResto favResto:favRestoList)
            {
                if(favResto.getOwnerId().equals(ownerId) && favResto.getRestaurant().getRestaurantName().equals(restaurant.getRestaurantName()))
                {
                    flag=false;
                }

            }
            if(flag)
            {
                FavResto favResto = new FavResto();
                favResto.setFavRestoId(lastId+1);
                favResto.setOwnerId(ownerId);
                favResto.setRestaurant(restaurant);
                favRestoList.add(favResto);
                userFavRepositary.delete(user);
                user.setFavResto(favRestoList);
                userFavRepositary.save(user);
                return "Restaurant Added Successfully";
            }
            else
            {
                return "Restaurant Already added";
            }
        }
    }


    @Override
    public String saveFavItem(String emailId, FavItem favItem1) throws UserNotFoundExistException {
         String ownerId=favItem1.getOwnerId();
         String restaurantName=favItem1.getRestaurantName();
         Item item=favItem1.getItem();
        if(userFavRepositary.findById(emailId).isEmpty())
        {
            throw new UserNotFoundExistException();
        }
        User user=userFavRepositary.findById(emailId).get();
        if(user.getFavItem()==null || user.getFavItem().size()==0)
        {

            FavItem favItem=new FavItem();
            favItem.setFavItemId(1);
            favItem.setOwnerId(ownerId);
            favItem.setRestaurantName(restaurantName);
            favItem.setItem(item);
            userFavRepositary.delete(user);
            user.setFavItem(Arrays.asList(favItem));
            userFavRepositary.save(user);
            return "Item added successfully";
        }
        else
        {
            List<FavItem>favItemList=user.getFavItem();
            int lastId=0;
            lastId = (favItemList.get(favItemList.size() - 1)).getFavItemId();
            favItem1.setFavItemId(lastId+1);
            boolean flag=true;
            for(FavItem favItem:favItemList)
            {
                if(favItem.getOwnerId().equals(ownerId)&&favItem.getRestaurantName().equals(restaurantName)&&favItem.getItem().getItemName().equals(item.getItemName()))
                {
                    flag=false;
                }
            }
            if(flag)
            {
                FavItem favItem=new FavItem();
                favItem.setFavItemId(lastId+1);
                favItem.setOwnerId(ownerId);
                favItem.setRestaurantName(restaurantName);
                favItem.setItem(item);
                favItemList.add(favItem);
                userFavRepositary.delete(user);
                user.setFavItem(favItemList);
                userFavRepositary.save(user);
                return "Item added successfully";
            }
            else{
                return"Item Already in the list";
            }
        }
    }



    @Override
    public List<FavResto> getFavResto(String emailId) throws UserNotFoundExistException {
        if(userFavRepositary.findById(emailId).isEmpty())
        {
            throw new UserNotFoundExistException();
        }

        else
        {
            User user=userFavRepositary.findById(emailId).get();
            return user.getFavResto();
        }

    }

    @Override
    public List<FavItem> getFavItem(String emailId) throws UserNotFoundExistException {
        if(userFavRepositary.findById(emailId).isEmpty())
        {
            throw new UserNotFoundExistException();
        }

        else
        {
            User user=userFavRepositary.findById(emailId).get();
            if(user.getFavItem()==null)
            {
                return null;
            }
            return user.getFavItem();
        }
    }
}
