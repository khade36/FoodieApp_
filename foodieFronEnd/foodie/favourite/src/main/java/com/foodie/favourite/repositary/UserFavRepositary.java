package com.foodie.favourite.repositary;

import com.foodie.favourite.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFavRepositary extends MongoRepository<User,String> {
}
