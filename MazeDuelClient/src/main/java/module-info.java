module gui.mazeduelclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens gui.mazeduelclient to javafx.fxml;
    exports gui.mazeduelclient;
}