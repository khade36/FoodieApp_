package com.user.user.controller;

import com.user.user.domain.ImageModel;
import com.user.user.domain.User;
import com.user.user.exception.RestoOwnerNotFoundException;
import com.user.user.exception.UserNotFoundException;
import com.user.user.service.SecurityJsonWebToken;

import com.user.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserServiceImpl userservice;
    private SecurityJsonWebToken jsonWebToken;

    @Autowired
    public UserController(UserServiceImpl userservice, SecurityJsonWebToken jsonWebToken) {
        this.userservice = userservice;
        this.jsonWebToken = jsonWebToken;
    }

    public UserController() {
    }

    @PostMapping(value={"/signUp"},consumes={MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> saveUser(@RequestPart("user") User user,
                                      @RequestPart("imageFile")MultipartFile file) {
        try{
            System.out.println("Comes Here");
            ImageModel images=uploadImage(file);
           //userservice.saveRestoOwner(user);
            System.out.println("Comes here4");
            user.setUserImages(images);
            return new ResponseEntity<>(userservice.saveUser(user), HttpStatus.CREATED);
        } catch (Exception e) {
            e.getMessage();
           return null;
        }
    }

    @PutMapping("/verify-account")
    public ResponseEntity<?>verifyAccount(@RequestParam String emailId, @RequestParam String otp) throws UserNotFoundException {
       return new ResponseEntity<>(userservice.verifyAccount(emailId,otp),HttpStatus.OK);
    }


@PutMapping("/regenerate-otp")
public ResponseEntity<?> regenerateOtp(@RequestParam String emailId) throws UserNotFoundException {
       return new ResponseEntity<>(userservice.regerateOtp(emailId),HttpStatus.OK) ;
    }



 public ImageModel uploadImage(MultipartFile multipartFile) throws IOException
 {
     ImageModel imageModels;
     ImageModel imageModel=new ImageModel(
             multipartFile.getOriginalFilename(),
             multipartFile.getContentType(),
             multipartFile.getBytes()
     );
     imageModels=imageModel;
     return imageModels ;
 }


    @GetMapping("user/listOfUser")
    public ResponseEntity<?> fetchAllUser() {
        System.out.println(userservice.fetchAllUsers());
        return new ResponseEntity<>(userservice.fetchAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody User user) throws UserNotFoundException {
        System.out.println(user);
        User userObj = userservice.fetchAllUserByUserNameAndPassword(user.getEmailId(), user.getPassword());
        String token= null;
        if (userObj.getActive()) {
            token = jsonWebToken.generateToken(user);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }

        return new ResponseEntity<>("Please verify your email", HttpStatus.OK);
    }

    @PostMapping("user/userName")
    public ResponseEntity<?> userName(@RequestBody User user) throws UserNotFoundException {
        System.out.println(user);
        User userObj = userservice.fetchAllUserByUserNameAndPassword(user.getEmailId(), user.getPassword());
//        String token= null;
//        if (userObj.getEmailId().equals(user.getEmailId())) {
//            token = jsonWebToken.generateToken(user);
//        }
        return new ResponseEntity<>(userObj.getFirstName(), HttpStatus.OK);
    }


    @DeleteMapping("user/deleteUser/{emailId}")
    public ResponseEntity<?>deleteUser(@PathVariable String emailId) throws RestoOwnerNotFoundException {
        return new ResponseEntity<>(userservice.deleteUserByUserName(emailId), HttpStatus.OK);
    }

    @PatchMapping("user/update/{emailId}")
    public ResponseEntity<?> updateUser(@PathVariable String emailId,@RequestBody User user) {
        return new ResponseEntity<>(userservice.updateUser(emailId,user), HttpStatus.CREATED);
    }
}
