package com.clientjarkom.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.clientjarkom.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label inputConfirmasiPassword;

    @FXML
    private TextField inputName;

    @FXML
    private TextField inputPassword;

    @FXML
    private TextField inputUsername;

    @FXML
    private Button registerCancel;

    @FXML
    private Button registerComfir;

    @FXML
    void loginCancelClicked(ActionEvent event) {

    }

    @FXML
    void registerCancelClicked(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

    @FXML
    private void goToLogin() throws IOException {
        App.setRoot("login");
    }

}
