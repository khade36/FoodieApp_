package com.foodie.orders.domain;

public class Address {
    private int unitNo;
    private String buildingName;
    private String Street;
    private String landMark;
    private String area;
    private int pinCode;

    public Address(int unitNo, String buildingName, String street, String landMark, String area, int pinCode) {
        this.unitNo = unitNo;
        this.buildingName = buildingName;
        Street = street;
        this.landMark = landMark;
        this.area = area;
        this.pinCode = pinCode;
    }

    public Address() {
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

    @Override
    public String toString() {
        return "Address{" +
                "unitNo=" + unitNo +
                ", buildingName='" + buildingName + '\'' +
                ", Street='" + Street + '\'' +
                ", landMark='" + landMark + '\'' +
                ", area='" + area + '\'' +
                ", pinCode=" + pinCode +
                '}';
    }
}
