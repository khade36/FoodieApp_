package com.foodie.cart.service;

import com.foodie.cart.domain.Cart;
import com.foodie.cart.domain.Item;
import com.foodie.cart.domain.User;
import com.foodie.cart.exception.ItemAlreayExistException;
import com.foodie.cart.exception.ItemNotFoundException;
import com.foodie.cart.exception.UserAlreayExistException;
import com.foodie.cart.exception.UserNotFoundException;
import com.foodie.cart.repositary.UserCartRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserCartServiceImpl implements UserCartService{
    private UserCartRepositary userCartRepositary;
     @Autowired
    public UserCartServiceImpl(UserCartRepositary userCartRepositary) {
        this.userCartRepositary = userCartRepositary;
    }

    public UserCartServiceImpl() {
    }

    @Override
    public User saveUser(User user) throws UserAlreayExistException {
        System.out.println("Part3");
         if(userCartRepositary.findById(user.getEmailId()).isPresent())
         {
             throw new UserAlreayExistException();
         }
         userCartRepositary.save(user);
        return user;
    }

    @Override
    public Cart saveItemToCart( String emailId, String ownerId,String restaurantName, Item item) throws UserNotFoundException, ItemAlreayExistException {

          if(userCartRepositary.findById(emailId).isEmpty())
          {

              throw new UserNotFoundException();
          }
          else
          {
              User user=userCartRepositary.findById(emailId).get();
              Cart cart = new Cart();
              if(user.getCart()!=null)
              {
                   cart = user.getCart();
                  if (cart.getItem() == null) {
                      cart.setItem(Arrays.asList(item));
                      userCartRepositary.delete(user);
                      user.setCart(cart);
                      userCartRepositary.save(user);
                  } else
                  {
                      List<Item> items = cart.getItem();
                      for(Item item1:items)
                      {
                          if(item1.getItemName().equals(item.getItemName()))
                          {
                              throw new ItemAlreayExistException();
                          }
                      }
                      items.add(item);
                      cart.setItem(items);
                      userCartRepositary.delete(user);
                      user.setCart(cart);
                      userCartRepositary.save(user);
                  }

              }
              else
              {
                  System.out.println(restaurantName);
                  cart.setRestaurantName(restaurantName);
                  cart.setOwnerId(ownerId);
                  cart.setItem(Arrays.asList(item));
                  userCartRepositary.delete(user);
                  user.setCart(cart);
                  userCartRepositary.save(user);

              }
              return  cart;
          }

    }

    @Override
    public boolean deleteItemFromCart(String emailId, String itemName) throws UserNotFoundException, ItemNotFoundException {
        Cart cart;
        if(userCartRepositary.findById(emailId).isEmpty())
        {
            throw new UserNotFoundException();
        }
        else
        {
          User user=userCartRepositary.findById(emailId).get();
          cart=user.getCart();
          List<Item>items=cart.getItem();
          boolean flag=true;
          for(int i=0;i<items.size();i++)
            {
               if(items.get(i).getItemName().equals(itemName))
               {
                   flag=false;
                   items.remove(i);
                   cart.setItem(items);
                   userCartRepositary.delete(user);
                   user.setCart(cart);
                   userCartRepositary.save(user);
               }
            }
          if(flag)
          {
              throw new ItemNotFoundException();
          }
        }
        return true;
    }

    @Override
    public Cart getCart(String emailId) throws UserNotFoundException {
        Cart cart;
        if(userCartRepositary.findById(emailId).isEmpty())
        {
            throw new UserNotFoundException();
        }
        else
        {
            User user=userCartRepositary.findById(emailId).get();
           cart= user.getCart();
        }
        return cart;
    }

    @Override
    public Cart updateQty(String emailId, String itemName,int itemQty) throws UserNotFoundException, ItemNotFoundException {
        Cart cart;
        if(userCartRepositary.findById(emailId).isEmpty())
        {
            throw new UserNotFoundException();
        }
        else
        {
            User user=userCartRepositary.findById(emailId).get();
            cart=user.getCart();
            List<Item>items=cart.getItem();
            boolean flag=true;
            for(int i=0;i<items.size();i++)
            {
                if(items.get(i).getItemName().equals(itemName))
                {
                    flag=false;
                    Item item=items.get(i);
                    items.remove(i);
                    item.setItemQty(itemQty);
                    items.add(item);
                    cart.setItem(items);
                    userCartRepositary.delete(user);
                    user.setCart(cart);
                    userCartRepositary.save(user);
                }
            }
            if(flag)
            {
                throw new ItemNotFoundException();
            }

       }
        return cart;
   }
}
