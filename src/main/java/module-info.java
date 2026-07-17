module com.example.malabe_tuktuk {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.malabe_tuktuk to javafx.fxml;
    exports com.example.malabe_tuktuk;
    exports com.example.malabe_tuktuk.controller;
    opens com.example.malabe_tuktuk.controller to javafx.fxml;
    opens com.example.malabe_tuktuk.model to javafx.base;
}