package lk.ijse.gdse.factory_mvc_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class AttendenceController {

    @FXML
    private AnchorPane attendenceAnchorPane;

    @FXML
    private Button buttAdd;

    @FXML
    private Button buttClear;

    @FXML
    private Button buttRemove;

    @FXML
    private Button buttUpdate;

    @FXML
    private Label lbEmlId;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblEXtime;

    @FXML
    private Label lblEntime;

    @FXML
    private Label lblId;

    @FXML
    private TableView<?> tblAttendence;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField txtEnployeeId;

    @FXML
    private TextField txtEntryTime;

    @FXML
    private TextField txtExiteTime;

    @FXML
    private TextField txtId;

    @FXML
    void addOnAction(ActionEvent event) {

    }

    @FXML
    void clearOnActon(ActionEvent event) {

    }

    @FXML
    void removeOnAction(ActionEvent event) {

    }

    @FXML
    void updateOnAction(ActionEvent event) {

    }

}
