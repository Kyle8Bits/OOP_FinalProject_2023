package com.example.finalproject_oop_2023.Rental;

import com.example.finalproject_oop_2023.Items.Item;

import java.io.File;

public class ItemRenting{

    private String idRenting;
    private String titleRenting;
    private RentalType typeRenting;
    private LoanType loanTypeRenting;
    private Genre genreRenting;
    private String feeRenting;

    public String getIdRenting() {
        return idRenting;
    }

    public void setIdRenting(String idRenting) {
        this.idRenting = idRenting;
    }

    public String getTitleRenting() {
        return titleRenting;
    }

    public void setTitleRenting(String titleRenting) {
        this.titleRenting = titleRenting;
    }

    public RentalType getTypeRenting() {
        return typeRenting;
    }

    public void setTypeRenting(RentalType typeRenting) {
        this.typeRenting = typeRenting;
    }

    public LoanType getLoanTypeRenting() {
        return loanTypeRenting;
    }

    public void setLoanTypeRenting(LoanType loanTypeRenting) {
        this.loanTypeRenting = loanTypeRenting;
    }

    public Genre getGenreRenting() {
        return genreRenting;
    }

    public void setGenreRenting(Genre genreRenting) {
        this.genreRenting = genreRenting;
    }

    public String getFeeRenting() {
        return feeRenting;
    }

    public void setFeeRenting(String feeRenting) {
        this.feeRenting = feeRenting;
    }

    public ItemRenting(){

    }

    public ItemRenting(String idRenting, String titleRenting, RentalType typeRenting, LoanType loanTypeRenting, Genre genreRenting, String feeRenting) {

        this.idRenting = idRenting;
        this.titleRenting = titleRenting;
        this.typeRenting = typeRenting;
        this.loanTypeRenting = loanTypeRenting;
        this.genreRenting = genreRenting;
        this.feeRenting = feeRenting;
    }



    public enum RentalType{
        OLD_MOVIE_RECORD,
        DVD,
        VIDEO_GAME
    }

    public enum Genre{
        ACTION,
        HORROR,
        DRAMA,
        COMEDY,
        NOT_APPLICABLE
    }

    public enum LoanType{
        TWO_DAYS_LOAN,
        ONE_WEEK_LOAN
    }



}

