package com.clientjarkom;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PrimaryController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void loginButton() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        Client.login(username, password);
    }
    @FXML
    private void goToRegister() throws IOException {
        App.setRoot("secondary");
    }
}
