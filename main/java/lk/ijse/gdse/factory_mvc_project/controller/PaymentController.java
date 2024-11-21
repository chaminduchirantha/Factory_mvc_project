package lk.ijse.gdse.factory_mvc_project.controller;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.gdse.factory_mvc_project.dto.PaymentDto;
import lk.ijse.gdse.factory_mvc_project.dto.SupplierDto;
import lk.ijse.gdse.factory_mvc_project.dto.tm.PaymentTm;
import lk.ijse.gdse.factory_mvc_project.model.PaymentModel;
import lk.ijse.gdse.factory_mvc_project.model.SupplierModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {
    @FXML
    private AnchorPane PaymentAnchorPane;

    @FXML
    private ComboBox<String> cmbSupplierId;

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
    private Label lblSupName;

    @FXML
    private Label lblSupNumber;

    @FXML
    private Label lblSupPhone;

    @FXML
    private Label lblSupName1;

    @FXML
    private Label lblDiscount;

    @FXML
    private Label lblId;

    @FXML
    private Label lblPrice;

    @FXML
    private DatePicker paymentDate;

    @FXML
    private TableView<PaymentTm> tblPayment;

    @FXML
    private TableColumn<String, PaymentTm> colmnDiscount;

    @FXML
    private TableColumn<String, PaymentTm> colomnDate;

    @FXML
    private TableColumn<String, PaymentTm> colomnId;

    @FXML
    private TableColumn<String, PaymentTm> colomnPrice;

    @FXML
    private TableColumn<String, PaymentTm> colomnSupId;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDiscount;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtPrice;

    SupplierModel supplierModel = new SupplierModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colomnId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colomnDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colmnDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colomnPrice.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
        colomnSupId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));


        TranslateTransition slider = new TranslateTransition();
        slider.setNode(PaymentAnchorPane);
        slider.setDuration(Duration.seconds(1.0));
        slider.setFromX(-200);
        slider.setToX(0);
        slider.play();

        try {
            loadNextPaymentId();
            loadSupplierIds();
            loadTableData();

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    PaymentModel paymentModel = new PaymentModel();

    private void loadNextPaymentId() {
        try {
            String nextPaymentId = paymentModel.getNextPaymentId();
            txtCode.setText(nextPaymentId);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Payment not found").show();
        }
    }

    private void loadTableData(){
        try {
            ArrayList<PaymentDto> paymentDtos = paymentModel.getAllPayment();

            ObservableList<PaymentTm> paymentTms = FXCollections.observableArrayList();

            for (PaymentDto paymentDto : paymentDtos) {
                PaymentTm paymentTm = new PaymentTm(
                        paymentDto.getPaymentId(),
                        paymentDto.getPaymentDate(),
                        paymentDto.getDiscount(),
                        paymentDto.getPaymentAmount(),
                        paymentDto.getSupplierId()

                );
                paymentTms.add(paymentTm);
            }
            tblPayment.setItems(paymentTms);

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Payment not found").show();
        }
    }

    private void refreshPage(){
        loadNextPaymentId();

        buttSave.setDisable(false);

        buttUpdate.setDisable(true);
        buttDelete.setDisable(true);

        txtCode.setText("");
        txtDate.setTooltip(new Tooltip("Date"));
        txtPrice.setText("");
        txtDiscount.setText("");
        cmbSupplierId.setValue("");
    }


    @FXML
    void clearONAction(ActionEvent event) {
        refreshPage();

    }

    @FXML
    void deleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            String paymentId = txtCode.getText();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Payment?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> optionalButtonType = alert.showAndWait();

            if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
                boolean isDeleted = paymentModel.deletePayment(paymentId);
                if (isDeleted) {
                    refreshPage();
                    new Alert(Alert.AlertType.INFORMATION, "Payment deleted").show();
                    loadTableData();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Payment not deleted").show();
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Payment not found").show();
        }
    }

    @FXML
    void saveOnAction(ActionEvent event) {
        try {
            String paymentId = txtCode.getText();
            LocalDate paymentDate = txtDate.getValue();
            double paymentAmount = Double.parseDouble(txtPrice.getText());
            double paymentDiscount = Double.parseDouble(txtDiscount.getText());
            String supplierId = cmbSupplierId.getValue();

            PaymentDto paymentDto = new PaymentDto(
                    paymentId ,
                    paymentDate ,
                    paymentDiscount ,
                    paymentAmount ,
                    supplierId
            );
            boolean isSaved = paymentModel.savePayment(paymentDto);

            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Payment added successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Payment not added successfully").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Payment not found").show();
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
//        try {
            String paymentId = txtCode.getText();
            LocalDate paymentDate = txtDate.getValue();
            double paymentAmount = Double.parseDouble(txtPrice.getText());
            double paymentDiscount = Double.parseDouble(txtDiscount.getText());
            String supplierId = cmbSupplierId.getValue();

            PaymentDto paymentDto = new PaymentDto(
                    paymentId ,
                    paymentDate ,
                    paymentDiscount ,
                    paymentAmount ,
                    supplierId
            );

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Payment?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isSaved = paymentModel.updatePayment(paymentDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Payment update successfully").show();
//                loadTableData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Payment not update successfully").show();
            }
        }

//        } catch (Exception e) {
//            new Alert(Alert.AlertType.ERROR, "Payment not found").show();
//        }
    }

    @FXML
    void tblOnMouseClick(MouseEvent event) {
        PaymentTm paymentTm = tblPayment.getSelectionModel().getSelectedItem();
        if (paymentTm != null) {
            txtCode.setText(paymentTm.getPaymentId());
            txtDate.setValue(paymentTm.getPaymentDate());
            txtPrice.setText(String.valueOf(paymentTm.getPaymentAmount()));
            txtDiscount.setText(String.valueOf(paymentTm.getDiscount()));
            cmbSupplierId.setValue(paymentTm.getSupplierId());

            buttSave.setDisable(true);
            buttDelete.setDisable(false);
            buttUpdate.setDisable(false);
            loadTableData();
        }
    }

    private void loadSupplierIds(){
        try {
            ArrayList<String> supplierIds = supplierModel.getAllSupplierIDs();
            ObservableList<String> observableList = FXCollections.observableArrayList();
            observableList.addAll(supplierIds);
            cmbSupplierId.setItems(observableList);
        }catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, "Supplier not found").show();
        }
    }

    @FXML
    void cmbSupplierOnAction(ActionEvent event){
        try {
            String selectedSupplierId = cmbSupplierId.getSelectionModel().getSelectedItem();
            SupplierDto supplierDto = supplierModel.findById(selectedSupplierId);

            if (supplierDto != null) {

                lblSupName.setText(supplierDto.getSupplierName());
                lblSupNumber.setText(supplierDto.getSupplierContactNumber());

            }
        }catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, "Supplier not found").show();
        }
    }
}




