package com.usta.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Ventana {
    private String title;
    private String header;
    private String body;
    private AlertType alertType;
    public Ventana(String title, String header, String body, AlertType alertType) {
        this.title = title;
        this.header = header;
        this.body = body;
        this.alertType = alertType;
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(body);
        alert.showAndWait();

    }

    


}
