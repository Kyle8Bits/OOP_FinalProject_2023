package com.example.finalproject_oop_2023.controller;

import com.example.finalproject_oop_2023.Items.Item;
import com.example.finalproject_oop_2023.Main;
import com.example.finalproject_oop_2023.Utils.AccountUtils;
import com.example.finalproject_oop_2023.supporter.Alertsup;
import javafx.beans.property.ListPropertyBase;
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
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

//using edit-item.fxml
public class EditItemUIController implements Initializable {

    Alertsup alertsup = new Alertsup();
    Stage stage;
    Scene scene;
    Parent root;
    @FXML
    private Label idTF;
    @FXML
    private TextField itemTitleTF;
    @FXML
    private TextField rentalFeeTF;
    @FXML
    private TextField totalCopyTF;
    @FXML
    private ComboBox<String> loanTypeCB;
    @FXML
    private ComboBox<String> genreCB;
    @FXML
    private ComboBox<String> rentalTypeCB;
    AccountUtils accountUtils = new AccountUtils();
    private final File fileItem = new File("src/main/java/com/example/finalproject_oop_2023/SavingText/items.txt");
    private ObservableList<String> rentalTypeList = FXCollections.observableArrayList("Game","DVD","Record");
    private ObservableList<String> loanTypeList = FXCollections.observableArrayList("1-week","2-day");
    private ObservableList<String> genreList = FXCollections.observableArrayList(  "Action", "Horror", "Drama", "Comedy", "NOT_APPLICABLE");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
     rentalTypeCB.setItems(rentalTypeList);
     loanTypeCB.setItems(loanTypeList);
     genreCB.setItems(genreList);
    }
    public void setInfoOnCB(String s, ObservableList<String> observableList, ComboBox<String> comboBox){
        for (int i = 0; i < observableList.size(); i++){
            if(observableList.get(i).toString().equalsIgnoreCase(s)){
                comboBox.setValue(observableList.get(i));
            }
        }
    }
    public void setInfoOnField(String s) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(fileItem, "rw");
        String line;
        while (((line = randomAccessFile.readLine()) != null)) {
            String[] tokens = line.split(",");
            String idCheck = tokens[0];
            String title = tokens[1];
            String rentalType = tokens[2];
            String loanType = tokens[3];
            String copy = tokens[4];
            String fee = tokens[5];
            String genre = tokens[6];

            if (idCheck.equals(s)) {
                idTF.setText(idCheck);
                itemTitleTF.setText(title);
                totalCopyTF.setText(copy);
                rentalFeeTF.setText(fee);
                setInfoOnCB(rentalType,rentalTypeList,rentalTypeCB);
                setInfoOnCB(loanType,loanTypeList,loanTypeCB);
                setInfoOnCB(genre,genreList,genreCB);
                randomAccessFile.close();
                break;
            }
        }
    }


    @FXML
    public void onSaveButton(ActionEvent event) throws IOException {
        List<String> listLine = accountUtils.getTheList(fileItem);

        if (rentalTypeCB.getValue() == null || loanTypeCB.getValue() == null || genreCB.getValue() == null) {
            alertsup.alertError("Error", "Please select all values!");
        }
        else if (totalCopyTF.getText().matches("[0-9]*") && rentalFeeTF.getText().matches("\\d+(\\.\\d+)?")) {
            for (int i = 0; i < listLine.size(); i++) {
                String[] tokens = listLine.get(i).split(",");
                String idCheck = tokens[0];
                if (idCheck.equals(idTF.getText())) {
                    listLine.set(i, idTF.getText() + "," + itemTitleTF.getText() + "," + rentalTypeCB.getValue() + "," + loanTypeCB.getValue() + "," + totalCopyTF.getText() + "," + rentalFeeTF.getText() + "," + genreCB.getValue());
                }
                Files.write(fileItem.toPath(), listLine);
            }
            alertsup.alertSuccess("Save Successfully", "Your new information's are saved");
        } else{
            alertsup.alertError("Please enter again", "Fee or copy must be integer");
        }
    }
    @FXML
     public void onDelete(ActionEvent event) throws IOException{
         List<String> listLineDelete = accountUtils.getTheList(fileItem);
         for (int i = 0; i < listLineDelete.size(); i++){
             String[] tokens = listLineDelete.get(i).split(",");
             String idCheck = tokens[0];
             if(idCheck.equals(idTF.getText())){
                 listLineDelete.remove(i);
                 Files.write(fileItem.toPath(), listLineDelete);
                 break;
             }
         }

         alertsup.alertSuccess("Confirm Delete", "The item has been delete");

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
