package com.foodie.orders.service;
import com.foodie.orders.domain.Order;
import com.foodie.orders.domain.User;
import com.foodie.orders.exception.*;
import com.foodie.orders.repositary.UserOrderRepositary;
import com.foodie.orders.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class UserOrderServiceImpl implements UserOrderService {
    private UserOrderRepositary userOrderRepositary;
    private EmailUtil emailUtil;
    @Autowired
    public UserOrderServiceImpl(UserOrderRepositary userOrderRepositary, EmailUtil emailUtil) {
        this.userOrderRepositary = userOrderRepositary;
        this.emailUtil = emailUtil;
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
        //System.out.println("Order Done");
        return user;
    }

    @Override//Random orderId should generate in frontend
    public User saveUserOrderToList(String emailId, Order order) throws UserNotFoundException, MessagingException {
        if(userOrderRepositary.findById(emailId).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user =  userOrderRepositary.findById(emailId).get();
        System.out.println(user);
        if( user.getOrder()== null || user.getOrder().size()==0)
        {
            order.setOrderId(1);
            userOrderRepositary.delete(user);
            user.setOrder(Arrays.asList(order));
            userOrderRepositary.save(user);
            emailUtil.sendOrderPlacedEmail(order.getOwnerId(),order);
        }
        else {

            List<Order>orders=user.getOrder();
            int lastId=0;
            lastId = (orders.get(orders.size() - 1)).getOrderId();
            order.setOrderId(lastId+1);

            System.out.println("In else part");
            orders.add(order);
            userOrderRepositary.delete(user);
            user.setOrder(orders);
            userOrderRepositary.save(user);
            emailUtil.sendOrderPlacedEmail(order.getOwnerId(),order);
        }
        return userOrderRepositary.save(user);
    }

    @Override
    public boolean deleteUserOrderFromList(String emailId,int orderId) throws UserNotFoundException, OrderNotFoundException, MessagingException {
        if(userOrderRepositary.findById(emailId).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user = userOrderRepositary.findById(emailId).get();
        List<Order>orders=user.getOrder();
        boolean flag=true;
        Order order=new Order();
        for(int i=0;i<orders.size();i++)
        {
            if(orders.get(i).getOrderId()==orderId)
            {
                flag=false;
                order=orders.get(i);
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
        emailUtil.sendOrderCanceledEmailToRestaurant(order.getOwnerId(),order);
        return true;
    }

    @Override
    public List<Order> getAllUserOrders(String emailId) throws UserNotFoundException {
        if(userOrderRepositary.findById(emailId).isEmpty())
        {
            System.out.println("In the if");
            throw new UserNotFoundException();
        }
        System.out.println("out of if");
        System.out.println(userOrderRepositary.findById(emailId).get().getOrder());
        return userOrderRepositary.findById(emailId).get().getOrder();
    }

    @Override
    public List<User> fetchOrderByOwnerId(String ownerId) throws UserNotFoundException {
        if(userOrderRepositary.findAll().isEmpty())
        {
            throw new UserNotFoundException();
        }
        List<User>userOrderList=new ArrayList<>();
        List<Order>orderList=new ArrayList<>();
        List <User>userList=userOrderRepositary.findAll();
        for(User user:userList)
        {
            List<Order> orders = user.getOrder();
            for (Order order : orders)
            {
                if (order.getOwnerId().equals(ownerId)) {
                    orderList.add(order);

                }
            }
            user.setOrder(orderList);
            userOrderList.add(user);
        }
        return userOrderList;
    }

    @Override
    public boolean changeOrderStatus(String emailId, int orderId) throws UserNotFoundException, OrderNotFoundException, MessagingException {
    if(userOrderRepositary.findById(emailId).isEmpty())
    {
       throw new UserNotFoundException();
    }
    User user=userOrderRepositary.findById(emailId).get();
    if(user.getOrder().isEmpty())
    {
        throw new OrderNotFoundException();
    }
    List<Order>orders=user.getOrder();
    boolean flag=true;
    for(int i=0;i<orders.size();i++)
    {
        if (orders.get(i).getOrderId()==orderId )
        {
            flag = false;
            orders.get(i).setStatus(true);
            userOrderRepositary.delete(user);
            user.setOrder(orders);
            userOrderRepositary.save(user);
            emailUtil.sendOrderConfirmationEmail(emailId,orders.get(i));
        }
    }
    if(flag)
    {
        throw new OrderNotFoundException();
    }
        return true;

    }
}
