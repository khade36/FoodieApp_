package com.foodie.orders.repositary;


import com.foodie.orders.domain.Order;
import com.foodie.orders.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOrderRepositary extends MongoRepository<User,String>
{
//    List<Order> findByOwnerId(String ownerId);
}
