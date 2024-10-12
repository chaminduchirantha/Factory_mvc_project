package lk.ijse.gdse.factory_mvc_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class PaymentController {

    @FXML
    private AnchorPane PaymentAnchorPane;

    @FXML
    private Button buttClear;

    @FXML
    private Button buttDelete;

    @FXML
    private Button buttSave;

    @FXML
    private Button buttUpdate;

    @FXML
    private Label lbPaymentDate;

    @FXML
    private Label lblDilivaryId;

    @FXML
    private Label lblDiscount;

    @FXML
    private Label lblId;

    @FXML
    private Label lblPrice;

    @FXML
    private DatePicker paymentDate;

    @FXML
    private TableView<?> tblPayment;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDilivaryId;

    @FXML
    private TextField txtDiscount;

    @FXML
    private TextField txtPrice;

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
    void updateOnAction(ActionEvent event) {

    }

}
