package com.user.user.service;

import com.user.user.domain.User;
import com.user.user.exception.RestoOwnerNotFoundException;
import com.user.user.exception.UserNotFoundException;
import com.user.user.proxy.UserCartProxy;
import com.user.user.proxy.UserProxy;
import com.user.user.repositary.UserRepositary;
import com.user.user.util.EmailUtil;
import com.user.user.util.OtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepositary userrepositary;
    private UserProxy userProxy;
    private UserCartProxy userCartProxy;
    private OtpUtil otpUtil;
    private EmailUtil emailUtil;
    @Autowired
    public UserServiceImpl(UserRepositary userrepositary,EmailUtil emailUtil,UserProxy userProxy,OtpUtil otpUtil) {
        this.userrepositary = userrepositary;
        this.userProxy=userProxy;
        this.otpUtil=otpUtil;
        this.emailUtil=emailUtil;

    }
     public UserServiceImpl()
     {}
    @Override
    public List<User> fetchAllUsers() {
        return userrepositary.findAll();
    }

    @Override
    public User saveUser(User user)
    {
        String otp=otpUtil.gererateOtp();
        try {
            emailUtil.sendOtpEmail(user.getEmailId(),otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
         userrepositary.save(user);
         return user;
    }

    public User saveRestoOwner(User user)
    {
        System.out.println("comes here2");

            if(user.getRole().equals("User"))
            {
                System.out.println("Comes here3");
                userCartProxy.saveUser(user);
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
    public Optional<User> updateUser(String emailId, User user)
    {
        for(int i=0;i<userrepositary.findAll().size();i++)
        {
            User t =userrepositary.findAll().get(i);
            if(t.getEmailId().equals(emailId))
            {
                userrepositary.deleteById(emailId);
               t.setUnitNo(user.getUnitNo());
               userrepositary.save(t);
            }
        }


        return  userrepositary.findById(emailId);

    }

    public String verifyAccount(String emailId, String otp) throws UserNotFoundException {
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
            return "OTP verified you can login";
        }
        return "Please regenerate OTP and try aqin";

    }

    public String regerateOtp(String emailId) throws UserNotFoundException {
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
      return "Email sent...please verify account within 5 minutes";
    }
}
