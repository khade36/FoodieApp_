package com.foodie.cart.controller;

import com.foodie.cart.domain.Cart;
import com.foodie.cart.domain.Item;
import com.foodie.cart.domain.User;
import com.foodie.cart.exception.ItemAlreayExistException;
import com.foodie.cart.exception.ItemNotFoundException;
import com.foodie.cart.exception.UserAlreayExistException;
import com.foodie.cart.exception.UserNotFoundException;
import com.foodie.cart.service.UserCartServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/cart")
public class UserCartController {
    private ResponseEntity<?> responseEntity;
    private UserCartServiceImpl userCartService;
    @Autowired
    public UserCartController(UserCartServiceImpl userCartService) {

        this.userCartService = userCartService;
    }

    public UserCartController() {
    }

    @PostMapping("/saveUser")
    public ResponseEntity<?> saveUser(@RequestBody User user)  {
        try {

            System.out.println("Part1");
            responseEntity = new ResponseEntity<>(userCartService.saveUser(user), HttpStatus.CREATED);
        }
        catch (UserAlreayExistException e)
        {
            e.getMessage();
        }

        return responseEntity;
    }

    @PostMapping("/user/saveItem")
    public ResponseEntity<?> saveItemToCart(@RequestBody Cart cart, HttpServletRequest request)  {
        try {
            System.out.println("header" +request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
//            System.out.println("emailId from claims :: " + claims.getSubject());
            String emailId = claims.getSubject();
            System.out.println("emailId :: "+emailId);

            responseEntity = new ResponseEntity<>(userCartService.saveItemToCart(emailId,cart), HttpStatus.CREATED);
        }
        catch (UserNotFoundException | ItemAlreayExistException e)
        {
            e.getMessage();
        }

        return responseEntity;
    }
    @GetMapping("/user/cart")
    public ResponseEntity<?> getCart(HttpServletRequest request) throws UserNotFoundException {
        try{
            System.out.println("header" +request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("EmailId from claims :: " + claims.getSubject());
            String emailId = claims.getSubject();
            System.out.println("emailid :: "+emailId);
            responseEntity = new ResponseEntity<>(userCartService.getCart(emailId), HttpStatus.OK);
        }catch(UserNotFoundException e)
        {
            throw new UserNotFoundException();
        }
        return responseEntity;
    }
    @DeleteMapping("/user/{itemName}")
    public ResponseEntity<?> deleteUserItemFromList(@PathVariable String itemName,HttpServletRequest request)
    {
        System.out.println("Inside delete method");
        Claims claims = (Claims) request.getAttribute("claims");
        System.out.println("emailId from claims :: " + claims.getSubject());
        String emailId = claims.getSubject();

        System.out.println("emailId :: " + emailId);
        try {
            responseEntity = new ResponseEntity<>(userCartService.deleteItemFromCart(emailId, itemName), HttpStatus.OK);
        } catch (UserNotFoundException | ItemNotFoundException m) {
            m.getMessage();
        }
        return responseEntity;
    }

    @DeleteMapping("/user/emptyCart")
    public ResponseEntity<?> deleteCart(HttpServletRequest request)
    {
        System.out.println("Inside delete method");
        Claims claims = (Claims) request.getAttribute("claims");
        System.out.println("emailId from claims :: " + claims.getSubject());
        String emailId = claims.getSubject();

        System.out.println("emailId :: " + emailId);
        try {
            responseEntity = new ResponseEntity<>(userCartService.deleteCart(emailId), HttpStatus.OK);
        } catch (UserNotFoundException  m) {
            m.getMessage();
        }
        return responseEntity;
    }

    @PutMapping("/Items/{itemName}/{itemQty}/{emailId}")
    public ResponseEntity<?> getItemByRestaurantName(@PathVariable String itemName,@PathVariable int itemQty,@PathVariable String emailId)  {

        try {
            responseEntity = new ResponseEntity<>(userCartService.updateQty(emailId,itemName,itemQty), HttpStatus.OK);
        }
        catch (ItemNotFoundException | UserNotFoundException e){
            e.getMessage();
        }
        return responseEntity;
    }


}
