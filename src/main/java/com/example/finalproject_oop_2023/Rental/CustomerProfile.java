package com.example.finalproject_oop_2023.Rental;

import com.example.finalproject_oop_2023.User.CustomerAccount;
import com.example.finalproject_oop_2023.User.User;

public class CustomerProfile {

    String currentUsers;
    public void setCurrentUsers(String currentUsers) {
        this.currentUsers = currentUsers;
    }

    public String getCurrentUsers() {
        return currentUsers;
    }

    public CustomerProfile(String currentUsers) {
        this.currentUsers = currentUsers;
    }

    @Override
    public String toString() {
        return currentUsers;
    }
}
