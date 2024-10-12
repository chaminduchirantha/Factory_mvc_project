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
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {



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
    private TableColumn<EmployeeTm, String> idColomn;

    @FXML
    private TableColumn<EmployeeTm, String> nameColomn;

    @FXML
    private TableColumn<EmployeeTm, String> ageColomn;

    @FXML
    private TableColumn<EmployeeTm, String> addressColomn;

    @FXML
    private TableColumn<EmployeeTm, String> sectionColmn;

    @FXML
    private TableColumn<EmployeeTm, String> taskColomn;

    @FXML
    private TableColumn<EmployeeTm, String> phoneColomn;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColomn.setCellValueFactory(new PropertyValueFactory<>("Employee id"));
        nameColomn.setCellValueFactory(new PropertyValueFactory<>("Employee Name"));
        ageColomn.setCellValueFactory(new PropertyValueFactory<>("age"));
        addressColomn.setCellValueFactory(new PropertyValueFactory<>("Employee Address"));
        sectionColmn.setCellValueFactory(new PropertyValueFactory<>("section"));
        taskColomn.setCellValueFactory(new PropertyValueFactory<>("task"));
        phoneColomn.setCellValueFactory(new PropertyValueFactory<>("Phone"));

        try {
            loadNextEmployeeId();
            loadTableData();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load Employee ID");
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {

        loadNextEmployeeId();

        buttSave.setDisable(false);

        buttUpdate.setDisable(true);
        buttDelete.setDisable(true);

        txtName.setText("");
        txtAge.setText("");
        txtAdderess.setText("");
        txtSection.setText("");
        txtTask.setText("");
        txtPhone.setText("");

    }

    @FXML
    void clearONAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();

    }

    @FXML
    void deleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String employeeId = txtId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION , "Are you sure you want to delete this employee?" , ButtonType.YES , ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = employeeModel.deleteEmployee(employeeId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION , "Employee deleted").show();

            }else{
                new Alert(Alert.AlertType.ERROR , "Employee not deleted").show();
            }
        }
    }

    @FXML
    void reportOnAction(ActionEvent event) {

    }

    EmployeeModel employeeModel = new EmployeeModel();

   private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDto> employeeDtos = employeeModel.getAllEmployees();

        ObservableList<EmployeeTm> employeeTms = FXCollections.observableArrayList();

            for (EmployeeDto employeeDto : employeeDtos) {
           EmployeeTm employeeTm = new EmployeeTm(
                        employeeDto.getEmployeeId(),
                        employeeDto.getEmployeeName(),
                        employeeDto.getEmployeeAge(),
                        employeeDto.getEmployeeAddress(),
                        employeeDto.getEmployeeSection(),
                        employeeDto.getEmployeeTask(),
                        employeeDto.getEmployeeContactNumber()
            );
            employeeTms.add(employeeTm);
        }
        tblEmployee.setItems(employeeTms);
    }

    public void loadNextEmployeeId() throws SQLException, ClassNotFoundException {

        String nextCustomerId = employeeModel.getNextEmployeeId();
        txtId.setText(nextCustomerId);
    }


    @FXML
    void saveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String employeeId = txtId.getText();
        String employeeName = txtName.getText();
        String age =txtAge.getText();
        String address = txtAdderess.getText();
        String section = txtSection.getText();
        String task = txtTask.getText();
        String phone =txtPhone.getText();

        EmployeeDto employeeDto = new EmployeeDto(employeeId, employeeName,age, address, section, task, phone);

        boolean isSaved = employeeModel.saveEmployee(employeeDto);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Successfully saved the Employee").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to save the Employee").show();
        }
    }

    @FXML
    void tblEmployeeOnMouseClicked(MouseEvent event) {
        EmployeeTm employeeTm = tblEmployee.getSelectionModel().getSelectedItem();
        if (employeeTm != null) {
            txtId.setText(employeeTm.getEmployeeId());
            txtName.setText(employeeTm.getEmployeeName());
            txtAge.setText(employeeTm.getEmployeeAge());
            txtAdderess.setText(employeeTm.getEmployeeAddress());
            txtSection.setText(employeeTm.getEmployeeSection());
            txtTask.setText(employeeTm.getEmployeeTask());
            txtPhone.setText(employeeTm.getEmployeeContactNumber());

            buttSave.setDisable(true);

            buttDelete.setDisable(false);
            buttUpdate.setDisable(false);
        }

    }

    @FXML
    void updateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String employeeId = txtId.getText();
        String employeeName = txtName.getText();
        String age = txtAge.getText();
        String address = txtAdderess.getText();
        String section = txtSection.getText();
        String task = txtTask.getText();
        String phone = txtPhone.getText();

        EmployeeDto employeeDto = new EmployeeDto(employeeId, employeeName, age, address, section, task, phone);

        boolean isUpdate = employeeModel.updateEmployee(employeeDto);
        if (isUpdate) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Successfully saved the Employee").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to save the employee").show();
        }
    }
}
