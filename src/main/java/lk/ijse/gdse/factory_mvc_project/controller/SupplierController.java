package lk.ijse.gdse.factory_mvc_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class SupplierController {

    @FXML
    private TableColumn<?, ?> addressColomn;

    @FXML
    private TableColumn<?, ?> ageColomn;

    @FXML
    private Button buttClear;

    @FXML
    private Button buttDelete;

    @FXML
    private Button buttSave;

    @FXML
    private Button buttUpdate;

    @FXML
    private Label emLable;

    @FXML
    private TableColumn<?, ?> idColomn;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblAge;

    @FXML
    private Label lblId;

    @FXML
    private Label lblName;

    @FXML
    private Label lblNicNumber;

    @FXML
    private Label lblNumber;

    @FXML
    private TableColumn<?, ?> nameColomn;

    @FXML
    private TableColumn<?, ?> sectionColmn;

    @FXML
    private AnchorPane supplierAnchorPane;

    @FXML
    private TableColumn<?, ?> taskColomn1;

    @FXML
    private TableView<?> tblEmployee;

    @FXML
    private TextField txtAdderess;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtPhone;

    @FXML
    void clearONAction(ActionEvent event) {

    }

    @FXML
    void deleteOnAction(ActionEvent event) {

    }

    @FXML
    void saveOnAction(ActionEvent event) {

    }

    @FXML
    void tblEmployeeOnMouseClicked(MouseEvent event) {

    }

    @FXML
    void updateOnAction(ActionEvent event) {

    }

}
