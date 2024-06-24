package com.clientjarkom;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import com.clientjarkom.model.Client;
import com.clientjarkom.model.ClientChat;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static ClientChat clientChat;
    public static Client client;

    @Override
    public void start(Stage stage) throws IOException {
        clientChat = new ClientChat();
        client = new Client();
        scene = new Scene(loadFXML("login"));

        stage.setScene(scene);

        stage.setTitle("Multi Chat Rooms");
        stage.setMinHeight(695);
        stage.setMinWidth(1072);
        stage.show();

    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }


    public static void userLogin(String username,String session){
        client.setClient(username,session);
    }

    public static void userClose(){
        client.setClient("", "");;
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/clientjarkom/views/"+fxml+ ".fxml"));
        
        return fxmlLoader.load();
    }

    public static void auth(String json){
        try {
            clientChat.handlerAuth(json);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    

    public static void joinRoom(){
        
    }

    public static void command(String json){
        try {
            clientChat.handlerCommand(json);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static Object resAuth(){
        try {
            return clientChat.handlerResAuth();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }


    public static void showDetails() throws Exception {

        Scene scene = new Scene(loadFXML("dialog"));

        Stage stage = new Stage();
        stage.setTitle("Room details");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setWidth(600);
        stage.setHeight(300);

        stage.showAndWait();
    }

    public static void createDialog() throws Exception{
        Scene scene = new Scene(loadFXML("join"));

        Stage stage = new Stage();
        stage.setTitle("Create room");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);

        stage.showAndWait();
    }


    public static void createRoom(String json) throws Exception{
        clientChat.handlerCommand(json);
    }

    public static void main(String[] args) {
        launch();
    }

}