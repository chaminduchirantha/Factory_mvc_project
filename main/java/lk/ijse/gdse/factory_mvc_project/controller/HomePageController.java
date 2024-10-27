package lk.ijse.gdse.factory_mvc_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.io.IOException;

public class HomePageController {

    @FXML
    private Button ButtLogOut;


    @FXML
    private Button ButtPayment;

    @FXML
    private Button ButtWorkSheet;

    @FXML
    private Pane OptionPane;

    @FXML
    private Button buttAtten;

    @FXML
    private Button buttBranch;

    @FXML
    private Button buttDililvary;

    @FXML
    private Button buttMachine;

    @FXML
    private Button buttProductDetail;

    @FXML
    private Button buttProduct;

    @FXML
    private Button buttSalary;

    @FXML
    private Button buttStock;

    @FXML
    private Button buttSupplier;

    @FXML
    private Button buutEmployee;

    @FXML
    private AnchorPane homePageAnchorPane;

    @FXML
    private Label lblHomePage;

    @FXML
    private AnchorPane loadingAnchorPane;

    @FXML
    void LogOutOnAction(ActionEvent event) {

    }

    @FXML
    void DilivaryOnAction(ActionEvent event) throws IOException {
        navigateTo("/View/DilivaryView.fxml");
    }

    @FXML
    void attendenceOnAction(ActionEvent event) throws IOException {
        navigateTo("/View/AttendenceView.fxml");
    }

    @FXML
    void branchOnAction(ActionEvent event) throws IOException {
        navigateTo("/View/BranchView.fxml");
    }

    @FXML
    void employeeOnAction(ActionEvent event) throws IOException {
        navigateTo("/View/EmployeeView.fxml");
    }

    @FXML
    void machineOnAction(ActionEvent event) throws IOException {
        navigateTo("/View/MachinesView.fxml");
    }

    @FXML
    void paymentOnAction(ActionEvent event) throws IOException {
        navigateTo("/View/PaymentView.fxml");
    }

    @FXML
    void productOnAction(ActionEvent event) throws IOException {
        navigateTo("/View/ProductView.fxml");
    }

    @FXML
    void productDetailOnAction(ActionEvent event) {

    }

    @FXML
    void salaryOnAction(ActionEvent event) throws IOException {
        navigateTo("/View/SalaryView.fxml");

    }

    @FXML
    void stockOnAction(ActionEvent event) throws IOException {
        navigateTo("/View/StockView.fxml");

    }

    @FXML
    void supplierOnAction(ActionEvent event) throws IOException {
        navigateTo("/View/SupplierView.fxml");

    }

    @FXML
    void worksheetOnAction(ActionEvent event) throws IOException {
        navigateTo("/View/Worksheet.fxml");

    }

    private void navigateTo(String fxmlPath) {
        try {
            loadingAnchorPane.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane loadedPane = loader.load();
            loadingAnchorPane.getChildren().add(loadedPane);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load view: " + fxmlPath);
        }
        System.out.println("Loading FXML: " + fxmlPath);
    }
}

