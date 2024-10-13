module lk.ijse.gdse.factory_mvc_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires lombok;
    requires java.sql;

    opens lk.ijse.gdse.factory_mvc_project.dto.tm to javafx.base;
    opens lk.ijse.gdse.factory_mvc_project.controller to javafx.fxml;
    exports lk.ijse.gdse.factory_mvc_project;
}