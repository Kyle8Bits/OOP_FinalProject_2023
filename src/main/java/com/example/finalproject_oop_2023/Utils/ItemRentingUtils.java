package com.example.finalproject_oop_2023.Utils;

import com.example.finalproject_oop_2023.Items.Item;
import com.example.finalproject_oop_2023.Rental.ItemRenting;
import com.example.finalproject_oop_2023.Rental.Transaction;
import com.example.finalproject_oop_2023.controller.UserGUIController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemRentingUtils {

    private final File rentingListFile = new File("src/main/java/com/example/finalproject_oop_2023/SavingText/RentingList.txt");


    public ArrayList<Transaction> readRentingList() throws IOException {
        ArrayList<Transaction> rentingList= new ArrayList<>();

        return rentingList;
    }
    public List<ItemRenting> readFromDatabase(String username){
        System.out.println(username);
        List<ItemRenting> itemRentingList = new ArrayList<>();
        try{
            RandomAccessFile randomAccessFile = new RandomAccessFile(rentingListFile,"rw");
            String line;

            while( (line = randomAccessFile.readLine()) != null) {
                String[] tokens = line.split(",");
                String idRenting = tokens[0];
                String userRenting = tokens[1];
                String status = tokens[2];

                RandomAccessFile randomAccessFileItem = new RandomAccessFile("src/main/java/com/example/finalproject_oop_2023/SavingText/items.txt", "r");
                while ((line = randomAccessFileItem.readLine()) != null) {
                    String[] tokensItem = line.split(",");
                    String idCheck = tokensItem[0];
                    String title = tokensItem[1];
                    String rentalTypeString = tokensItem[2];
                    ItemRenting.RentalType rentalType = convertRentalType(rentalTypeString);
                    String loanTypeString = tokensItem[3];
                    ItemRenting.LoanType loanType = convertLoanType(loanTypeString);
                    String fee = tokensItem[5];

                    String genreString = tokensItem[6];
                    ItemRenting.Genre genre = convertGenre(genreString);

                    if (status.equalsIgnoreCase("Rent") && idCheck.equals(idRenting) && userRenting.equals(username)){
                        itemRentingList.add(new ItemRenting(idRenting,title,rentalType,loanType,genre,fee));
                    }
                }
            }

            randomAccessFile.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return itemRentingList;
    }

    public static ItemRenting.RentalType convertRentalType(String rentalType){
        if (rentalType.equalsIgnoreCase("Record") || rentalType.equalsIgnoreCase("OLD_MOVIE_RECORD")
                || rentalType.equalsIgnoreCase("Old movie records")){
            return ItemRenting.RentalType.OLD_MOVIE_RECORD;
        }
        if (rentalType.equalsIgnoreCase("DVD") || rentalType.equalsIgnoreCase("DVDs")){
            return ItemRenting.RentalType.DVD;

        }
        if (rentalType.equalsIgnoreCase("Game") || rentalType.equalsIgnoreCase("VIDEO_GAME")
                || rentalType.equalsIgnoreCase("Video Games")){
            return ItemRenting.RentalType.VIDEO_GAME;
        }

        return null;
    }
    public static ItemRenting.LoanType convertLoanType(String loanType){
        if(loanType.equalsIgnoreCase("1-week") || loanType.equalsIgnoreCase("ONE_WEEK_LOAN")
                || loanType.equalsIgnoreCase("1 week loan")){
            return ItemRenting.LoanType.ONE_WEEK_LOAN;
        }
        if(loanType.equalsIgnoreCase("2-day") || loanType.equalsIgnoreCase("TWO_DAYS_LOAN")
                || loanType.equalsIgnoreCase("2 days loan")){
            return ItemRenting.LoanType.TWO_DAYS_LOAN;
        }

        return null;
    }

    public static ItemRenting.Genre convertGenre(String genre) {
        if(genre.equalsIgnoreCase("Action") || genre.equalsIgnoreCase("ACTION")){
            return ItemRenting.Genre.ACTION;
        }
        if(genre.equalsIgnoreCase("Horror") || genre.equalsIgnoreCase("HORROR")){
            return ItemRenting.Genre.HORROR;
        }
        if(genre.equalsIgnoreCase("Drama") || genre.equalsIgnoreCase("DRAMA")){
            return ItemRenting.Genre.DRAMA;
        }
        if(genre.equalsIgnoreCase("Comedy") || genre.equalsIgnoreCase("COMEDY")){
            return ItemRenting.Genre.COMEDY;
        }
        if(genre.equalsIgnoreCase("") || genre.equalsIgnoreCase("N/A") || genre.equalsIgnoreCase("NOT_APPLICABLE")){
            return ItemRenting.Genre.NOT_APPLICABLE;
        }

        return null;
    }

    public void updateItem(ArrayList<Transaction>transactionArrayList ) throws IOException {
        BufferedWriter writer1 = new BufferedWriter(new FileWriter("src/main/java/com/example/finalproject_oop_2023/SavingText/RentingList.txt"));
        for (Transaction transaction : transactionArrayList) {
            String line = rentingToString(transaction);
            writer1.write(line);
            writer1.newLine();
        }
        writer1.close();
    }
    private static String rentingToString(Transaction transaction) {
        StringBuilder sb = new StringBuilder();
        sb.append(transaction.getItem().getId()).append(",");
        sb.append(transaction.getCustomerAccount().getUsername()).append(",");
        sb.append(transaction.getRentingStatus()).append(",");
        sb.append(transaction.getTime());

        return sb.toString();
    }
}
