package com.user.user.service;

import com.user.user.domain.Address;
import com.user.user.domain.ImageModel;
import com.user.user.domain.User;
import com.user.user.exception.EmailExists;
import com.user.user.exception.RestoOwnerNotFoundException;
import com.user.user.exception.UserAlreadyExistException;
import com.user.user.exception.UserNotFoundException;
import com.user.user.proxy.UserCartProxy;
import com.user.user.proxy.UserFavProxy;
import com.user.user.proxy.UserOrderProxy;
import com.user.user.proxy.UserProxy;
import com.user.user.repositary.UserRepositary;
import com.user.user.util.EmailUtil;
import com.user.user.util.OtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepositary userrepositary;
    private UserProxy userProxy;
    private UserCartProxy userCartProxy;
    private OtpUtil otpUtil;
    private EmailUtil emailUtil;
    private UserOrderProxy userOrderProxy;
    private UserFavProxy userFavProxy;
    @Autowired
    public UserServiceImpl(UserRepositary userrepositary,EmailUtil emailUtil,UserProxy userProxy,UserFavProxy userFavProxy,UserCartProxy userCartProxy,UserOrderProxy userOrderProxy,OtpUtil otpUtil) {
        this.userrepositary = userrepositary;
        this.userProxy=userProxy;
        this.otpUtil=otpUtil;
        this.emailUtil=emailUtil;
        this.userCartProxy=userCartProxy;
        this.userOrderProxy=userOrderProxy;
        this.userFavProxy=userFavProxy;

    }
     public UserServiceImpl()
     {}
    @Override
    public List<User> fetchAllUsers() {
        return userrepositary.findAll();
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistException {
        boolean flag=false;
        for(int i=0;i<userrepositary.findAll().size();i++)
        {
          if(userrepositary.findById(user.getEmailId()).isPresent())
          {
                  flag=true;
          }
        }
        if(flag)
        {
            throw new UserAlreadyExistException();
        }
        else {
            System.out.println("inside else**");
            String otp=otpUtil.gererateOtp();
            try {
                emailUtil.sendOtpEmail(user.getEmailId(),otp);
            } catch (MessagingException e) {
                throw new RuntimeException("Unable to send otp please try again");
            }
            user.setOtp(otp);
            user.setOtpGeneratedTime(LocalDateTime.now());
            System.out.println("hr");
            userrepositary.save(user);
            return user;
        }

    }

    public User saveFeignUser(User user)
    {
        System.out.println("comes here2");

            if(user.getRole().equals("Owner"))
            {
                System.out.println("Comes here3");
                userProxy.saveRestoOwner(user);
             }
       else if(user.getRole().equals("User"))
        {
            System.out.println("Comes here3");
            userFavProxy.saveUser(user);
            userCartProxy.saveUser(user);
           userOrderProxy.saveUser(user);
        }
      return null;
    }


    @Override
    public User fetchAllUserByUserNameAndPassword(String emailId, String password) throws UserNotFoundException {
        User userobj= userrepositary.findByEmailIdAndPassword(emailId,password);
        if(userobj==null)
        {
            throw new UserNotFoundException();
        }
        return userobj;
    }



    @Override
    public boolean deleteUserByUserName(String emailId) throws RestoOwnerNotFoundException {
            userrepositary.deleteById(emailId);
            return true;


    }

    @Override
    public Optional<User> updateUser(String emailId, Address address) throws UserNotFoundException {
        if(userrepositary.findById(emailId).isEmpty())
        {
           throw new UserNotFoundException();
        }
        else {
            User user=userrepositary.findById(emailId).get();
                    if (user.getAddress() == null)
                    {
                        userrepositary.delete(user);
                        user.setAddress(Arrays.asList(address));
                        userrepositary.save(user);
                    }
                    else
                    {
                        List<Address> addresses = user.getAddress();
                        userrepositary.delete(user);
                        addresses.add(address);
                        user.setAddress(addresses);
                        userrepositary.save(user);
                   }
        }


        return  userrepositary.findById(emailId);

    }

    @Override
    public User getUser(String emailId) throws UserNotFoundException {

        if(userrepositary.findById(emailId).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user=userrepositary.findById(emailId).get();
        return user;
    }

    @Override
    public String updateUserPic(String emailId, MultipartFile file) throws UserNotFoundException {
        if(userrepositary.findById(emailId).isEmpty())
        {
            throw new UserNotFoundException();
        }
        else
        {
            User user=userrepositary.findById(emailId).get();
            userrepositary.delete(user);
            try {
                ImageModel image=uploadImage(file);
                user.setUserImages(image);
                System.out.println("uplaoded");
                userrepositary.save(user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    @Override
    public boolean verifyAccount(String emailId, String otp) throws UserNotFoundException {
        if(userrepositary.findById(emailId).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user=userrepositary.findById(emailId).get();
        if(user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(),LocalDateTime.now()).getSeconds()<300)
        {
            userrepositary.delete(user);
           user.setActive(true);
           userrepositary.save(user);
            return true;
        }
        else {
            return false;
        }
    }
    @Override
    public boolean regerateOtp(String emailId) throws UserNotFoundException {
        if(userrepositary.findById(emailId).isEmpty())
        {
            throw new UserNotFoundException();
        }

        String otp=otpUtil.gererateOtp();
        User user=userrepositary.findById(emailId).get();
        try {
            emailUtil.sendOtpEmail(user.getEmailId(),otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userrepositary.save(user);
      return true;
    }
    @Override
    public  String forgotPassword(String emailId) throws UserNotFoundException {
        if(userrepositary.findById(emailId).isEmpty())
        {
            throw new UserNotFoundException();
        }
        try {
            emailUtil.sendSetPasswordEmail(emailId);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to set password email please try again");
        }
        return null;
    }
    @Override
    public String setPassword(String emailId, String newPassword) throws UserNotFoundException
    {
        if(userrepositary.findById(emailId).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user=userrepositary.findById(emailId).get();
        user.setPassword(newPassword);
        userrepositary.save(user);
        return null;
    }
//    user checking --------------------------------------------------------
    @Override
    public List<String> checkEmail() {
        List<String>emailList=new ArrayList<>();
        List<User>userList=userrepositary.findAll();
        for(User user:userList)
        {
            emailList.add(user.getEmailId());
        }
        return emailList;
    }

    @Override
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

}
