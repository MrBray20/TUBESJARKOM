package com.clientjarkom;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SecondaryController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField nameuserField;

    @FXML
    private void registerButton() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String name = nameuserField.getText();

        Client.register(username, password, name);
    }
    @FXML
    private void goToLogin() throws IOException {
        App.setRoot("primary");
    }
}