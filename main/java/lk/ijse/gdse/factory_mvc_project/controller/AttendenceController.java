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
import lk.ijse.gdse.factory_mvc_project.dto.AttendenceDto;
import lk.ijse.gdse.factory_mvc_project.dto.tm.AttendenceTm;
import lk.ijse.gdse.factory_mvc_project.dto.tm.SalaryTm;
import lk.ijse.gdse.factory_mvc_project.model.AttendenceModel;
import lk.ijse.gdse.factory_mvc_project.model.EmployeeModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AttendenceController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colomnId.setCellValueFactory(new PropertyValueFactory<>("attendenceId"));
        colomnAttendenceDate.setCellValueFactory(new PropertyValueFactory<>("attendenceDate"));
        colomnEntryTmie.setCellValueFactory(new PropertyValueFactory<>("entryTime"));
        colomnExiteTime.setCellValueFactory(new PropertyValueFactory<>("exitTime"));
        colomnEmployeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));

        try {
            loadNextAttendanceId();
            loadNextEmployeeId();
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load Attendance ID");

        }
    }

    EmployeeModel employeeModel = new EmployeeModel();

    private void loadNextAttendanceId() throws SQLException, ClassNotFoundException {
        String nextAttendanceId = attendenceModel.getNextAttendenceId();
        txtId.setText(nextAttendanceId);
    }

    private void loadNextEmployeeId() throws SQLException, ClassNotFoundException {
        String nextEmployeeId =employeeModel.getNextEmployeeId();
        txtEnployeeId.setText(nextEmployeeId);
    }


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
    private TableView<AttendenceTm> tblAttendence;

    @FXML
    private TableColumn<AttendenceTm, String> colomnAttendenceDate;

    @FXML
    private TableColumn<AttendenceTm, String> colomnEmployeId;

    @FXML
    private TableColumn<AttendenceTm, String> colomnEntryTmie;

    @FXML
    private TableColumn<AttendenceTm, String> colomnExiteTime;

    @FXML
    private TableColumn<AttendenceTm, String> colomnId;

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
    void addOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String AttendenceId = txtId.getText();
        String entryTime = txtEntryTime.getText();
        String exitTime = txtExiteTime.getText();
        LocalDate attendenceDate = datePicker.getValue();
        String employeeId = txtEnployeeId.getText();

        AttendenceModel attendenceModel = new AttendenceModel();

        AttendenceDto attendenceDto = new AttendenceDto(AttendenceId, entryTime, exitTime, attendenceDate, employeeId);

        boolean isSaved = attendenceModel.saveAttendence(attendenceDto);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Successfully saved the Attendece").show();
            loadTableData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to save the Attendence").show();
        }
    }

    AttendenceModel attendenceModel = new AttendenceModel();
    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<AttendenceDto> attendenceDtos = attendenceModel.getAllAttendence();

        ObservableList<AttendenceTm> attendenceTms = FXCollections.observableArrayList();

        for (AttendenceDto attendenceDto : attendenceDtos) {
            AttendenceTm attendenceTm = new AttendenceTm(
                    attendenceDto.getAttendenceId(),
                    attendenceDto.getEntryTime(),
                    attendenceDto.getExitTime(),
                    attendenceDto.getAttendenceDate(),
                    attendenceDto.getEmployeeId()

            );
            attendenceTms.add(attendenceTm);
        }
        tblAttendence.setItems(attendenceTms);
    }


    private void refreshPage() throws SQLException, ClassNotFoundException {

        loadNextAttendanceId();
        loadTableData();

        buttAdd.setDisable(false);

        buttUpdate.setDisable(true);
        buttRemove.setDisable(true);

        txtEntryTime.setText("");
        txtExiteTime.setText("");
        datePicker.setTooltip(new Tooltip("Date"));
        txtEnployeeId.setText("");

    }

    @FXML
    void clearOnActon(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();

    }

    @FXML
    void removeOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String attendanceId = txtId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION , "Are you sure you want to delete this Attendance?" , ButtonType.YES , ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = attendenceModel.deleteAttendence(attendanceId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION , "Attendance deleted").show();
                loadTableData();

            }else{
                new Alert(Alert.AlertType.ERROR , "Attendance not deleted").show();
            }
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String AttendenceId = txtId.getText();
        String entryTime = txtEntryTime.getText();
        String exitTime = txtExiteTime.getText();
        LocalDate attendenceDate = datePicker.getValue();
        String employeeId = txtEnployeeId.getText();

        AttendenceDto attendenceDto = new AttendenceDto(AttendenceId, entryTime, exitTime, attendenceDate, employeeId);

        boolean isUpdate = attendenceModel.deleteAttendence(String.valueOf(attendenceDto));
        if (isUpdate) {
           refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Successfully Update the Attendance").show();
            loadTableData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to update the Attendance").show();
        }
    }
    @FXML
    void tblOnMouseClick(MouseEvent event) throws SQLException, ClassNotFoundException {
        AttendenceTm attendenceTm = tblAttendence.getSelectionModel().getSelectedItem();
        if (attendenceTm != null) {
            txtId.setText(attendenceTm.getAttendenceId());
            txtEntryTime.setText(attendenceTm.getEntryTime());
            txtExiteTime.setText(attendenceTm.getExitTime());
            datePicker.setValue(attendenceTm.getAttendenceDate());
            txtEnployeeId.setText(attendenceTm.getEmployeeId());

            buttAdd.setDisable(true);
            buttRemove.setDisable(false);
            buttUpdate.setDisable(false);
            loadTableData();
        }
    }
}

