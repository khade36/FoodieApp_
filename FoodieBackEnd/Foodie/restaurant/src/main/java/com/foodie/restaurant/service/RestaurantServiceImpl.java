package com.foodie.restaurant.service;


import com.foodie.restaurant.domain.Item;
import com.foodie.restaurant.domain.Restaurant;
import com.foodie.restaurant.domain.RestoOwner;
import com.foodie.restaurant.domain.User;
import com.foodie.restaurant.exception.*;
import com.foodie.restaurant.repositary.RestaurantRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private RestaurantRepositary restaurantRepositary;

     @Autowired
    public RestaurantServiceImpl(RestaurantRepositary restaurantRepositary) {
        this.restaurantRepositary = restaurantRepositary;

    }

    public RestaurantServiceImpl() {
    }

    @Override
    public RestoOwner saveRestoOwnerToList(User restoOwner) throws RestaurantOwnerAlreadyExistException
    {
        //we want this method because we want to create database space for Resto owner as soon as we check whether user is resto-owner or not in signUp
         RestoOwner restoOwner1 = new RestoOwner();
       // System.out.println(restoOwner.getEmailId());
         restoOwner1.setOwnerId(restoOwner.getEmailId());
         restoOwner1.setOwnerName(restoOwner.getFirstName());
        if(restaurantRepositary.findById(restoOwner1.getOwnerId()).isPresent())
        {
            throw new RestaurantOwnerAlreadyExistException();
        }

        else
        {
            //System.out.println("part2");
            restaurantRepositary.save(restoOwner1);
        }
        return restoOwner1;
    }

    @Override
    public Restaurant saveRestoToList(String ownerId, Restaurant restaurant) throws RestoOwnerNotFoundException, RestaurantAlreadyExistException {
        if(restaurantRepositary.findById(ownerId)==null)//checking whether owner is ther or not
        {
            throw new RestoOwnerNotFoundException();
        }
        else{
            //System.out.println("++");
            //System.out.println(restaurantRepositary.findById(ownerId).isEmpty());
           RestoOwner restoOwner=restaurantRepositary.findById(ownerId).get();
           if(restoOwner.getRestaurant()==null)//it will check list is already there or not if not will add restaurant as list
           {
               //System.out.println("++1");
               RestoOwner owner=restaurantRepositary.findById(ownerId).get();
               //Approach is : remove all old entities(restaurant,owner) and re-add all that after doing required update.
               restaurantRepositary.delete(owner);//removing old owner
               restoOwner.setRestaurant(Arrays.asList(restaurant));//converting into list because list is not created it was empty
               restaurantRepositary.save(restoOwner);//saving updated owner into the repo

           }

           else //if some restaurants already there then it will add in that existing list
           {
               List<Restaurant> restoList=restoOwner.getRestaurant();
               for(Restaurant restaurant1:restoList)//here checking restaurant is already there or not if not, then only add that restaurant into list
               {
                   if (restaurant1.getRestaurantName().equals( restaurant.getRestaurantName())){
                       throw new RestaurantAlreadyExistException();
                   }
               }

                       List <Restaurant> restoList1= restoOwner.getRestaurant();
                       restoList1.add(restaurant);
                       //Heading with same approach as above removing old and re-adding with updated one
                       restaurantRepositary.delete(restoOwner);
                       restoOwner.setRestaurant(restoList1);
                       restaurantRepositary.save(restoOwner);
           }
        }

        return restaurant;
    }

    @Override
    public RestoOwner saveItemToList(String ownerId, String restaurantName, Item item) throws RestoOwnerNotFoundException, RestaurantNotFoundException, ItemAlreayExistException {
        if(restaurantRepositary.findById(ownerId).isEmpty())//here checking that owner is present or not
        {
            throw new RestoOwnerNotFoundException();
        }
        else{
            RestoOwner restoOwner=restaurantRepositary.findById(ownerId).get();
            if(restoOwner.getRestaurant().isEmpty())//here checking restaurants are there for that owner or not
            {
                throw new RestaurantNotFoundException();
            }
            else
            {
                boolean flag=true;
                for(Restaurant restaurant:restoOwner.getRestaurant())//Here Iterating through restaurant list of provided owner to match further restaurant by Name one by one
                {
                    if(restaurant.getRestaurantName().equals(restaurantName)) //here matching that restaurantName with restaurant list to get required one
                    {
                        flag=false;
                        if (restaurant.getItem() == null)//checking whether Items are there or not (meaning understanding whether it is first time to add or not).
                        {
                            RestoOwner owner=restaurantRepositary.findById(ownerId).get();
                            List<Restaurant>restaurants=owner.getRestaurant();
                     //Approach is : remove all old entities(restaurant,owner) and re-add all that after doing required update.
                            restaurantRepositary.delete(owner);//removing old owner.
                            for(int i=0;i<restaurants.size();i++)//this is to remove old restaurant.
                            {
                                if(restaurant.getRestaurantName().equals(restaurants.get(i).getRestaurantName()))
                                {
                                    restaurants.remove(i);
                                }
                            }
//                            restoOwner.setRestaurant(restaurants);
                            restaurant.setItem(Arrays.asList(item));//setting Item to restaurant since it is first time added as a array
                            restaurants.add(restaurant);//adding updated restaurant to restaurant list.
                            restoOwner.setRestaurant(restaurants);//setting updated restaurant list to owner.
                            restaurantRepositary.save(restoOwner);//saving updated owner into repository.
                        }
                        else
                        {
                            List<Item>items=restaurant.getItem();
                            for (Item item1 : items)//iterating through item list of provided restaurant
                            {
                                if (item1.getItemName().equals(item.getItemName())) //here checking if that item is already or not
                                {
                                    throw new ItemAlreayExistException();
                                }
                            }

                                    RestoOwner owner=restaurantRepositary.findById(ownerId).get();
                                    List<Restaurant>restaurants=owner.getRestaurant();
                                    restaurantRepositary.delete(owner);
                                    List<Item> item2 = restaurant.getItem();
                                    item2.add(item);

                                    for(int i=0;i<restaurants.size();i++)
                                    {
                                        if(restaurant.getRestaurantName().equals(restaurants.get(i).getRestaurantName()))
                                        {
                                          restaurants.remove(i);
                                        }
                                    }


                                    restaurant.setItem(item2);
                                    restaurants.add(restaurant);
                                    owner.setRestaurant(restaurants);

                                    restaurantRepositary.save(owner);
                        }
                    }
                }
                if(flag)
                {
                    throw new RestaurantNotFoundException();
                }
            }
        }
        return restaurantRepositary.findById(ownerId).get();
    }



    @Override//working
    public boolean deleteRestoOwnerFromList(String ownerId) throws RestoOwnerNotFoundException {

        RestoOwner restoOwner=restaurantRepositary.findById(ownerId).get();
        if(restoOwner==null)
        {
            throw new RestoOwnerNotFoundException();
        }
        else
        {
            restaurantRepositary.delete(restoOwner);
        }
        return true;
    }

    @Override//working with exception
    public RestoOwner deleteRestoFromList(String ownerId, String restaurantName) throws RestoOwnerNotFoundException, RestaurantNotFoundException {
        RestoOwner restoOwner=restaurantRepositary.findById(ownerId).get();
       if(restaurantRepositary.findById(ownerId).isEmpty())//checking provided restoOwner is available or not
       {
          // System.out.println("comes here in delete**");
           throw new RestoOwnerNotFoundException();
       }
       else
       {
          // System.out.println("comes here in delete1");
           if(restoOwner.getRestaurant().isEmpty())//checking restaurant list is empty or not
           {
               throw new RestaurantNotFoundException();
           }
           else
           {
               List<Restaurant>restaurants=restoOwner.getRestaurant();//this is taken repeatedly to intention of not using and change same parameter that is used to run the loop (below for loop is there)
               List<Restaurant>restaurants1=restoOwner.getRestaurant();
               boolean flag=true;
               for(Restaurant restaurant:restaurants)//iterating through restaurant list of provided owner to match required restaurant one by one
               {
                   //System.out.println("comes here in delete1.1");

                   if(restaurant.getRestaurantName().equals(restaurantName))//checking one by one provided restaurant by comparing name
                   {
                      // System.out.println("comes here in delete2");
                       flag=false;

                       //Approach is : remove all old entities(restaurant,owner) and re-add all that after doing required update
                       for(int i=0;i<restaurants1.size();i++)
                       {
                           if(restaurants1.get(i).getRestaurantName().equals(restaurantName))//removing provided restaurant from the list of particular owner
                           {
                               restaurants1.remove(i);
                           }
                       }

                       restaurantRepositary.delete(restoOwner);//removing old owner
                       restoOwner.setRestaurant(restaurants1);//setting updated list to respective owner
                       restaurantRepositary.save(restoOwner);//saving updated owner
                   }
               }
                if(flag)
               {
                  // System.out.println("comes here in delete3");
                   throw new RestaurantNotFoundException();
               }
           }
       }
        return restoOwner;
    }

    @Override//working with exception
    public boolean deleteItemFromList(String ownerId, String restaurantName, String itemName) throws RestoOwnerNotFoundException, RestaurantNotFoundException, ItemNotFoundException {
        RestoOwner restoOwner=restaurantRepositary.findById(ownerId).get();
        if(restoOwner==null)//checking provided restoOwner is available or not
        {

            throw new RestoOwnerNotFoundException();
        }
        else
        {

            if(restoOwner.getRestaurant().isEmpty())//checking restaurant list is empty or not
            {
                throw new RestaurantNotFoundException();
            }
            else
            {

                List<Restaurant>restaurants=restoOwner.getRestaurant();
                List<Restaurant>restaurants1=restoOwner.getRestaurant();//this is taken repeatedly to intention of not using and change same parameter that is used to run the loop (below for loop is there)
                boolean flag=true;
                for(Restaurant restaurant:restaurants)//iterating through restaurant list of provided owner
                {

                    if(restaurant.getRestaurantName().equals(restaurantName))//checking provided restaurant is present or not
                    {

                        flag=false;
                        if (restaurant.getItem().isEmpty())//if Items are not there then nothing to delete
                        {
                            throw new ItemNotFoundException();
                        }
                        else
                        {

                            List<Item> itemList = restaurant.getItem();
                            List<Item> itemList1 = restaurant.getItem();//this is taken repeatedly to intention of not using and change same parameter that is used to run the loop (below for loop is there)
                            boolean flag1=true;
                            for (Item item : itemList)
                            {
                                //Approach is : remove all old entities(restaurant,owner) and re-add all that after doing required update
                                if (item.getItemName().equals(itemName) )//Iterating through itemList to get required item by comparing itemName one by one
                               {
                                   flag1=false;

                                   for(int i=0;i<restaurants.size();i++)//removing corresponding old restaurant of item that should delete
                                   {
                                       if(restaurants1.get(i).getRestaurantName().equals(restaurantName))
                                       {
                                           restaurants1.remove(i);
                                       }
                                   }




                                   for(int i=0;i<itemList.size();i++)//removing provided item from the list of particular restaurant of particular owner
                                   {
                                       if(itemList1.get(i).getItemName().equals(itemName))
                                       {
                                           itemList1.remove(i);
                                       }
                                   }

                               Restaurant restaurant1=restaurant;
                                restaurant1.setItem(itemList1);//setting updated list to respective restaurant of provided owner
                                restaurants1.add(restaurant1);//adding updated restaurant into restaurant list
                                restaurantRepositary.delete(restoOwner);//removing old Owner
                                restoOwner.setRestaurant(restaurants1);//setting updated restaurant list to restaurant owner
                                restaurantRepositary.save(restoOwner);//saving updated restaurant owner into the repository
                               }


                            }
                             if(flag1)
                            {
                                throw new ItemNotFoundException();
                            }
                        }
                    }

                }
                if(flag)
                {
                    throw new RestaurantNotFoundException();
                }
            }
        }
        return true;
    }


    @Override//working
    public List<RestoOwner> getAllRestoOwner()
    {
        return restaurantRepositary.findAll();
    }

    @Override//working
    public List<Restaurant> getAllResto(String ownerId) throws RestoOwnerNotFoundException
    {

        List<Restaurant> restaurants=new ArrayList<>();
        if(restaurantRepositary.findById(ownerId).get()==null)//checking provided restoOwner is available or not
        {
            throw new RestoOwnerNotFoundException();
        }
        else//Here be careful if restaurant is empty then it may happen that it is not in form of list may give error
        {
            RestoOwner restoOwner=restaurantRepositary.findById(ownerId).get();
            restaurants=restoOwner.getRestaurant();
        }

        return restaurants;
    }


    @Override
    public List<Item> getAllRestoItems(String ownerId, String restaurantName) throws RestoOwnerNotFoundException, RestaurantNotFoundException {
        RestoOwner restoOwner=restaurantRepositary.findById(ownerId).get();
        List<Item> itemList=new ArrayList<>();
        if(restaurantRepositary.findById(ownerId).isEmpty())//checking provided restoOwner is available or not
        {
            throw new RestoOwnerNotFoundException();
        }
        else
        {

            if(restoOwner.getRestaurant().isEmpty())//checking restaurant list is empty or not
            {
                throw new RestaurantNotFoundException();
            }
            else
            {

                List<Restaurant>restaurants=restoOwner.getRestaurant();
                boolean flag=true;
                for(Restaurant restaurant:restaurants)//iterating through restaurant list of provided owner
                {

                    if(restaurant.getRestaurantName().equals(restaurantName))//checking provided restaurant is present or not
                    {
                        flag=false;
                         itemList=restaurant.getItem();//here also  br careful if Item list is empty then it will be not converted into list
                    }
                }
                if(flag)
                {
                    System.out.println("**5");
                    throw new RestaurantNotFoundException();
                }
            }
        }
        return itemList;
    }

    @Override//this is to get all the registered restaurant from whole database
    public List<Restaurant> getAllRestoForAll()  {
         List<RestoOwner>restOwners=restaurantRepositary.findAll();
        List<Restaurant> restaurants = null;
         for(RestoOwner restoOwner:restOwners)
         {
             restaurants.add((Restaurant) restoOwner.getRestaurant());

         }
        return restaurants;
    }

    @Override//this is to search particular restaurant by name from whole database
    public List<Restaurant> getRestoFormAll(String restaurantName)
    {
        List<RestoOwner>restOwners=restaurantRepositary.findAll();
        List<Restaurant> restaurants = null;
        for(RestoOwner restoOwner:restOwners)
        {
            restaurants.add((Restaurant) restoOwner.getRestaurant());

        }
        List<Restaurant>restaurantsReq=null;
        for(Restaurant restaurant:restaurantsReq)
        {
            if(restaurant.getRestaurantName().equals(restaurantName))
            {
                restaurantsReq.add(restaurant);
            }
        }
        return restaurantsReq;
    }

    @Override//this is to search particular item by name from whole database
    public List<Restaurant> getItemFormAll(String itemName)
    {
        List<RestoOwner>restOwners=restaurantRepositary.findAll();
        List<Restaurant> restaurants = null;
        for(RestoOwner restoOwner:restOwners)
        {
            restaurants.add((Restaurant) restoOwner.getRestaurant());

        }
        List<Item>items=null;
        List<Restaurant>restoList=null;
        for(Restaurant restaurant:restaurants)
        {
           items.add((Item)restaurant.getItem());

            for(Item item:items)
            {
                if(item.getItemName().equals(itemName))
                {
                    restoList.add(restaurant);
                }
            }
            items.clear();
        }

        return restoList;
    }


    @Override//working
    public String updateItem(String ownerId, String restaurantName, String itemName, int newPrice) throws RestoOwnerNotFoundException, RestaurantNotFoundException, ItemNotFoundException {

        List<Restaurant>restaurants=new ArrayList<>();
        List<Restaurant>restaurants1=new ArrayList<>();//this is taken repeatedly to intention of not using and change same parameter that is used to run the loop (below for loop is there)
        List<Item> itemList=new ArrayList<>();
        List<Item> itemList1=new ArrayList<>();
        if(restaurantRepositary.findById(ownerId).isEmpty())//checking provided restoOwner is available or not
        {
            throw new RestoOwnerNotFoundException();
        }
        else
        {

            RestoOwner restoOwner=restaurantRepositary.findById(ownerId).get();
            if(restoOwner.getRestaurant().isEmpty())//checking restaurant list is empty or not
            {
                throw new RestaurantNotFoundException();
            }
            else
            {
                restaurants=restoOwner.getRestaurant();
                restaurants1=restoOwner.getRestaurant();
                boolean flag=true;
                for(Restaurant restaurant:restaurants)//iterating through restaurant list of provided owner
                {

                    if(restaurant.getRestaurantName().equals(restaurantName))//checking provided restaurant is present or not
                    {

                        itemList=restaurant.getItem();
                        itemList1=restaurant.getItem();
                        flag=false;
                        if(restaurant.getItem().isEmpty())//if item list is empty then nothing to update
                        {
                            throw new ItemNotFoundException();
                        }
                        else
                        {

                            boolean flag1=true;
                            for(Item item:itemList)//Iterating through itemList to get required item by comparing itemName one by one
                            {
                               if(item.getItemName().equals(itemName))//matching itemName one by one
                                {

                                  flag1=false;
                                  for(int i=0;i<itemList1.size();i++)//this is only to remove old item
                                  {
                                      if(itemList1.get(i).getItemName().equals(itemName))
                                      {

                                          itemList1.remove(i);
                                      }
                                  }


                                  for(int i =0;i<restaurants1.size();i++)//this is to remove restaurant of old item
                                  {
                                      if(restaurants1.get(i).getRestaurantName().equals(restaurantName))
                                      {

                                          restaurants1.remove(i);
                                      }
                                  }



                                  restaurantRepositary.delete(restoOwner);//removing old restoOwner
                                  //this removal of old Item,Associated restaurant , associate restoOwner is to update and re add into repositary

                                 item.setItemPrice(newPrice);//changing old price to new price...this is the Update
                                 itemList1.add(item);//adding updated item into itemList
                                 restaurant.setItem(itemList1);//setting updated list to respective restaurant provided
                                restaurants1.add(restaurant);//adding updated restaurant to restaurant list
                                restoOwner.setRestaurant(restaurants1);//setting updated restaurant List to respective Owner provided
                                restaurantRepositary.save(restoOwner);//saving updated restoOwner into repositary.
                                }

                            }
                            if(flag1)
                            {
                                throw new ItemNotFoundException();
                            }
                        }
                    }

                }
                if(flag)
                {
                    throw new RestaurantNotFoundException();
                }
            }
        }
        return "Price Updated successfully";
    }


    @Override
    public String changeRestoStatus(String ownerId, String restaurantName) throws RestoOwnerNotFoundException, RestaurantNotFoundException  {
        if(restaurantRepositary.findById(ownerId).isEmpty())
        {
            throw new RestoOwnerNotFoundException();
        }
        RestoOwner restoOwner=restaurantRepositary.findById(ownerId).get();
        if(restoOwner.getRestaurant().isEmpty())
        {
            throw new RestaurantNotFoundException();
        }
        List<Restaurant> restaurants=restoOwner.getRestaurant();
        boolean flag=true;
        for(int i=0;i<restaurants.size();i++)
        {
            if (restaurants.get(i).getRestaurantName().equals(restaurantName) )
            {
                flag = false;
                restaurants.get(i).setStatus(true);
                restaurantRepositary.delete(restoOwner);
                restoOwner.setRestaurant(restaurants);
                restaurantRepositary.save(restoOwner);
            }
        }
        if(flag)
        {
            throw new RestaurantNotFoundException();
        }
        return "Restaurant is permitted by Admin";

    }



    private final String FOLDER_PATH="D:/Foodie Completed NIIT/foodieFronEnd/foodie/src/assets";
//    D:/Foodie Completed NIIT/Images
    public String uploadImage(MultipartFile multipartFile) throws IOException
    {
        String name=multipartFile.getOriginalFilename();
       // System.out.println("***Name "+name);
       // System.out.println("***Name "+name.substring(name.lastIndexOf(".")));
        String randomId= UUID.randomUUID().toString();
        String fileName=randomId.concat(name.substring(name.lastIndexOf(".")));
        String filePath=FOLDER_PATH+ File.separator+fileName;
        String extension=name.substring(name.lastIndexOf(".")+1);
        if(extension.equals("png")||extension.equals("jpg")||extension.equals("jpeg")) {
            Files.copy(multipartFile.getInputStream(), Paths.get(filePath));
            return fileName ;
        }
        else {
            return "File not uploaded due to format error" ;
        }
    }
}
