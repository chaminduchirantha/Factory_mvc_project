package lk.ijse.gdse.factory_mvc_project.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.io.IOException;

public class LoginViewController {

    @FXML
    private AnchorPane LoginAnchorPane;

    @FXML
    private Button loginBut;

    @FXML
    private TextField txName;

    @FXML
    private TextField txtPassword;

    @FXML
    void loginOnAction(ActionEvent event) throws IOException {
        String userName = txName.getText();
        String password = txtPassword.getText();

        if (userName.equals("cha1234") || password.equals("cha1234")) {
            LoginAnchorPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/View/HomePage.fxml"));
            LoginAnchorPane.getChildren().add(load);
        }else {
            JOptionPane.showMessageDialog(null, "User name or PassWord is wrong ");
        }
    }
}
