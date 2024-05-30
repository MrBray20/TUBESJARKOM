module com.clientjarkom {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    opens com.clientjarkom to javafx.fxml;
    exports com.clientjarkom;
}
