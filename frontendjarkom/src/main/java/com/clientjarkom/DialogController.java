package com.clientjarkom;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DialogController {

    @FXML
    private Button closeButton;
    @FXML
    private Button exitButton;

    @FXML
    private void handleClose() {
        // Get the current stage (window) and close it
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleExitRoom() {
        // Get the current stage (window) and close it
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void showInfo() {
        // Get the current stage (window) and close it
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void showMembers() {
        // Get the current stage (window) and close it
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
