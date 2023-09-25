package com.foodie.orders.service;



import com.foodie.orders.domain.Order;
import com.foodie.orders.domain.User;
import com.foodie.orders.exception.*;
import com.foodie.orders.repositary.UserOrderRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class UserOrderServiceImpl implements UserOrderService {
    private UserOrderRepositary userOrderRepositary;
@Autowired
    public UserOrderServiceImpl(UserOrderRepositary userOrderRepositary) {
        this.userOrderRepositary = userOrderRepositary;
    }

    public UserOrderServiceImpl() {
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
        if(userOrderRepositary.findById(user.getEmailId()).isPresent())
        {
            throw new UserAlreadyExistsException();
        }
        userOrderRepositary.save(user);
        return user;
    }

    @Override//Random orderId should generate in frontend
    public User saveUserOrderToList(String emailId, Order order) throws UserNotFoundException {
        if(userOrderRepositary.findById(emailId).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user =  userOrderRepositary.findById(emailId).get();
        System.out.println(user);
        if( user.getOrder()== null)
        {
            userOrderRepositary.delete(user);
            user.setOrder(Arrays.asList(order));
            userOrderRepositary.save(user);
        }
        else {
            List<Order>orders=user.getOrder();
            System.out.println("In else part");
            orders.add(order);
            userOrderRepositary.delete(user);
            user.setOrder(orders);
            userOrderRepositary.save(user);
        }
        return userOrderRepositary.save(user);
    }

    @Override
    public boolean deleteUserOrderFromList(String emailId,int orderId) throws UserNotFoundException, OrderNotFoundException {
        if(userOrderRepositary.findById(emailId).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user = userOrderRepositary.findById(emailId).get();
        List<Order>orders=user.getOrder();
        boolean flag=true;
        for(int i=0;i<orders.size();i++)
        {
            if(orders.get(i).getOrderId()==orderId)
            {
                flag=false;
                orders.remove(i);
            }
        }
        if(flag)
        {
            throw new OrderNotFoundException();
        }
        userOrderRepositary.delete(user);
        user.setOrder(orders);
        userOrderRepositary.save(user);
        return true;
    }

    @Override
    public List<Order> getAllUserOrders(String emailId) throws UserNotFoundException {
        if(userOrderRepositary.findById(emailId).isEmpty())
        {
            throw new UserNotFoundException();
        }
        return userOrderRepositary.findById(emailId).get().getOrder();
    }

    @Override
    public List<Order> fetchOrderByOwnerId(String emailId,String ownerId) throws UserNotFoundException {
        if(userOrderRepositary.findById(emailId).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user=userOrderRepositary.findById(emailId).get();
        List<Order>orders=user.getOrder();
        List<Order>orderList=new ArrayList<>();
        for(Order order:orders)
        {
            if(order.getOwnerId().equals(ownerId))
            {
                orderList.add(order);
            }
        }

        return orderList;
    }
}
