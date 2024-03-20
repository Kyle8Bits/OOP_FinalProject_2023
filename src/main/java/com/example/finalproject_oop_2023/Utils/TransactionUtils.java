package com.example.finalproject_oop_2023.Utils;

import com.example.finalproject_oop_2023.Items.Item;
import com.example.finalproject_oop_2023.Rental.Transaction;
import com.example.finalproject_oop_2023.User.CustomerAccount;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransactionUtils {
    File file= new File("src/main/java/com/example/finalproject_oop_2023/SavingText/RentingList.txt");
    public List<Transaction> readFromDatabase(){

        List<Transaction> transactionArrayList = new ArrayList<>();
        try{

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;

            while( (line = bufferedReader.readLine()) != null){
                String[] tokens = line.split(",");
                String id= tokens[0];
                String rentingUsername= tokens[1];
                String rentingStatus= tokens[2];
                String times= tokens[3];
                transactionArrayList.add(new Transaction(new CustomerAccount(rentingUsername),new Item(id),rentingStatus,times));
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return transactionArrayList;
    }
}
