package com.example.finalproject_oop_2023.controller;

import com.example.finalproject_oop_2023.CreatAccount.Authentication;
import com.example.finalproject_oop_2023.Main;
import com.example.finalproject_oop_2023.supporter.Alertsup;
import com.example.finalproject_oop_2023.supporter.Checking;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    Stage stage;
    Scene scene;
    Parent root;
    Checking checking = new Checking();
    private Alertsup alertsup= new Alertsup();
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldAddress;
    @FXML
    private TextField textFieldPhone;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField textFieldPass;

    Authentication authentication= new Authentication();
    @FXML
    protected void onReturnButton(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-GUI.fxml"));
        root= fxmlLoader.load();
        scene = new Scene(root);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onSignup(ActionEvent e) throws IOException {

        String name= textFieldName.getText();
        String address= textFieldAddress.getText();
        String phone= textFieldPhone.getText();
        String username= textFieldUsername.getText().trim();
        String pass= textFieldPass.getText();

        if(textFieldUsername.getText().equals("Admin") || textFieldUsername.getText().equals("admin")){
            alertsup.alertError("Error!","Username cannot be admin");
            System.out.println("Cannot admin");
        }else if(textFieldPass.getText().isEmpty() || textFieldUsername.getText().isEmpty()) {
            alertsup.alertError("Error!", "Username/Password cannot be empty");
        }
        else{
            if((name.isEmpty() && address.isEmpty() && phone.isEmpty()) && (username.length()  !=0 || pass.length()!=0)){
            if( authentication.addAccount(username,pass)){
                alertsup.alertSuccess("Congrats!","You have successfully sign up");
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-GUI.fxml"));
                root= fxmlLoader.load();
                scene = new Scene(root);
                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }else{
                alertsup.alertError("Error", "Username already exist");
            }

        }
            else if(username.length()  == 0 || pass.length()== 0){
            alertsup.alertError("Error","Username/Password cannot be empty");
        } else {
                if (checking.isValidPhoneNumber(phone)) {
                    if (authentication.addAccount(username, pass, name, address, phone)) {
                        alertsup.alertSuccess("Congrats!", "You have successfully sign up");
                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-GUI.fxml"));
                        root = fxmlLoader.load();
                        scene = new Scene(root);
                        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        alertsup.alertError("Error!", "Username already exist");
                    }
                }
                else {
                    alertsup.alertError("Error", "Your phone number is invalid");
                }
              }
        }
    }
}
