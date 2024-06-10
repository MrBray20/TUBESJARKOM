package com.clientjarkom;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class HomeController {

    @FXML
    private Button createButton;

    @FXML
    private Button joinButton;

    @FXML
    private ListView<?> listViewGroup;

    @FXML
    private Button logoutButton;

    @FXML
    private TextField massageFild;

    @FXML
    private Button sendButton;

    @FXML
    void buttonCreateClick(ActionEvent event) throws Exception {
        App.showDialog();
    }

    @FXML
    void buttonJoinClick(ActionEvent event) {

    }

    @FXML
    void buttonLogoutClick(ActionEvent event) {

    }

    @FXML
    void buttonSendClick(ActionEvent event) {

    }

}
