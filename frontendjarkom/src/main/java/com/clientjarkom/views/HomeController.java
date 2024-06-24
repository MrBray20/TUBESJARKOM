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
    }

    @FXML
    void buttonJoinClick(ActionEvent event) {
        refreshListView();
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
        listRooms();
        String json = jsonHelper.commandlistrooms(App.client.getUUID());
        App.auth(json);
        Object obj = App.resAuth();
        if (obj instanceof CommadMessage) {

            CommadMessage commadMessage = (CommadMessage) obj;
            
            for (Map<String,String> entry : commadMessage.getroom().entrySet()){
                System.out.println(entry.getkey());
            }
            
        }
    }

    private void refreshListView() {
        // Clear the existing items
        listView.getItems().clear();

        // Optionally, fetch new data from the server here
        // For demonstration, we'll add some example data
        listView.getItems().addAll(
                new GroupItem("New Group 1"),
                new GroupItem("New Group 2"),
                new GroupItem("New Group 3")
        );

        // Refresh the ListView by calling listRooms()
    }

    private void listRooms(){
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
        listView.getItems().addAll(
                new GroupItem("Group 1"),
                new GroupItem("Group 2"),
                new GroupItem("Group 3")
        );
    }


    private void showRoom(GroupItem item) {
        System.out.println("Showing room: " + item.getGroupName());
        // Add your code here to show the room
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
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/clientjarkom/views/detail_view.fxml"));
    //     Parent root = loader.load();

    //     DetailViewController controller = loader.getController();
    //     controller.setGroupItem(item);

    //     Stage stage = new Stage();
    //     stage.initModality(Modality.APPLICATION_MODAL);
    //     stage.setTitle("Details");
    //     stage.setScene(new Scene(root));
    //     stage.showAndWait();
    }
}
