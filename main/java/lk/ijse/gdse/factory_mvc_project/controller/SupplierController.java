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
import lk.ijse.gdse.factory_mvc_project.dto.SupplierDto;
import lk.ijse.gdse.factory_mvc_project.dto.tm.EmployeeTm;
import lk.ijse.gdse.factory_mvc_project.dto.tm.SupplierTm;
import lk.ijse.gdse.factory_mvc_project.model.SupplierModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColomn.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        nameColomn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        addressColomn.setCellValueFactory(new PropertyValueFactory<>("supplierAddress"));
        ageColomn.setCellValueFactory(new PropertyValueFactory<>("supplierAge"));
        PhoneColomn.setCellValueFactory(new PropertyValueFactory<>("supplierContactNumber"));
        nicColomn.setCellValueFactory(new PropertyValueFactory<>("supplierNICNumber"));

        try {
            loadTableData();
            refreshPage();
            loadNextSupplierId();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load Supplier ID");

        }
    }

    public void loadNextSupplierId() throws SQLException, ClassNotFoundException {

        String nextSupplierId = supplierModel.getNextSupplierId();
        txtId.setText(nextSupplierId);
    }

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private TableColumn<SupplierTm, String> PhoneColomn;

    @FXML
    private TableColumn<SupplierTm, String> addressColomn;

    @FXML
    private TableColumn<SupplierTm, Integer> ageColomn;

    @FXML
    private TableColumn<SupplierTm, String> idColomn;

    @FXML
    private TableColumn<SupplierTm, String> nameColomn;

    @FXML
    private TableColumn<SupplierTm, String> nicColomn;

    @FXML
    private Button buttClear;

    @FXML
    private Button buttDelete;

    @FXML
    private Button buttSave;

    @FXML
    private Button buttUpdate;

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
    private AnchorPane supplierAnchorPane;

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

    SupplierModel supplierModel = new SupplierModel();

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierDto> supplierDtos = supplierModel.getAllSupplier();

        ObservableList<SupplierTm> supplierTms = FXCollections.observableArrayList();

        for (SupplierDto supplierDto : supplierDtos) {
            SupplierTm supplierTm = new SupplierTm(
                    supplierDto.getSupplierId(),
                    supplierDto.getSupplierName(),
                    supplierDto.getSupplierAddress(),
                    supplierDto.getSupplierAge(),
                    supplierDto.getSupplierContactNumber(),
                    supplierDto.getSupplierNICNumber()
            );
            supplierTms.add(supplierTm);
        }
        tblSupplier.setItems(supplierTms);
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {

        loadTableData();
        loadNextSupplierId();

        buttSave.setDisable(false);

        buttUpdate.setDisable(true);
        buttDelete.setDisable(true);

        txtName.setText("");
        txtAge.setText("");
        txtAdderess.setText("");
        txtPhone.setText("");
        txtNic.setText("");

    }

    @FXML
    void clearONAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();

    }

    @FXML
    void deleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String supplierId = txtId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Supplier?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = supplierModel.deleteSupplier(supplierId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "supplier deleted").show();

            } else {
                new Alert(Alert.AlertType.ERROR, "supplier not deleted").show();
            }
        }
    }

    @FXML
    void saveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String supplierId = txtId.getText();
        String supplierName = txtName.getText();
        String supplierAddress = txtAdderess.getText();
        int supplierAge = Integer.parseInt(txtAge.getText());
        String supplierContactNumber = txtPhone.getText();
        String supplierNic =txtNic.getText();

        SupplierDto supplierDto = new SupplierDto(supplierId, supplierName,supplierAddress, supplierAge, supplierContactNumber, supplierNic);

        boolean isSaved = supplierModel.saveSupplier(supplierDto);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Successfully saved the Supplier").show();
//            loadTableData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to save the Supplier").show();
        }
    }

    @FXML
    void tblSupplierOnMouseClicked(MouseEvent event) throws SQLException, ClassNotFoundException {

        SupplierTm supplierTm = tblSupplier.getSelectionModel().getSelectedItem();
        if (supplierTm != null) {
            txtId.setText(supplierTm.getSupplierId());
            txtName.setText(supplierTm.getSupplierName());
            txtAge.setText(String.valueOf(supplierTm.getSupplierAge()));
            txtAdderess.setText(supplierTm.getSupplierAddress());
            txtPhone.setText(supplierTm.getSupplierContactNumber());
            txtNic.setText(supplierTm.getSupplierNICNumber());

            buttSave.setDisable(true);

            buttDelete.setDisable(false);
            buttUpdate.setDisable(false);
            loadTableData();
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String supplierId = txtId.getText();
        String supplierName = txtName.getText();
        String supplierAddress = txtAdderess.getText();
        int supplierAge = Integer.parseInt(txtAge.getText());
        String supplierContactNumber = txtPhone.getText();
        String supplierNic =txtNic.getText();

        SupplierDto supplierDto = new SupplierDto(supplierId, supplierName,supplierAddress, supplierAge, supplierContactNumber, supplierNic);

        boolean isSaved = supplierModel.updateSupplier(supplierDto);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Successfully update the Supplier").show();
//            loadTableData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to update the Supplier").show();
        }

    }
}
