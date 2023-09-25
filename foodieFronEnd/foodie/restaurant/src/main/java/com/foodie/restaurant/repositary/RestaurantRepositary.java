package com.foodie.restaurant.repositary;


import com.foodie.restaurant.domain.RestoOwner;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;



@Repository
public interface RestaurantRepositary extends MongoRepository<RestoOwner,String> {


}
