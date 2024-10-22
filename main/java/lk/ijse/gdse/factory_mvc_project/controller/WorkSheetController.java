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
import lk.ijse.gdse.factory_mvc_project.dto.WorkSheetSheduleDto;
import lk.ijse.gdse.factory_mvc_project.dto.tm.EmployeeTm;
import lk.ijse.gdse.factory_mvc_project.dto.tm.WorkSheetSheduleTm;
import lk.ijse.gdse.factory_mvc_project.model.WorkSheetSheduleModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class WorkSheetController implements Initializable {

    @FXML
    private Button buttAdd;

    @FXML
    private Button buttClear;

    @FXML
    private Button buttRemove;

    @FXML
    private Button buttUpdate;

    @FXML
    private TableView<WorkSheetSheduleTm> tblWorksheet;

    @FXML
    private TableColumn<WorkSheetSheduleTm, String> colomnEmId;

    @FXML
    private TableColumn<WorkSheetSheduleTm, String> colomnEndTime;

    @FXML
    private TableColumn<WorkSheetSheduleTm, String> colomnId;

    @FXML
    private TableColumn<WorkSheetSheduleTm, String> colomnStartTime;

    @FXML
    private TableColumn<WorkSheetSheduleTm, String> colomnTask;

    @FXML
    private TextField txtEmId;

    @FXML
    private TextField txtEndTime;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtSatartTime;

    @FXML
    private TextField txtTask;

    @FXML
    private AnchorPane workShhetAnchorPane;

    @FXML
    void addOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String workSheetId = txtId.getText();
        String workStartTime = txtSatartTime.getText();
        String workEndTime = txtEndTime.getText();
        String workTaskName = txtTask.getText();
        String employeeId = txtEmId.getText();


        WorkSheetSheduleDto workSheetSheduleDto = new WorkSheetSheduleDto(workSheetId, workStartTime,workEndTime, workTaskName, employeeId);

        boolean isSaved = workSheetSheduleModel.saveShedule(workSheetSheduleDto);
        if (isSaved) {
            //refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Successfully saved the Schedule").show();
//            loadTableData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to save the Schedule").show();
        }

    }

    @FXML
    void tblMouseOnAction(MouseEvent event) throws SQLException, ClassNotFoundException {
        WorkSheetSheduleTm workSheetSheduleTm = tblWorksheet.getSelectionModel().getSelectedItem();
        if (workSheetSheduleTm != null) {
            txtId.setText(workSheetSheduleTm.getWorkSheetId());
            txtSatartTime.setText(workSheetSheduleTm.getWorkStartTime());
            txtEndTime.setText(workSheetSheduleTm.getWorkEndTime());
            txtTask.setText(workSheetSheduleTm.getTaskName());
            txtEmId.setText(workSheetSheduleTm.getEmployeeId());

            buttAdd.setDisable(true);

            buttRemove.setDisable(false);
            buttUpdate.setDisable(false);
            loadTableData();
        }
    }

    @FXML
    void clearOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void removeOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String workSheetId = txtId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION , "Are you sure you want to delete this workSheetShedule?" , ButtonType.YES , ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = workSheetSheduleModel.deleteWorkSheetShedule(workSheetId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION , "Employee deleted").show();

            }else{
                new Alert(Alert.AlertType.ERROR , "Employee not deleted").show();
            }
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String workSheetId = txtId.getText();
        String workStartTime = txtSatartTime.getText();
        String workEndTime = txtEndTime.getText();
        String workTaskName = txtTask.getText();
        String employeeId = txtEmId.getText();


        WorkSheetSheduleDto workSheetSheduleDto = new WorkSheetSheduleDto(workSheetId, workStartTime,workEndTime, workTaskName, employeeId);

        boolean isSaved = workSheetSheduleModel.updateShedule(workSheetSheduleDto);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Successfully updated the Schedule").show();
//            loadTableData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to updated the Schedule").show();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colomnId.setCellValueFactory(new PropertyValueFactory<WorkSheetSheduleTm, String>("workSheetId"));
        colomnStartTime.setCellValueFactory(new PropertyValueFactory<WorkSheetSheduleTm , String>("workStartTime"));
        colomnEndTime.setCellValueFactory(new PropertyValueFactory<WorkSheetSheduleTm,String>("workEndTime"));
        colomnTask.setCellValueFactory(new PropertyValueFactory<WorkSheetSheduleTm , String>("taskName"));
        colomnEmId.setCellValueFactory(new PropertyValueFactory<WorkSheetSheduleTm , String>("employeeId"));

        try {
            loadNextWorksheetId();
            loadTableData();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load Worksheet ID");
        }
    }

    WorkSheetSheduleModel workSheetSheduleModel = new WorkSheetSheduleModel();
    public void loadNextWorksheetId() throws SQLException, ClassNotFoundException {
        String nextCustomerId = workSheetSheduleModel.getNextWorkSheetId();
        txtId.setText(nextCustomerId);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<WorkSheetSheduleDto> workSheetSheduleDtos = workSheetSheduleModel.getAllShedules();

        ObservableList<WorkSheetSheduleTm> workSheetSheduleTms = FXCollections.observableArrayList();

        for (WorkSheetSheduleDto workSheetSheduleDto : workSheetSheduleDtos) {
            WorkSheetSheduleTm workSheetSheduleTm = new WorkSheetSheduleTm(
                    workSheetSheduleDto.getWorkSheetId(),
                    workSheetSheduleDto.getWorkStartTime(),
                    workSheetSheduleDto.getWorkEndTime(),
                    workSheetSheduleDto.getTaskName(),
                    workSheetSheduleDto.getEmployeeId()
            );
            workSheetSheduleTms.add(workSheetSheduleTm);
        }
        tblWorksheet.setItems(workSheetSheduleTms);
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {

        loadNextWorksheetId();
        loadTableData();

        buttAdd.setDisable(false);

        buttUpdate.setDisable(true);
        buttRemove.setDisable(true);

        txtSatartTime.setText("");
        txtEndTime.setText("");
        txtTask.setText("");
        txtEmId.setText("");
    }
}
