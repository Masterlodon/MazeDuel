module gui.mazeduelclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens gui.mazeduelclient to javafx.fxml;
    exports gui.mazeduelclient;
}