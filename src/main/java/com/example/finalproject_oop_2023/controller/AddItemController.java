package com.example.finalproject_oop_2023.controller;

import com.example.finalproject_oop_2023.Utils.ItemUtils;
import com.example.finalproject_oop_2023.Main;
import com.example.finalproject_oop_2023.supporter.Alertsup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddItemController implements Initializable {
    Stage stage;
    Scene scene;
    Parent root;
    ItemUtils itemUtils = new ItemUtils();

    Alertsup alertsup = new Alertsup();

    @FXML
    private TextField publishTextField;

    @FXML
    private TextField itemTitleTextField;

    @FXML
    private ComboBox<String> rentalTypeComboBox;

    @FXML
    private ComboBox<String> loanTypeComboBox;

    @FXML
    private TextField copiesTextField;

    @FXML
    private TextField feeTextField;

    @FXML
    private ComboBox<String> genreComboBox;
    @FXML
    public void cancelAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("admin-dashboard.fxml"));
        root= fxmlLoader.load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void saveItem() throws IOException {
        String publishYear = publishTextField.getText();
        String itemTitle = itemTitleTextField.getText();
        String rentalType = rentalTypeComboBox.getValue();
        String loanType = loanTypeComboBox.getValue();
        String copies = copiesTextField.getText();
        String rentalFee = feeTextField.getText();
        String genre = genreComboBox.getValue();
        System.out.println(rentalType);

        if (rentalType == null || loanType == null || genre == null) {
            alertsup.alertError("Error", "Please select all values!");
        }
        if(copies.matches("[0-9]*") && rentalFee.matches("\\d+(\\.\\d+)?") ){
            int copiesInteger = Integer.parseInt(copies);

            if (!publishYear.matches("\\d{4}")) {
                alertsup.alertError("Error!", "Wrong format for published year! ");
            } else if (!publishYear.isEmpty() && !itemTitle.isEmpty() && !rentalType.isEmpty() && !loanType.isEmpty() && !copies.isEmpty()
                    && !rentalFee.isEmpty() && !genre.isEmpty()) {
                if(rentalType.equalsIgnoreCase("Video Games") && !genre.equalsIgnoreCase("N/A")){
                    alertsup.alertError("Error", "Genre of video games must be N/A");
                }
                else if (!rentalType.equalsIgnoreCase("Video Games")  && genre.equalsIgnoreCase("N/A")){
                    alertsup.alertError("Error", "This item's genre can not be N/A");
                }
                else if (itemUtils.addItem(publishYear, itemTitle, rentalType, loanType, copiesInteger, rentalFee, genre)) {
                    alertsup.alertSuccess("Congrats!", "Item successfully added!");
                } else {
                    alertsup.alertError("Error", "The title of item is already exist");
                }
            } else {
                alertsup.alertError("Error", "Nothing can be blank!");
            }
        } else{
            alertsup.alertError("Please enter again", "Fee or copy must be integer");
        }
     }


    /*--------------------
    -------EDIT COMBOBOX--
    -------------------- */
    ObservableList<String> rentalTypeList = FXCollections.observableArrayList("Old movie records", "DVDs", "Video Games");
    ObservableList<String> loanTypeList = FXCollections.observableArrayList("2 days loan", "1 week loan");
    ObservableList<String> genreTypeList = FXCollections.observableArrayList("Action", "Horror", "Drama", "Comedy", "N/A");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rentalTypeComboBox.setItems(rentalTypeList);
        loanTypeComboBox.setItems(loanTypeList);
        genreComboBox.setItems(genreTypeList);
    }



}
