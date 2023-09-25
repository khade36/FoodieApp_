package com.foodie.restaurant.controller;


import com.foodie.restaurant.domain.Item;
import com.foodie.restaurant.domain.Restaurant;
import com.foodie.restaurant.domain.RestoOwner;
import com.foodie.restaurant.domain.User;
import com.foodie.restaurant.exception.*;
import com.foodie.restaurant.service.RestaurantServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestaurantController {
    private ResponseEntity<?> responseEntity;
    private RestaurantServiceImpl restaurantService;


    @Autowired
    public RestaurantController(RestaurantServiceImpl restaurantService) {
        this.restaurantService = restaurantService;
    }

    public RestaurantController() {
    }

    @PostMapping("/saveRestoOwner")
    public ResponseEntity<?> saveRestoOwner(@RequestBody User restoOwner) throws RestaurantOwnerAlreadyExistException {

        System.out.println("part1");
            responseEntity = new ResponseEntity<>(restaurantService.saveRestoOwnerToList(restoOwner), HttpStatus.CREATED);

        return responseEntity;
    }




    @PostMapping(value={"restaurant/saveRestaurant"},consumes={MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> saveRestaurant(@RequestPart("Restaurant") Restaurant restaurant, @RequestPart ("imageFile") MultipartFile file, HttpServletRequest request) throws RestaurantAlreadyExistException, RestoOwnerNotFoundException {
        try {
            System.out.println("header" + request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims"); //khade36@gmail.com
            System.out.println("emailId from claims :: " + claims.getSubject());
            String OwnerId = claims.getSubject();
            System.out.println("OwnerId :: " + OwnerId);
            String images=restaurantService.uploadImage(file);
            restaurant.setRestoImage(images);
            responseEntity = new ResponseEntity<>(restaurantService.saveRestoToList(OwnerId, restaurant), HttpStatus.CREATED);
        } catch (RestaurantAlreadyExistException | RestoOwnerNotFoundException | IOException e) {
            e.printStackTrace();//check how to throw multiple exceptions
        }
        return responseEntity;
    }


    @PostMapping(value={"restaurant/saveItem/{restaurantName}"},consumes={MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> saveItemInResto(@PathVariable String restaurantName, @RequestPart("Item") Item item, @RequestPart ("imageFile") MultipartFile file, HttpServletRequest request)  throws ItemAlreayExistException, RestoOwnerNotFoundException,RestaurantNotFoundException
    {
        try {
            System.out.println("header" + request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("emailId from claims :: " + claims.getSubject());
            String OwnerId = claims.getSubject();
            System.out.println("OwnerId :: " + OwnerId);
            String images=restaurantService.uploadImage(file);
            item.setItemPic(images);
            responseEntity = new ResponseEntity<>(restaurantService.saveItemToList(OwnerId,restaurantName, item), HttpStatus.CREATED);
        } catch (ItemAlreayExistException | RestoOwnerNotFoundException | RestaurantNotFoundException | IOException e) {
            e.printStackTrace();//check how to throw multiple exceptions
        }
        return responseEntity;
    }


    @DeleteMapping("restaurant/deleteRestOwner")
    public ResponseEntity<?> deleteRestoOwner( HttpServletRequest request)  throws  RestoOwnerNotFoundException
    {
        try {
            System.out.println("header" + request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("emailId from claims :: " + claims.getSubject());
            String ownerId = claims.getSubject();
            System.out.println("OwnerId :: " + ownerId);

            responseEntity = new ResponseEntity<>(restaurantService.deleteRestoOwnerFromList(ownerId), HttpStatus.CREATED);
        } catch ( RestoOwnerNotFoundException e) {
            throw new RestoOwnerNotFoundException();
        }
        return responseEntity;
    }


    @DeleteMapping("restaurant/deleteRestaurant/{restaurantName}")
    public ResponseEntity<?> deleteRestaurant( @PathVariable String restaurantName, HttpServletRequest request)  throws  RestoOwnerNotFoundException,RestaurantNotFoundException
    {
        try {
            System.out.println("header" + request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("emailId from claims :: " + claims.getSubject());
            String OwnerId = claims.getSubject();
            System.out.println("OwnerId :: " + OwnerId);

            responseEntity = new ResponseEntity<>(restaurantService.deleteRestoFromList(OwnerId,restaurantName), HttpStatus.CREATED);
        } catch ( RestoOwnerNotFoundException |RestaurantNotFoundException e) {
            throw new RestoOwnerNotFoundException();//check how to throw multiple exceptions
        }
        return responseEntity;
    }


    @DeleteMapping("restaurant/deleteRestaurantItem/{restaurantName}/{itemName}")
    public ResponseEntity<?> deleteItem( @PathVariable String restaurantName,@PathVariable  String itemName, HttpServletRequest request)  throws  RestoOwnerNotFoundException,RestaurantNotFoundException, ItemNotFoundException
    {
        try {
            System.out.println("header" + request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("emailId from claims :: " + claims.getSubject());
            String ownerId = claims.getSubject();
            System.out.println("OwnerId :: " + ownerId);

            responseEntity = new ResponseEntity<>(restaurantService.deleteItemFromList(ownerId,restaurantName,itemName), HttpStatus.CREATED);
        } catch ( RestoOwnerNotFoundException |RestaurantNotFoundException | ItemNotFoundException e) {
            throw new RestoOwnerNotFoundException();//check how to throw multiple exceptions
        }
        return responseEntity;
    }

    @GetMapping("resto/getAllRestOwners")//Use this for non-token display without login
    public ResponseEntity<?> getAllRestoOwners()
    {
        responseEntity = new ResponseEntity<>(restaurantService.getAllRestoOwner(), HttpStatus.CREATED);

        return responseEntity;
    }

    @GetMapping("restaurant/getRestaurantList")
    public ResponseEntity<?> getRestaurantsForOwnerIdProvided( HttpServletRequest request)  throws  RestoOwnerNotFoundException
    {
        try {
            System.out.println("header" + request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("emailId from claims :: " + claims.getSubject());
            String OwnerId = claims.getSubject();
            System.out.println("OwnerId :: " + OwnerId);

            responseEntity = new ResponseEntity<>(restaurantService.getAllResto(OwnerId), HttpStatus.CREATED);
        } catch ( RestoOwnerNotFoundException  e) {
            throw new RestoOwnerNotFoundException();
        }
        return responseEntity;
    }

    @GetMapping("restaurant/getItemList/{restaurantName}")
    public ResponseEntity<?> getItemForRestaurantProvided(@PathVariable String restaurantName, HttpServletRequest request)  throws  RestoOwnerNotFoundException,RestaurantNotFoundException
    {
        try {
            System.out.println("header" + request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("emailId from claims :: " + claims.getSubject());
            String OwnerId = claims.getSubject();
            System.out.println("OwnerId :: " + OwnerId);

            responseEntity = new ResponseEntity<>(restaurantService.getAllRestoItems(OwnerId,restaurantName), HttpStatus.CREATED);
        } catch ( RestoOwnerNotFoundException | RestaurantNotFoundException e)//check how to throw multiple exceptions
        {
            throw new RestoOwnerNotFoundException();
        }
        return responseEntity;
    }

    @GetMapping("restaurant/ItemList/{restaurantName}/{ownerId}")
    public ResponseEntity<?> getItemFormRestaurantProvided( @PathVariable String restaurantName,@PathVariable String ownerId, HttpServletRequest request)  throws  RestoOwnerNotFoundException,RestaurantNotFoundException
    {
        try {
            System.out.println("header" + request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("emailId from claims :: " + claims.getSubject());


            responseEntity = new ResponseEntity<>(restaurantService.getAllRestoItems(ownerId,restaurantName), HttpStatus.CREATED);
        } catch ( RestoOwnerNotFoundException | RestaurantNotFoundException e)//check how to throw multiple exceptions
        {
            throw new RestoOwnerNotFoundException();
        }
        return responseEntity;
    }

    @PutMapping("restaurant/updatePrice/{restaurantName}/{itemName}")
    public ResponseEntity<?> updatePrice(@PathVariable String restaurantName,@PathVariable String itemName,@RequestParam int newPrice, HttpServletRequest request)  throws  RestoOwnerNotFoundException,RestaurantNotFoundException,ItemNotFoundException
    {
        try {
            System.out.println("header" + request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("emailId from claims :: " + claims.getSubject());
            String ownerId = claims.getSubject();
            System.out.println("OwnerId :: " + ownerId);

            responseEntity = new ResponseEntity<>(restaurantService.updateItem(ownerId,restaurantName,itemName,newPrice), HttpStatus.CREATED);
        } catch (RestoOwnerNotFoundException | RestaurantNotFoundException | ItemNotFoundException e) //check how to throw multiple exceptions
        {
            throw new RestoOwnerNotFoundException();
        }
        return responseEntity;
    }

    @PutMapping("/changeRestoStatus/{ownerId}/{restaurantName}")
    public ResponseEntity<?>changeRestoStatus(@PathVariable String ownerId,@PathVariable String restaurantName)
    {
//        System.out.println("header" + request.getHeader("Authorization"));
//        Claims claims = (Claims) request.getAttribute("claims");
//        System.out.println("emailId from claims :: " + claims.getSubject());
//        System.out.println("got here");
        try {
            responseEntity = new ResponseEntity<>(restaurantService.changeRestoStatus(ownerId, restaurantName), HttpStatus.OK);
        }catch ( RestoOwnerNotFoundException | RestaurantNotFoundException e)
        {
            e.printStackTrace();
        }
        return responseEntity;
    }



}
