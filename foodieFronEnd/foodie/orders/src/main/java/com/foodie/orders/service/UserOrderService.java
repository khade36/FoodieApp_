package com.foodie.orders.service;

import com.foodie.orders.domain.Address;
import com.foodie.orders.domain.Item;
import com.foodie.orders.domain.Order;
import com.foodie.orders.domain.User;
import com.foodie.orders.exception.*;

import java.util.List;

public interface UserOrderService {
    User saveUser(User user) throws UserAlreadyExistsException;
    User saveUserOrderToList(String emailId, Order order) throws UserNotFoundException;
    boolean deleteUserOrderFromList(String emailId,int orderId) throws UserNotFoundException, OrderNotFoundException;
    List<Order> getAllUserOrders(String emailId) throws UserNotFoundException;
    List<Order> fetchOrderByOwnerId(String emailId,String ownerId) throws  UserNotFoundException;
}
