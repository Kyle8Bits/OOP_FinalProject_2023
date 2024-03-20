package com.example.finalproject_oop_2023.controller;

import com.example.finalproject_oop_2023.Main;
import com.example.finalproject_oop_2023.Utils.AccountUtils;
import com.example.finalproject_oop_2023.supporter.Alertsup;
import com.example.finalproject_oop_2023.supporter.Checking;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Edit_InfoGUIController implements Initializable {
    Checking checking = new Checking();
    private final File file= new File("src/main/java/com/example/finalproject_oop_2023/SavingText/Account.txt");
    Parent root;
    Stage stage;
    Scene scene;
    private String username= null;

    @FXML
     private TextField full_nameTextField;
    @FXML
     private TextField addressTextField;
    @FXML
     private TextField phoneTextField;
    @FXML
     private TextField passwordTextField;

    private AccountUtils accountUtils= new AccountUtils();


    @FXML
    public void onReturnButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("user-dashboard.fxml"));
        root= fxmlLoader.load();
        UserGUIController userGUIController= fxmlLoader.getController();
        userGUIController.setUsername(username);
        for(int i = 0; i < accountUtils.readFromDatabase().size() ; i++){
            if(accountUtils.readFromDatabase().get(i).getUsername().equals(username)){
                userGUIController.setLabelWelcome(accountUtils.readFromDatabase().get(i).getRole(),username);
            }
        }
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void updateInfo() throws IOException {
        String id = null;
        String role= null;
        String point=null;

            ArrayList<String> userList = accountUtils.readFile();
            ArrayList<String[]> userListValue = new ArrayList<>();
            for (String value : userList) {
                String[] tokens = value.split(",");
                userListValue.add(tokens);
            }
            for (String[] value : userListValue) {
                if (value[1].equals(username)) {
                    id= value[0];
                    role= value[6];
                    point= value[7];
                }
            }
            List<String> listLine = Files.readAllLines(file.toPath());
            for (int i = 0; i < listLine.size(); i++) {
                String[] tokens = listLine.get(i).split(",");
                String username1 = tokens[1];
                if (username1.equals(username)) {
                    listLine.remove(i);
                    listLine.add(id+","+username+","+passwordTextField.getText()+","+full_nameTextField.getText()+","+addressTextField.getText()+","+phoneTextField.getText()+","+role+","+point);
                }
                Files.write(file.toPath(), listLine);
            }
        Alertsup alertsup= new Alertsup();
            alertsup.alertSuccess("You have successfully update!!", "Congrats!!");

    }

    public void setUsername(String username) {
        this.username = username;

    }

    public void compareToUsername() throws IOException {
        try {
            ArrayList<String> userList= accountUtils.readFile();
            ArrayList<String[]> userListValue= new ArrayList<>();
            for(String value : userList){
                String[] tokens= value.split(",");
                userListValue.add(tokens);
            }
            for(String[] value: userListValue){
                if(value[1].equals(username)) {
                    full_nameTextField.setText(value[3]);
                    addressTextField.setText(value[4]);
                    phoneTextField.setText(value[5]);
                    passwordTextField.setText(value[2]);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onSaveButton() throws IOException {
        if(checking.isValidPhoneNumber(phoneTextField.getText()) || phoneTextField.getText().equals("null") || phoneTextField.getText().isEmpty()) {
            updateInfo();
        }
        else{
         Alertsup alertsup = new Alertsup();
         alertsup.alertError("Error", "Your new phone number is not valid");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            compareToUsername();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
