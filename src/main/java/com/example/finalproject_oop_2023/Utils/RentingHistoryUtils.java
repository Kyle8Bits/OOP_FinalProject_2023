package com.example.finalproject_oop_2023.Utils;

import com.example.finalproject_oop_2023.Items.Item;
import com.example.finalproject_oop_2023.Rental.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class RentingHistoryUtils {
    private final File file= new File("src/main/java/com/example/finalproject_oop_2023/SavingText/RentingList.txt");

    private ItemUtils itemRentingUtils= new ItemUtils();
    private TransactionUtils transactionUtils= new TransactionUtils();
    public List<RentingHistory> readFromDatabase(String username){
        System.out.println("Username in history" + username);
        String titleRenting= null;
        String feeRenting=null;
        ArrayList<Transaction> transactionArrayList= (ArrayList<Transaction>) transactionUtils.readFromDatabase();
        ArrayList<Item> itemRentings= (ArrayList<Item>) itemRentingUtils.readFromDatabase();
        ArrayList<RentingHistory> rentingHistories = new ArrayList<>();
        try{

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;

            while( (line = bufferedReader.readLine()) != null){
                String[] tokens = line.split(",");
                String id= tokens[0];
                String rentingUsername= tokens[1];
                String rentingStatus= tokens[2];
                String times= tokens[3];
                for(Item item: itemRentings){
                    if(id.equals(item.getId()) && rentingUsername.equals(username) && !Objects.equals(rentingStatus, "Rent")){
                        titleRenting= item.getTitle();
                        feeRenting=item.getFee();
                        long timeInmilis= Long.parseLong(times);
                        Date date= new Date(timeInmilis);
                        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String formattedTime= sdf.format(date);
                        rentingHistories.add(new RentingHistory(id,titleRenting,rentingUsername,feeRenting,rentingStatus,formattedTime));
                    }
                }

            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rentingHistories;
    }

    public int returnNumberofReting(String username){
        int count=0;
        ArrayList<RentingHistory> rentingHistories= (ArrayList<RentingHistory>) readFromDatabaseAdmin();

        for(RentingHistory rentingHistory: rentingHistories){
            if(username.equals(rentingHistory.getUsernameRenting()) && rentingHistory.getRentingStatus().equals("Rent")){

                count++;
            }
        }
        System.out.println("Final count" + count);
        return count;
    }
    public List<RentingHistory> readFromDatabaseAdmin() {
        String titleRenting;
        String feeRenting;
        ArrayList<Transaction> transactionArrayList = (ArrayList<Transaction>) transactionUtils.readFromDatabase();
        ArrayList<Item> itemRentings = (ArrayList<Item>) itemRentingUtils.readFromDatabase();
        ArrayList<RentingHistory> rentingHistories = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split(",");
                String id = tokens[0];
                String rentingUsername = tokens[1];
                String rentingStatus = tokens[2];
                String times = tokens[3];

                // Find the corresponding item for this line
                Item matchingItem = null;
                for (Item item : itemRentings) {
                    if (item.getId().equals(id)) {
                        matchingItem = item;
                        break;
                    }
                }

                if (matchingItem != null) {
                    titleRenting = matchingItem.getTitle();
                    feeRenting = matchingItem.getFee();
                    long timeInMilis = Long.parseLong(times);
                    Date date = new Date(timeInMilis);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedTime = sdf.format(date);
                    rentingHistories.add(new RentingHistory(id, titleRenting, rentingUsername, feeRenting, rentingStatus, formattedTime));
                }
            }

            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return rentingHistories;
    }

}
