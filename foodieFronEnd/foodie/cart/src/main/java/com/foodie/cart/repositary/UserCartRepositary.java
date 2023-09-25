package com.foodie.cart.repositary;

import com.foodie.cart.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCartRepositary extends MongoRepository<User,String> {

}
