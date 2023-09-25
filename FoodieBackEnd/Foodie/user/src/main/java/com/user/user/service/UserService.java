package com.user.user.service;

import com.user.user.domain.Address;
import com.user.user.domain.ImageModel;
import com.user.user.domain.User;
import com.user.user.exception.EmailExists;
import com.user.user.exception.RestoOwnerNotFoundException;
import com.user.user.exception.UserAlreadyExistException;
import com.user.user.exception.UserNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> fetchAllUsers();
    User saveUser(User user) throws UserAlreadyExistException;
    User fetchAllUserByUserNameAndPassword(String emailId,String password) throws UserNotFoundException;
    boolean deleteUserByUserName(String emailId) throws RestoOwnerNotFoundException;
    Optional<User> updateUser(String emailId, Address address) throws UserNotFoundException;
    User getUser(String emailId) throws UserNotFoundException;
    String updateUserPic(String emailId, MultipartFile file) throws UserNotFoundException;
    boolean verifyAccount(String emailId, String otp) throws UserNotFoundException;
    boolean regerateOtp(String emailId) throws UserNotFoundException;
    String forgotPassword(String emailId) throws UserNotFoundException;
    String setPassword(String emailId, String newPassword) throws UserNotFoundException;
    ImageModel uploadImage(MultipartFile multipartFile) throws IOException;
//    user checking
    List<String> checkEmail();

}
