module com.example.bella_italia {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    //opens com.example.bella_italia to javafx.fxml;
    //exports com.example.bella_italia;
    exports com.example.bella_italia.app;
    opens com.example.bella_italia.app to javafx.fxml;
    exports com.example.bella_italia.controllers;
    opens com.example.bella_italia.controllers to javafx.fxml;
}