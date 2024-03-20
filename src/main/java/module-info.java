module com.example.finalproject_oop_2023 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.finalproject_oop_2023 to javafx.fxml;
    exports com.example.finalproject_oop_2023;
    exports com.example.finalproject_oop_2023.controller;
    opens com.example.finalproject_oop_2023.controller to javafx.fxml;
    opens com.example.finalproject_oop_2023.Items to javafx.base;
    opens com.example.finalproject_oop_2023.User to javafx.base;
    opens com.example.finalproject_oop_2023.Rental to javafx.base;
    opens com.example.finalproject_oop_2023.Utils to javafx.base;
}