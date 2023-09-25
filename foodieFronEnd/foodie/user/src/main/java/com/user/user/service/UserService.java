package com.user.user.service;

import com.user.user.domain.User;
import com.user.user.exception.RestoOwnerNotFoundException;
import com.user.user.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> fetchAllUsers();
    User saveUser(User user);
    User fetchAllUserByUserNameAndPassword(String emailId,String password) throws UserNotFoundException;
    boolean deleteUserByUserName(String emailId) throws RestoOwnerNotFoundException;
    Optional<User> updateUser(String emailId, User user);
}
