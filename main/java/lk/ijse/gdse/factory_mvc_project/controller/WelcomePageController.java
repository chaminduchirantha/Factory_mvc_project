package lk.ijse.gdse.factory_mvc_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class WelcomePageController {

    @FXML
    private Label descNameLbl;

    @FXML
    private Label lblLogin;

    @FXML
    private Button loginBut;

    @FXML
    private AnchorPane welcomeAnchorPane;

    @FXML
    void loginOnAction(ActionEvent actionEvent){
        try {
            welcomeAnchorPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/View/LoginView.fxml"));
            welcomeAnchorPane.getChildren().add(load);

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Welcome Page not found").show();

        }
    }
}
