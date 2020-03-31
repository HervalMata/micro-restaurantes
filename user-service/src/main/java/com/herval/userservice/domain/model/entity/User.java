package com.herval.userservice.domain.model.entity;

public class User extends BaseEntity<String> {

    private String address;
    private String city;
    private String phoneNo;

    public User(String id, String name, String address, String city, String phoneNo) {
        super(id, name);
        this.address = address;
        this.city = city;
        this.phoneNo = phoneNo;
    }

    private User(String id, String name) {
        super(id, name);
    }

    public static User getDummyUser() {
        return new User(null, null);
    }
}
