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
import lk.ijse.gdse.factory_mvc_project.dto.SalaryDto;
import lk.ijse.gdse.factory_mvc_project.dto.tm.EmployeeTm;
import lk.ijse.gdse.factory_mvc_project.dto.tm.SalaryTm;
import lk.ijse.gdse.factory_mvc_project.model.EmployeeModel;
import lk.ijse.gdse.factory_mvc_project.model.SalaryModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SalaryController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colomnId.setCellValueFactory(new PropertyValueFactory<>("salaryId"));
        colomnReleasDate.setCellValueFactory(new PropertyValueFactory<>("salaryReleaseDate"));
        colomnSalaryFees.setCellValueFactory(new PropertyValueFactory<>("salaryFees"));
        colomnBasicSalary.setCellValueFactory(new PropertyValueFactory<>("basicSalary"));
        colomnEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));

        try {
            loadNextSalaryId();
            loadNextEmployeeId();
            loadTableData();

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    SalaryModel salaryModel = new SalaryModel();
    EmployeeModel employeeModel = new EmployeeModel();

    private void loadNextSalaryId() throws SQLException, ClassNotFoundException {

        String nextSalaryId = salaryModel.getNextSalaryId();
        txtId.setText(nextSalaryId);
    }

    private void loadNextEmployeeId() throws SQLException, ClassNotFoundException {
        String nextEmployeeId =employeeModel.getNextEmployeeId();
        txtEmId.setText(nextEmployeeId);
    }

    @FXML
    private Button buttAdd;

    @FXML
    private Button buttClear;

    @FXML
    private Button buttRemove;

    @FXML
    private Button buttUpdate;

    @FXML
    private Label lblBasicSalry;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblEmId;

    @FXML
    private Label lblFees;

    @FXML
    private Label lblid;

    @FXML
    private AnchorPane salaryAnchorPane;

    @FXML
    private TableView<SalaryTm> tblSalary;

    @FXML
    private TableColumn<SalaryTm, String> colomnBasicSalary;

    @FXML
    private TableColumn<SalaryTm, String> colomnEmployeeId;

    @FXML
    private TableColumn<SalaryTm, String> colomnId;

    @FXML
    private TableColumn<SalaryTm, String> colomnReleasDate;

    @FXML
    private TableColumn<SalaryTm, String> colomnSalaryFees;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtEmId;

    @FXML
    private TextField txtFees;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtSalary;

    @FXML
    void addOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String salaryId = txtId.getText();
        String salaryFees = txtFees.getText();
        LocalDate salaryReleaseDate = txtDate.getValue();
        String basicSalary = txtSalary.getText();
        String employeeId = txtEmId.getText();

        SalaryDto salaryDto = new SalaryDto(salaryId , salaryFees , salaryReleaseDate , basicSalary , employeeId);
        boolean isSaved = salaryModel.saveSalary(salaryDto);

        if(isSaved){
            new Alert(Alert.AlertType.INFORMATION,"Salary added successfully").show();
            loadTableData();
        }else {
            new Alert(Alert.AlertType.ERROR, "Salary not added successfully").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<SalaryDto> salaryDtos = salaryModel.getAllSalary();

        ObservableList<SalaryTm> salaryTms = FXCollections.observableArrayList();

        for (SalaryDto salaryDto : salaryDtos) {
            SalaryTm salaryTm = new SalaryTm(
                    salaryDto.getSalaryId(),
                    salaryDto.getSalaryFees(),
                    salaryDto.getSalaryReleaseDate(),
                    salaryDto.getBasicSalary(),
                    salaryDto.getEmployeeId()

            );
            salaryTms.add(salaryTm);
        }
        tblSalary.setItems(salaryTms);
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {

        loadNextSalaryId();

        buttAdd.setDisable(false);

        buttUpdate.setDisable(true);
        buttRemove.setDisable(true);

        txtFees.setText("");
        txtFees.setText("");
        txtDate.setTooltip(new Tooltip("Date"));
        txtSalary.setText("");
        txtEmId.setText("");

    }

    @FXML
    void clearOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();

    }

    @FXML
    void removeOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String salaryId = txtId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION , "Are you sure you want to delete this salary?" , ButtonType.YES , ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = salaryModel.deleteSalary(salaryId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION , "Salary deleted").show();
                loadTableData();

            }else{
                new Alert(Alert.AlertType.ERROR , "Salary not deleted").show();
            }
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String salaryId = txtId.getText();
        String salaryFees = txtFees.getText();
        LocalDate salaryReleaseDate = txtDate.getValue();
        String basicSalary = txtSalary.getText();
        String employeeId = txtEmId.getText();

        if (salaryReleaseDate == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a valid salary release date").show();
            return;
        }

        SalaryDto salaryDto = new SalaryDto(salaryId , salaryFees , salaryReleaseDate , basicSalary , employeeId);
        boolean isUpdate = salaryModel.updateSalary(salaryDto);

        if(isUpdate){
            new Alert(Alert.AlertType.INFORMATION,"Salary update successfully").show();
            loadTableData();
        }else {
            new Alert(Alert.AlertType.ERROR, "Salary not update successfully").show();
        }
    }
    @FXML
    void salaryOnMouseClicked(MouseEvent event) throws SQLException, ClassNotFoundException {
        SalaryTm salaryTm = tblSalary.getSelectionModel().getSelectedItem();
        if (salaryTm != null) {
            txtId.setText(salaryTm.getSalaryId());
            txtFees.setText(salaryTm.getSalaryFees());
            txtDate.setValue(salaryTm.getSalaryReleaseDate());
            txtSalary.setText(salaryTm.getBasicSalary());
            txtEmId.setText(salaryTm.getEmployeeId());

            buttAdd.setDisable(true);
            buttRemove.setDisable(false);
            buttUpdate.setDisable(false);
            loadTableData();
        }
    }
}
