package com.user.user.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class User {

    private String firstName;
    private String lastName;
    private long phoneNo;
    @Id
    private String emailId;
    private String password;
    @OneToMany(targetEntity = Address.class,cascade = CascadeType.ALL)
    @JoinColumn(name="user_address",referencedColumnName = "emailId")
    private List<Address> address;
    private String role;
    private boolean active;
    private String otp;
    private LocalDateTime otpGeneratedTime;
    @OneToOne(fetch= FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="user_images",
    joinColumns = {
            @JoinColumn(name="email_id")
    },
            inverseJoinColumns = {
            @JoinColumn(name="image_id")
            }
    )
    private ImageModel userImages;

    public User(String firstName, String lastName,
                long phoneNo, String emailId, String password,
                int unitNo, String buildingName, String street,
                String landMark, String area, int pinCode,
                String role,boolean active, String otp,LocalDateTime otpGeneratedTime,ImageModel userImages) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.emailId = emailId;
        this.password = password;
        this.role = role;
        this.active=active;
        this.otp=otp;
        this. otpGeneratedTime=otpGeneratedTime;
        this.userImages = userImages;
    }

    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getOtpGeneratedTime() {
        return otpGeneratedTime;
    }

    public void setOtpGeneratedTime(LocalDateTime otpGeneratedTime) {
        this.otpGeneratedTime = otpGeneratedTime;
    }

    public ImageModel getUserImages() {
        return userImages;
    }

    public void setUserImages(ImageModel userImages) {
        this.userImages = userImages;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNo=" + phoneNo +
                ", emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                ", address=" + address +
                ", role='" + role + '\'' +
                ", active=" + active +
                ", otp='" + otp + '\'' +
                ", otpGeneratedTime=" + otpGeneratedTime +
                ", userImages=" + userImages +
                '}';
    }
}
