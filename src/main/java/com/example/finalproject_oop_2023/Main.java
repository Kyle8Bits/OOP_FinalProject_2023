package com.example.finalproject_oop_2023;

import com.example.finalproject_oop_2023.Items.Item;
import com.example.finalproject_oop_2023.controller.LoginGUIController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Main extends Application{
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-GUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        Image icon = new Image("file:src/main/resources/Logo/dvd.png");
        stage.getIcons().add(icon);
        stage.show();
}

    public static void main(String[] args) {
        launch();
    }
}