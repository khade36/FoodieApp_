package com.user.user.controller;

import com.user.user.domain.Address;
import com.user.user.domain.ImageModel;
import com.user.user.domain.User;
import com.user.user.exception.EmailExists;
import com.user.user.exception.RestoOwnerNotFoundException;
import com.user.user.exception.UserAlreadyExistException;
import com.user.user.exception.UserNotFoundException;
import com.user.user.service.SecurityJsonWebToken;

import com.user.user.service.UserServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class UserController {
    private ResponseEntity<?> responseEntity;
    private UserServiceImpl userservice;
    private SecurityJsonWebToken jsonWebToken;

    @Autowired
    public UserController(UserServiceImpl userservice, SecurityJsonWebToken jsonWebToken) {
        this.userservice = userservice;
        this.jsonWebToken = jsonWebToken;
    }

    public UserController() {
    }

    //@PostMapping(value={"/signUp"},consumes={MediaType.MULTIPART_FORM_DATA_VALUE})
    @PostMapping("/signUp")
    public ResponseEntity<?> saveUser(@RequestPart("user") User user,
                                      @RequestPart("imageFile")MultipartFile file) {
        try{
            System.out.println("Comes Here");
            ImageModel images=userservice.uploadImage(file);
            userservice.saveFeignUser(user);
            System.out.println("Comes here4");
            user.setUserImages(images);
            return new ResponseEntity<>(userservice.saveUser(user), HttpStatus.CREATED);
        } catch (UserAlreadyExistException | IOException e) {
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


@PutMapping("/forget-password")
public ResponseEntity<?> forgotPassword(@RequestParam String emailId) throws UserNotFoundException {

    return new ResponseEntity<>(userservice.forgotPassword(emailId),HttpStatus.OK) ;
}

@PutMapping("/set-password")
public ResponseEntity<?> setPassword(@RequestParam String emailId,@RequestParam String newPassword) throws UserNotFoundException {
    return new ResponseEntity<>(userservice.setPassword(emailId,newPassword),HttpStatus.OK) ;
}




    @GetMapping("user/listOfUser")
    public ResponseEntity<?> fetchAllUser() {
        System.out.println(userservice.fetchAllUsers());
        return new ResponseEntity<>(userservice.fetchAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody User user) throws UserNotFoundException {
       // System.out.println(user);
        User userObj = userservice.fetchAllUserByUserNameAndPassword(user.getEmailId(), user.getPassword());
        String token= null;
        if (userObj.getActive()) {
            token = jsonWebToken.generateToken(user);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }

        return new ResponseEntity<>("Please verify your email", HttpStatus.OK);
    }

    @PostMapping("/checkRole")
    public ResponseEntity<?> checkRole(@RequestBody User user ) throws UserNotFoundException {
       // System.out.println("got herrrrre");
        User userObj = userservice.fetchAllUserByUserNameAndPassword(user.getEmailId(),user.getPassword());
       // System.out.println("*******"+userObj);
            return new ResponseEntity<>(userObj, HttpStatus.OK);
        }

    @PostMapping("user/userName")
    public ResponseEntity<?> userName(@RequestBody User user) throws UserNotFoundException {
      //  System.out.println(user);
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

    @PostMapping("user/updateAddress")
    public ResponseEntity<?> updateUser(@RequestBody Address address,HttpServletRequest request)
    {
        try {
           // System.out.println("header" + request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
         //   System.out.println("emailId from claims :: " + claims.getSubject());
            String emailId = claims.getSubject();
         //   System.out.println("emailId :: " + emailId);
            responseEntity = new ResponseEntity<>(userservice.updateUser(emailId, address), HttpStatus.CREATED);
             }
              catch ( UserNotFoundException e)
              {
                e.getMessage();
              }
        return responseEntity;
        }

    @PostMapping("user/updatePic")
    public ResponseEntity<?> updateUserPic(@RequestBody MultipartFile file,HttpServletRequest request)
    {
        try {
           // System.out.println("header" + request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
           // System.out.println("emailId from claims :: " + claims.getSubject());
            String emailId = claims.getSubject();
            //System.out.println("emailId :: " + emailId);
            responseEntity = new ResponseEntity<>(userservice.updateUserPic(emailId, file), HttpStatus.CREATED);
        }
        catch ( UserNotFoundException e)
        {
            e.getMessage();
        }
        return responseEntity;
    }

    @GetMapping("user/user")
        public ResponseEntity<?>getUser(HttpServletRequest request) {
        try {
          //  System.out.println("header" + request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            //System.out.println("emailId from claims :: " + claims.getSubject());
            String emailId = claims.getSubject();
            //System.out.println("emailId :: " + emailId);
            responseEntity= new ResponseEntity<>(userservice.getUser(emailId), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            e.getMessage();
        }

    return responseEntity;
    }

    @GetMapping("/checkEmail")
    public ResponseEntity<?>checkEmail() {
        responseEntity= new ResponseEntity<>(userservice.checkEmail(), HttpStatus.OK);
        return responseEntity;
    }
}
