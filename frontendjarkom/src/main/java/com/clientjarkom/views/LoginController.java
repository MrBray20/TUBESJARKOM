package com.clientjarkom.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.clientjarkom.App;
import com.clientjarkom.model.AuthResponseMessage;
import com.clientjarkom.model.AuthResponseMessage.AuthResponseData;
import com.clientjarkom.util.jsonHelper;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class LoginController implements Initializable{

    @FXML
    private Button AlreadyAccount;

    @FXML
    private Button CreateAccount;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private TextField inputUsername;

    @FXML
    private Button loginButton;

    @FXML
    private AnchorPane paneLogin;

    @FXML
    private AnchorPane paneRegister;

    @FXML
    private AnchorPane paneSlide;

    @FXML
    private Button registerButton;

    @FXML
    private PasswordField registerConfirmPassword;

    @FXML
    private TextField registerName;

    @FXML
    private PasswordField registerPassword;

    @FXML
    private TextField registerUsername;

    @FXML
    private Alert alert;

    @FXML
    void loginButtonClicked(ActionEvent event) throws IOException {
        if(inputPassword.getText().isEmpty() || inputPassword.getText().isEmpty()){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Plese fill all blank fields");
            alert.showAndWait();
        } else{
            String username = inputUsername.getText();
            String password = inputPassword.getText();
            String json = jsonHelper.jsonLogin(username, password);
            App.auth(json);
            Object data = App.resAuth();
            if(data instanceof AuthResponseMessage.AuthResponseData){
                AuthResponseData dataparsing = (AuthResponseData) data;
                App.userLogin(dataparsing.getUsername(), dataparsing.getSessionToken());
                App.setRoot("home");
            }else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Username or password wrong");
                alert.showAndWait();
            }
            
        }
        
    }



    @FXML
    void registerButtonClicked(ActionEvent event) {
        if(registerName.getText().isEmpty() || registerUsername.getText().isEmpty() || registerPassword.getText().isEmpty() 
        || registerConfirmPassword.getText().isEmpty()){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Plese fill all blank fields");
            alert.showAndWait();
        } else{
            String name = registerName.getText();
            String username = registerUsername.getText();
            String password = registerPassword.getText();
            String confirmPassword = registerConfirmPassword.getText();
            String json = jsonHelper.jsonRegister(name, username, password, confirmPassword);
            App.auth(json);
            if(((String) App.resAuth()).equalsIgnoreCase("succes")){
                switchForm(event);
            };
        }
    }

    @FXML
    void switchForm(ActionEvent event){
        TranslateTransition slider = new TranslateTransition();
        if(event.getSource() == CreateAccount){
            slider.setNode(paneSlide);
            slider.setToX(440);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e)->{
                AlreadyAccount.setVisible(true);
                CreateAccount.setVisible(false);
            });
            
            slider.play();
        } else if(event.getSource() == AlreadyAccount){
            slider.setNode(paneSlide);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e)->{
                AlreadyAccount.setVisible(false);
                CreateAccount.setVisible(true);
            });
            
            slider.play();
        } else if(event.getSource() == registerButton){
            registerName.setText("");
            registerUsername.setText("");
            registerPassword.setText("");
            registerConfirmPassword.setText("");
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Succes Register");
            alert.setHeaderText(null);
            alert.setContentText("Succes Register");
            alert.showAndWait();
        }
    }



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub

    }

}
