module com.clientjarkom {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.clientjarkom to javafx.fxml;
    exports com.clientjarkom;
}
