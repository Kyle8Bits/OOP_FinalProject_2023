package com.example.finalproject_oop_2023.Rental;

import com.example.finalproject_oop_2023.Items.Item;
import com.example.finalproject_oop_2023.User.CustomerAccount;

import java.io.FileWriter;
import java.io.IOException;

public class Rent {

    public void saveToFile(String content) {
        try (FileWriter writer = new FileWriter("src/main/java/com/example/finalproject_oop_2023/SavingText/RentingList.txt", true)) {
            writer.write(content + System.lineSeparator());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}