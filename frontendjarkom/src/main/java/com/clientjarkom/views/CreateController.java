package com.clientjarkom.views;

import java.net.URL;
import java.util.ResourceBundle;

import com.clientjarkom.App;
import com.clientjarkom.util.jsonHelper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateController implements Initializable{

    @FXML
    private Button btnCancelCreate;

    @FXML
    private Button btnCreate;

    @FXML
    private TextField inputCreate;

    @FXML
    private Alert alert;

    @FXML
    void onCreate(ActionEvent event) throws Exception {
        if (event.getSource() == btnCancelCreate){
            Stage stage = (Stage) btnCancelCreate.getScene().getWindow();
            stage.close();

        }else if (event.getSource() == btnCreate){
            if(!inputCreate.getText().isEmpty()){
                String json = jsonHelper.commandCreate(inputCreate.getText());
                App.createRoom(json);
                App.resAuth();
                Stage stage = (Stage) btnCreate.getScene().getWindow();
                stage.close();
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Plese fill all blank fields");
                alert.showAndWait();
            }
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        
    }

}
