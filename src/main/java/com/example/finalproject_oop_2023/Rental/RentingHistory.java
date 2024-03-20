package com.example.finalproject_oop_2023.Rental;

import java.util.Collection;

public class RentingHistory implements Comparable<ItemRenting> {
    private String idRenting;
    private String titleRenting;
    private String usernameRenting;

    private String feeRenting;
    private String rentingStatus;
    private String time;


    public RentingHistory(String idRenting, String titleRenting, String usernameRenting, String feeRenting, String rentingStatus, String time) {
        this.idRenting = idRenting;
        this.titleRenting = titleRenting;
        this.usernameRenting = usernameRenting;
        this.feeRenting = feeRenting;
        this.rentingStatus = rentingStatus;
        this.time = time;
    }

    public String getIdRenting() {
        return idRenting;
    }

    public String getTitleRenting() {
        return titleRenting;
    }

    public String getUsernameRenting() {
        return usernameRenting;
    }

    public String getFeeRenting() {
        return feeRenting;
    }

    public String getRentingStatus() {
        return rentingStatus;
    }

    public String getTime() {
        return time;
    }

    @Override
    public int compareTo(ItemRenting o) {
        return 0;
    }
}
