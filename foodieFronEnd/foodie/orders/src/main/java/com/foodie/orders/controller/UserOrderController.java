package com.foodie.orders.controller;

import com.foodie.orders.domain.Item;
import com.foodie.orders.domain.Order;
import com.foodie.orders.domain.User;
import com.foodie.orders.exception.*;
import com.foodie.orders.service.UserOrderService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class UserOrderController {
    private ResponseEntity<?> responseEntity;
    private UserOrderService userOrderService;

    @Autowired
    public UserOrderController(UserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }

    public UserOrderController() {
    }


    @PostMapping("/saveUser")
    public ResponseEntity<?> saveUser(@RequestBody User user)  {
        try {


            responseEntity = new ResponseEntity<>(userOrderService.saveUser(user), HttpStatus.CREATED);
        }
        catch (UserAlreadyExistsException e)
        {
            e.getMessage();
        }

        return responseEntity;
    }

    @PostMapping("/user/cart")
    public ResponseEntity<?> saveUserItemToList(@RequestBody Order order, HttpServletRequest request) throws UserNotFoundException {
        try {
            System.out.println("header" +request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("emailId from claims :: " + claims.getSubject());
            String emailId = claims.getSubject();
            System.out.println("emailId :: "+emailId);

            responseEntity = new ResponseEntity<>(userOrderService.saveUserOrderToList(emailId,order), HttpStatus.CREATED);
        }
        catch (UserNotFoundException e)
        {
            throw new UserNotFoundException();
        }

        return responseEntity;
    }
    @GetMapping("/user/Items")
    public ResponseEntity<?> getAllUserItemsFromList(HttpServletRequest request) throws UserNotFoundException {
        try{
            System.out.println("header" +request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("EmailId from claims :: " + claims.getSubject());
            String emailId = claims.getSubject();
            System.out.println("emailid :: "+emailId);
            responseEntity = new ResponseEntity<>(userOrderService.getAllUserOrders(emailId), HttpStatus.OK);
        }catch(UserNotFoundException e)
        {
            throw new UserNotFoundException();
        }
        return responseEntity;
    }
    @DeleteMapping("/user/{orderId}")
    public ResponseEntity<?> deleteUserItemFromList(@PathVariable int orderId,HttpServletRequest request)
            throws UserNotFoundException, ItemNotFoundException {
        System.out.println("Inside delete method");
        Claims claims = (Claims) request.getAttribute("claims");
        System.out.println("emailId from claims :: " + claims.getSubject());
        String emailId = claims.getSubject();

        System.out.println("emailId :: " + emailId);
        try {
            responseEntity = new ResponseEntity<>(userOrderService.deleteUserOrderFromList(emailId, orderId), HttpStatus.OK);
        } catch (UserNotFoundException | OrderNotFoundException m) {
            throw new UserNotFoundException();
        }
        return responseEntity;
    }

    @GetMapping("/user/Items/{ownerId}")
    public ResponseEntity<?> getItemByRestaurantName(@PathVariable String ownerId,HttpServletRequest request) throws RestaurantNotFoundException, ItemNotFoundException {
        Claims claims = (Claims) request.getAttribute("claims");
        System.out.println("emailId from claims :: " + claims.getSubject());
        String emailId = claims.getSubject();
        try {
            responseEntity = new ResponseEntity<>(userOrderService.fetchOrderByOwnerId(emailId,ownerId), HttpStatus.OK);
        }
        catch ( UserNotFoundException e){
            throw new ItemNotFoundException();
        }
        return responseEntity;
    }
}
