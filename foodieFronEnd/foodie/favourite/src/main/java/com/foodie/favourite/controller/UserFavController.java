package com.foodie.favourite.controller;

import com.foodie.favourite.domain.FavItem;
import com.foodie.favourite.domain.FavResto;
import com.foodie.favourite.exception.RestaurantAlreadyExistException;
import com.foodie.favourite.exception.UserNotFoundExistException;
import com.foodie.favourite.service.UserFavServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class UserFavController {
    private ResponseEntity<?> responseEntity;
    private UserFavServiceImpl userFavService;
@Autowired
    public UserFavController(UserFavServiceImpl userFavService) {
        this.userFavService = userFavService;
    }

    public UserFavController() {
    }

    @PostMapping("/favResto")
    public ResponseEntity<?> saveRestToFavList(@RequestBody FavResto favResto, HttpServletRequest request)
    {
        try {
            System.out.println("header" + request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("emailId from claims :: " + claims.getSubject());
            String emailId = claims.getSubject();
            System.out.println("OwnerId :: " + emailId);
            responseEntity=new ResponseEntity<>(userFavService.saveFavResto(emailId,favResto), HttpStatus.OK);
        }
        catch (RuntimeException | UserNotFoundExistException | RestaurantAlreadyExistException e)
        {
            e.printStackTrace();
        }
        return responseEntity;
    }


    @PostMapping("/favItem")
    public ResponseEntity<?> saveItemToFavList(@RequestBody FavItem favItem, HttpServletRequest request)
    {
        try {
            System.out.println("header" + request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("emailId from claims :: " + claims.getSubject());
            String emailId = claims.getSubject();
            System.out.println("OwnerId :: " + emailId);
            responseEntity=new ResponseEntity<>(userFavService.saveFavItem(emailId,favItem), HttpStatus.OK);
        }
        catch (RuntimeException | UserNotFoundExistException e)
        {
            e.printStackTrace();
        }
        return responseEntity;
    }


    @GetMapping("/getFavRestList")
    public ResponseEntity<?> getAllFavResto(HttpServletRequest request)
    {

        try {
            System.out.println("header" + request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("emailId from claims :: " + claims.getSubject());
            String emailId = claims.getSubject();
            System.out.println("OwnerId :: " + emailId);
            responseEntity=new ResponseEntity<>(userFavService.getFavResto(emailId),HttpStatus.OK);
        }
        catch ( UserNotFoundExistException e)
        {
            e.printStackTrace();
        }
        return responseEntity;
    }


    @GetMapping("/getFavItemList")
    public ResponseEntity<?> getAllFavItem(HttpServletRequest request)
    {

        try {
            System.out.println("header" + request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("emailId from claims :: " + claims.getSubject());
            String emailId = claims.getSubject();
            System.out.println("OwnerId :: " + emailId);
            responseEntity=new ResponseEntity<>(userFavService.getFavItem(emailId),HttpStatus.OK);
        }
        catch ( UserNotFoundExistException e)
        {
            e.printStackTrace();
        }
        return responseEntity;
    }




}
