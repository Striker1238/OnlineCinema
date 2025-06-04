module com.cinema.onlinecinema {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires jdk.httpserver;
    requires java.net.http;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires java.prefs;

    opens com.cinema.onlinecinema to javafx.fxml;
    exports com.cinema.onlinecinema;
    exports Model;
    opens Model to com.fasterxml.jackson.databind;
    exports com.cinema.onlinecinema.Controller;
    opens com.cinema.onlinecinema.Controller to javafx.fxml;
}