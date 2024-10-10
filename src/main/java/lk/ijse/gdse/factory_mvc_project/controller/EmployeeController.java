package lk.ijse.gdse.factory_mvc_project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.factory_mvc_project.dto.EmployeeDto;
import lk.ijse.gdse.factory_mvc_project.dto.tm.EmployeeTm;
import lk.ijse.gdse.factory_mvc_project.model.EmployeeModel;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeController {

    private  EmployeeModel EMPLOYEE_MODEL;

    @FXML
    private TableColumn<EmployeeTm, String> addressColomn;

    @FXML
    private TableColumn<EmployeeTm, String> ageColomn;

    @FXML
    private Button buttClear;

    @FXML
    private Button buttDelete;

    @FXML
    private Button buttReport;

    @FXML
    private Button buttSave;

    @FXML
    private Button buttUpdate;

    @FXML
    private Label emLable;

    @FXML
    private AnchorPane employeeAnchorPane;

    @FXML
    private TableColumn<EmployeeTm, String> idColomn;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblAge;

    @FXML
    private Label lblId;

    @FXML
    private Label lblName;

    @FXML
    private Label lblNumber;

    @FXML
    private Label lblSection;

    @FXML
    private Label lblTask;

    @FXML
    private TableColumn<EmployeeTm, String> nameColomn;

    @FXML
    private TableColumn<EmployeeTm, String> sectionColmn;

    @FXML
    private TableColumn<EmployeeTm, String> taskColomn;

    @FXML
    private TableColumn<EmployeeTm, String > phoneColomn;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtAdderess;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtSection;

    @FXML
    private TextField txtTask;


    @FXML
    void clearONAction(ActionEvent event) {

    }

    @FXML
    void deleteOnAction(ActionEvent event) {

    }

    @FXML
    void reportOnAction(ActionEvent event) {

    }

    @FXML
    void saveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {


    }
    @FXML
    void tblEmployeeOnMouseClicked(MouseEvent event) {

    }

    @FXML
    void updateOnAction(ActionEvent event) {

    }
}
