package com.example.finalproject_oop_2023.Utils;

import com.example.finalproject_oop_2023.Items.Item;
import com.example.finalproject_oop_2023.User.CustomerAccount;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class AccountUtils {
    static File file = new File("src/main/java/com/example/finalproject_oop_2023/SavingText/Account.txt");

    public List<String> getTheList(File file) throws IOException {
        List<String> listLine = Files.readAllLines(file.toPath());
        return listLine;
    }
    public List<CustomerAccount> readFromDatabase(){

        List<CustomerAccount> accountAdd = new ArrayList<>();
        try{

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;

            while( (line = bufferedReader.readLine()) != null){
                String[] tokens = line.split(",");
                //generateId(), username1, password1, name, address, phone, "Guest", 0
                //Split to each part of array
                String id = tokens[0];
                String username = tokens[1];
                String password= tokens[2];
                String name= tokens[3];
                String address= tokens[4];
                String phone= tokens[5];
                String role= tokens[6];
                int point= Integer.parseInt(tokens[7]);

                accountAdd.add(new CustomerAccount(id,username,password,name,address,phone,role,point));
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return accountAdd;
    }

    public ArrayList<String> readFile() throws IOException {


        ArrayList<String> lines= new ArrayList<>();

        BufferedReader bufferedReader= new BufferedReader(new FileReader(file));
        String line;
        while((line = bufferedReader.readLine()) != null){
            lines.add(line);
        }

        ArrayList<String[]> valueList= new ArrayList<>();
        for(String linevalue: lines){
            String[] tokens= linevalue.split(",");
            valueList.add(tokens);
        }
        bufferedReader.close();
        return lines;
    }

    public void saveAccount(ArrayList<CustomerAccount> users) throws IOException {
        BufferedWriter writer2 = new BufferedWriter(new FileWriter("src/main/java/com/example/finalproject_oop_2023/SavingText/Account.txt"));
        for (CustomerAccount customerAccount : users) {
            String line = accountoString(customerAccount);
            writer2.write(line);
            writer2.newLine();
        }
        writer2.flush();
    }

    private static String accountoString(CustomerAccount customerAccount) {
        StringBuilder sb = new StringBuilder();
        sb.append(customerAccount.getId()).append(",");
        sb.append(customerAccount.getUsername()).append(",");
        sb.append(customerAccount.getPassword()).append(",");
        sb.append(customerAccount.getName()).append(",");
        sb.append(customerAccount.getAddress()).append(",");
        sb.append(customerAccount.getPhone()).append(",");
        sb.append(customerAccount.getRole()).append(",");
        sb.append(customerAccount.getPoint());
        return sb.toString();
    }


}
