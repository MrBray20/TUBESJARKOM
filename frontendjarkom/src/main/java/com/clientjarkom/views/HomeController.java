package com.clientjarkom.views;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.clientjarkom.App;
import com.clientjarkom.model.CommadMessage;
import com.clientjarkom.model.GroupItem;
import com.clientjarkom.util.jsonHelper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class HomeController implements Initializable{

    @FXML
    private Button createButton;

    @FXML
    private Button joinButton;

    @FXML
    private ListView<GroupItem> listView;

    @FXML
    private Button logoutButton;

    @FXML
    private TextField massageFild;

    @FXML
    private Button sendButton;

    @FXML
    void buttonCreateClick(ActionEvent event) throws Exception {
        App.createDialog();
        refreshListView(updateListRoom());
    }

    @FXML
    void buttonJoinClick(ActionEvent event) {

    }

    @FXML
    void buttonLogoutClick(ActionEvent event) throws IOException {
        App.userClose();
        App.setRoot("login");
    }

    @FXML
    void buttonSendClick(ActionEvent event) {
        
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        listRooms(updateListRoom());
    }

    public Map<String,String> updateListRoom(){
        String json = jsonHelper.commandlistrooms(App.client.getUUID());
        App.auth(json);
        CommadMessage commadMessage;
        Object obj = App.resAuth();
        if (obj instanceof CommadMessage) {
            commadMessage = (CommadMessage) obj;
            return commadMessage.getRoom();
        }
        return null;
    }

    private void refreshListView(Map<String,String> room) {
        // Clear the existing items
        listView.getItems().clear();

        // Optionally, fetch new data from the server here
        // For demonstration, we'll add some example data
        for (Map.Entry<String,String> Entry : room.entrySet()){
            System.out.println(Entry.getKey());
            String key = Entry.getKey();
            String value = Entry.getValue();

            GroupItem groupItem = new GroupItem(value,key);

            listView.getItems().addAll(groupItem);
        }

        // Refresh the ListView by calling listRooms()
    }

    private void listRooms(Map<String,String> room){
        listView.setCellFactory(new Callback<ListView<GroupItem>, ListCell<GroupItem>>() {
            @Override
            public ListCell<GroupItem> call(ListView<GroupItem> param) {
                return new ListCell<GroupItem>() {
                    private HBox content;
                    private Text groupName;
                    private Button detailButton;
                    private Button roomButton;

                    {
                        groupName = new Text();
                        detailButton = new Button("Details");
                        roomButton = new Button("Room");
                        content = new HBox(groupName, roomButton, detailButton);

                        detailButton.setOnAction(event -> {
                            GroupItem item = getItem();
                            if (item != null) {
                                try {
                                    showDetails(item);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            event.consume(); // Prevent the ListView's click event from being triggered
                        });

                        roomButton.setOnAction(event -> {
                            GroupItem item = getItem();
                            if (item != null) {
                                showRoom(item);
                            }
                            event.consume(); // Prevent the ListView's click event from being triggered
                        });

                        content.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                            if (event.getTarget() == detailButton || event.getTarget() == roomButton) {
                                event.consume();
                            }
                        });
                    }

                    @Override
                    protected void updateItem(GroupItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            groupName.setText(item.getGroupName());
                            setGraphic(content);
                        }
                    }
                };
            }
        });

        // Example data

        for (Map.Entry<String,String> Entry : room.entrySet()){
            System.out.println(Entry.getKey());
            String key = Entry.getKey();
            String value = Entry.getValue();

            GroupItem groupItem = new GroupItem(value,key);

            listView.getItems().addAll(groupItem);
        }
        
    }


    private void showRoom(GroupItem item) {
        System.out.println("Showing room: " + item.getGroupName() +"id : " + item.getidroom());
        String json = jsonHelper.commandJoin(item.getidroom());
        App.auth(json);

    }

    private void showDetails(GroupItem item) throws IOException {
        System.out.println("Showing details for: " + item.getGroupName());
        // Add your code here to show details in a new window or dialog

        try {
            App.showDetails();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
