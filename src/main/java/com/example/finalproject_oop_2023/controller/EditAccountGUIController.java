package com.example.finalproject_oop_2023.controller;

import com.example.finalproject_oop_2023.Main;
import com.example.finalproject_oop_2023.Utils.AccountUtils;
import com.example.finalproject_oop_2023.supporter.Alertsup;
import com.example.finalproject_oop_2023.supporter.Checking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;

// Admin modifications
public class EditAccountGUIController implements Initializable {
    Checking checking = new Checking();
    private final File fileAccount = new File("src/main/java/com/example/finalproject_oop_2023/SavingText/Account.txt");
    private final AccountUtils accountUtils = new AccountUtils();
    private final Alertsup alertsup = new Alertsup();
    Stage stage;
    Scene scene;
    Parent root;
    private String idAcc;
    @FXML
    ComboBox<String> typeAccCB;
    @FXML
    private TextField nameAccountTF;
    @FXML
    private TextField addressAccountTF;
    @FXML
    private TextField phoneAccountTF;
    @FXML
    private Label usernameAccountTF;
    @FXML
    private TextField passAccountTF;
    @FXML
    TextField pointTF;
    private ObservableList<String> typeAccList = FXCollections.observableArrayList("Guest","VIP","Regular");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeAccCB.setItems(typeAccList);
    }

    public void setInfoOnCB(String s, ObservableList<String> observableList, ComboBox<String> comboBox){
        for (int i = 0; i < observableList.size(); i++){
            if(observableList.get(i).toString().equalsIgnoreCase(s)){
                comboBox.setValue(observableList.get(i));
            }
        }
    }
    public void setInfoOnField(String s) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(fileAccount, "rw");
        String line;
        while (((line = randomAccessFile.readLine()) != null)) {
            String[] tokens = line.split(",");
            idAcc = tokens[0];
            String username = tokens[1];
            String pass = tokens[2];
            String name = tokens[3];
            String address = tokens[4];
            String phoneNum = tokens[5];
            String typeAcc = tokens[6];
            String point = tokens[7];
            if (idAcc.equals(s)){
                setInfoOnCB(typeAcc,typeAccList,typeAccCB);
                nameAccountTF.setText(name);
                addressAccountTF.setText(address);
                phoneAccountTF.setText(phoneNum);
                usernameAccountTF.setText(username);
                passAccountTF.setText(pass);
                pointTF.setText(point);
                randomAccessFile.close();
                break;
            }
        }
    }
    @FXML
    public void onSave(ActionEvent event) throws IOException {
        List<String> listAccount = accountUtils.getTheList(fileAccount);
        if(checking.isValidPhoneNumber(phoneAccountTF.getText()) || phoneAccountTF.getText().equals("null") || phoneAccountTF.getText().isEmpty()) {
            for (int i = 0; i < listAccount.size(); i++) {
                String[] tokens = listAccount.get(i).split(",");
                if (idAcc.equals(tokens[0])) {
                    listAccount.set(i, idAcc + "," + usernameAccountTF.getText() + "," + passAccountTF.getText() + "," + nameAccountTF.getText() + "," + addressAccountTF.getText() + "," + phoneAccountTF.getText() + "," + typeAccCB.getValue() + "," + pointTF.getText());
                }
                Files.write(fileAccount.toPath(), listAccount);
            }
            alertsup.alertSuccess("Save Successfully", "The account has been saved");
        }
        else {
            alertsup.alertError("Error", "Phone number is not valid");
        }
    }
    @FXML
    public void onDelete(ActionEvent event) throws IOException{
        List<String> listAccountDelete = accountUtils.getTheList(fileAccount);
        for (int i = 0; i < listAccountDelete.size();i++){
            String[] tokens = listAccountDelete.get(i).split(",");
            if(idAcc.equals(tokens[0])){
                listAccountDelete.remove(i);
            }
            Files.write(fileAccount.toPath(),listAccountDelete);
        }
        alertsup.alertSuccess("Confirm Delete", "The account has been delete");

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("admin-dashboard.fxml"));
        root= fxmlLoader.load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onCancel(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("admin-dashboard.fxml"));
        root= fxmlLoader.load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
