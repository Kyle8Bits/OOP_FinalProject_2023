package com.example.finalproject_oop_2023.User;

import com.example.finalproject_oop_2023.Items.Item;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CustomerAccount extends User {

    private String name;
    private String address;
    private String phone;
    private String role;

    private int point;

    private final ArrayList<Item> rentedItems= new ArrayList<>();




    public CustomerAccount(String id, String username, String password) {
        super( id,username, password);
    }

    public CustomerAccount(String id, String username, String password, String name, String address, String phone, String role, int point) {
        super( id, username, password);
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.role = role;
        this.point = point;
    }

    public CustomerAccount(String username) {
        super(username);
    }



    public void addRental(Item rental){
        this.rentedItems.add(rental);
        System.out.println("Linked item" + rental.getId()+ "to account"+ this.getId());
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public ArrayList<Item> getRentedItems() {
        return rentedItems;
    }

    @Override
    public String toString() {
        return "CustomerAccount{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                ", point=" + point +
                ", rentedItems=" + rentedItems +
                '}';
    }
}
