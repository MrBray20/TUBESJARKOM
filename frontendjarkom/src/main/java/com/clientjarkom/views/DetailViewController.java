package com.clientjarkom.views;

import com.clientjarkom.model.GroupItem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DetailViewController {

    @FXML
    private Label groupNameLabel;

    public void setGroupItem(GroupItem item) {
        groupNameLabel.setText(item.getGroupName());
    }

    @FXML
    private void handleCancel() {
        groupNameLabel.getScene().getWindow().hide();
    }
}