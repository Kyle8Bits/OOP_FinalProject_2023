package com.example.finalproject_oop_2023.User;

public class RegularAccount extends CustomerAccount{


    public RegularAccount(String id, String username, String password) {
        super(id, username, password);
    }

    public RegularAccount(String id, String username, String password, String name, String address, String phone, int point) {
        super(id, username, password, name, address, phone, "Regular", point);
    }

    public RegularAccount(String username) {
        super(username);
    }
}
