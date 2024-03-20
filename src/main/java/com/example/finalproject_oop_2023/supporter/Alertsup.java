package com.example.finalproject_oop_2023.supporter;

import javafx.scene.control.Alert;

public class Alertsup {
    public void alertSuccess(String title, String header){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(header);
        alert.showAndWait();
    }

    public void alertError(String title,String header){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(header);
        alert.showAndWait();
    }
}
