package com.user.user.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class User {

    private String firstName;
    private String lastName;
    private long phoneNo;
    @Id
    private String emailId;
    private String password;
    private int unitNo;
    private String buildingName;
    private String Street;
    private String landMark;
    private String area;
    private int pinCode;
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
        this.unitNo = unitNo;
        this.buildingName = buildingName;
        Street = street;
        this.landMark = landMark;
        this.area = area;
        this.pinCode = pinCode;
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

    public int getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(int unitNo) {
        this.unitNo = unitNo;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
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
                ", flatNo=" + unitNo +
                ", buildingName='" + buildingName + '\'' +
                ", Street='" + Street + '\'' +
                ", landMark='" + landMark + '\'' +
                ", area='" + area + '\'' +
                ", pinCode=" + pinCode +
                ", role='" + role + '\'' +
                '}';
    }
}
