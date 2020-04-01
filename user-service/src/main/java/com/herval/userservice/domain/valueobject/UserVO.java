package com.herval.userservice.domain.valueobject;

public class UserVO {

    private String id;
    private String name;
    private String address;
    private String city;
    private String phoneNo;

    public UserVO(String id, String name, String address, String city, String phoneNo) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.phoneNo = phoneNo;
    }

    public UserVO() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("{id: %s, name: %s, address: %s, city: %s, phoneNo: %s}", id, name, address, city, phoneNo);
    }
}
