package com.clientjarkom.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.clientjarkom.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField inputPassword;

    @FXML
    private TextField inputUsername;

    @FXML
    private Button loginButton;

    @FXML
    void loginButtonClicked(ActionEvent event) {

    }

    @FXML
    private void goToRegister() throws IOException {
        App.setRoot("register");
    }

}
