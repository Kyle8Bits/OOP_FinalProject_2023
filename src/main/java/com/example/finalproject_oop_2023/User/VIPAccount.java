package com.example.finalproject_oop_2023.User;

public class VIPAccount extends CustomerAccount{

    public VIPAccount(String id, String username, String password) {
        super(id, username, password);
    }

    public VIPAccount(String id, String username, String password, String name, String address, String phone, int point) {
        super(id, username, password, name, address, phone, "VIP", point);
    }

    public VIPAccount(String username) {
        super(username);
    }
}
