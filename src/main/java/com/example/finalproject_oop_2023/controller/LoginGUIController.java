package com.example.finalproject_oop_2023.controller;

import com.example.finalproject_oop_2023.CreatAccount.Authentication;
import com.example.finalproject_oop_2023.Main;
import com.example.finalproject_oop_2023.Utils.AccountUtils;
import com.example.finalproject_oop_2023.supporter.Alertsup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.*;

public class LoginGUIController {

    private final String adminUsername= "admin";
    private final String adminPassword= "admin";

    Stage stage;
    Scene scene;
    Parent root;
    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordTextField;

    private Authentication authentication= new Authentication();
    private Alertsup alertsup= new Alertsup();

    @FXML
    protected void onCancelButton(){
        userNameTextField.setText("");
        passwordTextField.setText("");
    }

    @FXML
    protected void onRegisButton(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("regis-account.fxml"));
        root= fxmlLoader.load();
        scene = new Scene(root);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onLoginButtonPress(ActionEvent event) throws Exception{
        AccountUtils accountUtils = new AccountUtils();
        authentication.createAccountFolder();
        authentication.readFile();
        // admin login
        if(authentication.loginAccount(userNameTextField.getText().trim(),passwordTextField.getText())){
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("user-dashboard.fxml"));
            root= fxmlLoader.load();
            UserGUIController userGUIController= fxmlLoader.getController();
            userGUIController.setUsername(userNameTextField.getText());

            for(int i = 0; i < accountUtils.readFromDatabase().size() ; i++){
                if(accountUtils.readFromDatabase().get(i).getUsername().equals(userNameTextField.getText()) && !accountUtils.readFromDatabase().get(i).getRole().equals("VIP")){
                    userGUIController.setLabelWelcome(accountUtils.readFromDatabase().get(i).getRole().toString(),userNameTextField.getText());
                }else if(accountUtils.readFromDatabase().get(i).getUsername().equals(userNameTextField.getText()) && accountUtils.readFromDatabase().get(i).getRole().equals("VIP")){
                    userGUIController.setLabelWelcome(accountUtils.readFromDatabase().get(i).getRole(),userNameTextField.getText(),accountUtils.readFromDatabase().get(i).getPoint());
                }
            }

            scene = new Scene(root);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if(adminUsername.equals(userNameTextField.getText())&&adminPassword.equals(passwordTextField.getText())){
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("admin-dashboard.fxml"));
            root= fxmlLoader.load();
            scene = new Scene(root);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }else{
            alertsup.alertError("Login Failed", "Wrong username or password");
        }
    }

    @FXML
    public void onLoginButton(KeyEvent event) throws IOException {
        AccountUtils accountUtils = new AccountUtils();

        if (event.getCode() == KeyCode.ENTER){
            authentication.createAccountFolder();
            authentication.readFile();
            // admin login
            if(authentication.loginAccount(userNameTextField.getText().trim(),passwordTextField.getText())){
                System.out.println( authentication.getCustomerProfile());
                //login
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("user-dashboard.fxml"));
                root= fxmlLoader.load();

                UserGUIController userGUIController= fxmlLoader.getController();

                for(int i = 0; i < accountUtils.readFromDatabase().size() ; i++){
                    if(accountUtils.readFromDatabase().get(i).getUsername().equals(userNameTextField.getText()) && !accountUtils.readFromDatabase().get(i).getRole().equals("VIP")){
                        userGUIController.setLabelWelcome(accountUtils.readFromDatabase().get(i).getRole().toString(),userNameTextField.getText());
                    }else if(accountUtils.readFromDatabase().get(i).getUsername().equals(userNameTextField.getText()) && accountUtils.readFromDatabase().get(i).getRole().equals("VIP")){
                        userGUIController.setLabelWelcome(accountUtils.readFromDatabase().get(i).getRole(),userNameTextField.getText(),accountUtils.readFromDatabase().get(i).getPoint());
                    }
                }

                userGUIController.setUsername(userNameTextField.getText());
                scene = new Scene(root);
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else if(adminUsername.equals(userNameTextField.getText()) && adminPassword.equals(passwordTextField.getText())){
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("admin-dashboard.fxml"));
                root= fxmlLoader.load();
                scene = new Scene(root);
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }else{
                alertsup.alertError("Login Failed", "Wrong username or password");
            }
        }
    }


}
