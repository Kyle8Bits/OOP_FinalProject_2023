package com.example.finalproject_oop_2023.Rental;

import com.example.finalproject_oop_2023.Items.Item;
import com.example.finalproject_oop_2023.User.CustomerAccount;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private CustomerAccount customerAccount;
    private Item item;

    private String rentingStatus;

    private Rent rent;
    private Date calendar;
    private String time;

    public Transaction(CustomerAccount customerAccount, Item item) {
        this.customerAccount = customerAccount;
        this.item = item;
        this.rentingStatus= "Rent";
        calendar= Calendar.getInstance().getTime();
    }


    public Transaction(CustomerAccount customerAccount, Item item, String rentingStatus) {
        this.customerAccount = customerAccount;
        this.item = item;
        this.rentingStatus = rentingStatus;
        calendar= Calendar.getInstance().getTime();
    }

    public Transaction(CustomerAccount customerAccount, Item item, String rentingStatus, String time) {
        this.customerAccount = customerAccount;
        this.item = item;
        this.rentingStatus = rentingStatus;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public CustomerAccount getCustomerAccount() {
        return customerAccount;
    }

    public String getRentingStatus() {
        return rentingStatus;
    }

    public void setCustomerAccount(CustomerAccount customerAccount) {
        this.customerAccount = customerAccount;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setRentingStatus(String rentingStatus) {
        this.rentingStatus = rentingStatus;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Item getItem() {
        return item;
    }
    public Date getCalendar() {
        return calendar;
    }
    public void saveRentItem(){
        rent= new Rent();
        rent.saveToFile(item.getId() + "," + customerAccount.getUsername()+","+getRentingStatus()+","+calendar.getTime());
    }
    public void saveRentItem(String userRent){
        rent= new Rent();
        rent.saveToFile(item.getId() + "," + customerAccount.getUsername()+","+userRent+","+calendar.getTime());
    }
}
