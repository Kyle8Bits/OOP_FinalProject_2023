package com.example.finalproject_oop_2023.CreatAccount;

import com.example.finalproject_oop_2023.Rental.CustomerProfile;
import com.example.finalproject_oop_2023.User.CustomerAccount;


import java.io.*;


public class Authentication {
    private CustomerProfile customerProfile;

    public Authentication() {
        this.customerProfile = null;
    }

    private static int count = 1;
    //private HashMap<String, CustomerAccount> list = new HashMap<>();

    File file = new File("src/main/java/com/example/finalproject_oop_2023/SavingText/Account.txt");

    public void createAccountFolder() {
        if (!file.exists()) {
            file.mkdirs();
        }
    }


    public void readFile() {
        try {
            FileReader fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            try {
                FileWriter fileWriter = new FileWriter(file);
                System.out.println("File created");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        System.out.println("file exist! ");
        File file = new File("Account.txt");
        System.out.println(file.getAbsolutePath());

    }


    public boolean addAccount(String username1, String password1, String name, String address, String phone){

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(randomAccessFile.length());
            if (checkUsername(username1)) {
                return false;
            } else {
                CustomerAccount user = new CustomerAccount(generateId(), username1, password1, name, address, phone, "Guest", 1);
                String data = user.getId() + "," + user.getUsername() + "," + user.getPassword() + "," + user.getName() + "," + user.getAddress() + "," + user.getPhone() + "," + user.getRole() + "," + user.getPoint();
                randomAccessFile.writeBytes(data + "\n");
                System.out.println("Register successfully");
                return true;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File cannot null");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addAccount(String username1, String password1){
        try {

            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(randomAccessFile.length());
            if (checkUsername(username1)) {
                return false;
            } else {
                CustomerAccount user = new CustomerAccount(generateId(), username1, password1, "null", "null", "null", "Guest", 1);
                String data = user.getId() + "," + user.getUsername() + "," + user.getPassword() + "," + user.getName() + "," + user.getAddress() + "," + user.getPhone() + "," + user.getRole() + "," + user.getPoint();
                randomAccessFile.writeBytes(data + "\n");
                System.out.println("Register successfully");
                return true;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File cannot null");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateId() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        String line;
        String id = null;
        while ((line = randomAccessFile.readLine()) != null) {
            String[] tokens = line.split(",");
            id = tokens[0];
        }
        if (id == null) {
            return "C" + "001";
        } else {
            System.out.println(id);
            String numberStr = id.substring(1);
            int afterIdScan = Integer.parseInt(numberStr);

            String customerId = String.format("C%03d", count + afterIdScan);
            return customerId;
        }
    }


    //Login function
    public boolean loginAccount(String username1, String password1){

        try {
            String username = null;
            String password = null;
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            String line;
            while (((line = randomAccessFile.readLine()) != null)) {
                String[] tokens = line.split(",");
                username = tokens[1];
                password = tokens[2];
                if (username1.equals(username) && password1.equals(password)) {
                    customerProfile = new CustomerProfile(username1);
                    System.out.println("LOGIN SUCCESS"+" // Welcome "+ customerProfile.getCurrentUsers());
                    return true;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public CustomerProfile getCustomerProfile() {
        return customerProfile;
    }

    //Checking if the username really exist
    public boolean checkUsername(String username) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        String user = null;
        String line;
        while ((line = randomAccessFile.readLine()) != null) {
            String[] tokens = line.split(",");
            user = tokens[1];
            if (username.equals(user) ) {
                return true;
            }
        }
        return false;
    }

}
