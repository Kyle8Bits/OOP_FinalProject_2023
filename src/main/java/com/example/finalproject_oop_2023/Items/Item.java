package com.example.finalproject_oop_2023.Items;

public class Item implements Comparable<Item>{
    private String id;
    private String title;
    private RentalType rentalType;
    private LoanType loanType;
    private Genre genre;
    private int copies;
    private String fee;

//    private String rentalStatus;

    /*------------/*
    --CONSTRUCTOR--
    /*--------------*/
    public Item(String id)
    {this.id= id;}
    public Item(String id, String title, RentalType rentalType, LoanType loanType, Genre genre, int copies, String fee) {
        this.id = id;
        this.title = title;
        this.rentalType = rentalType;
        this.loanType = loanType;
        this.genre = genre;
        this.copies = copies;
        this.fee = fee;
    }

    /*------------/*
     --GETTER & SETTER
    /*--------------*/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RentalType getRentalType() {
        return rentalType;
    }



    public LoanType getLoanType() {
        return loanType;
    }



    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getCopies() {
        return copies;
    }

    public int setCopies(int copies) {
        this.copies = copies;
        return copies;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    @Override
    public int compareTo(Item o) {
        return this.id.compareTo(o.id);
    }
    /*------------/*
     ENUM
    /*--------------*/

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




    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", rentalType=" + rentalType +
                ", loanType=" + loanType +
                ", genre=" + genre +
                ", copies=" + copies +
                ", fee='" + fee + '\'' +
                '}';
    }
}
