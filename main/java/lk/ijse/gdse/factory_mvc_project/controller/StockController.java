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
import lk.ijse.gdse.factory_mvc_project.dto.StockDto;
import lk.ijse.gdse.factory_mvc_project.dto.tm.EmployeeTm;
import lk.ijse.gdse.factory_mvc_project.dto.tm.StockTm;
import lk.ijse.gdse.factory_mvc_project.model.StockModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class StockController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colomnId.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colomnDesc.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        colomnPrice.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colomnQoh.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        colomnQuality.setCellValueFactory(new PropertyValueFactory<>("itemQuality"));

        try {
            loadNextItemId();
            loadTableData();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load Item ID");
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {

        loadNextItemId();
       loadTableData();

        buttSave.setDisable(false);

        buttUpdate.setDisable(true);
        buttDelete.setDisable(true);

        txtPrice.setText("");
        txtDesc.setText("");
        txtQoh.setText("");
        txtQu.setText("");

    }


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
    private Label lblDesc;

    @FXML
    private Label lblId;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblQoh;

    @FXML
    private Label lblQu;

    @FXML
    private AnchorPane stockAnchorPane;

    @FXML
    private TableView<StockTm> tblStock;

    @FXML
    private TableColumn<StockTm, String> colomnDesc;

    @FXML
    private TableColumn<StockTm, String> colomnId;

    @FXML
    private TableColumn<StockTm, String> colomnPrice;

    @FXML
    private TableColumn<StockTm, String> colomnQoh;

    @FXML
    private TableColumn<StockTm, String> colomnQuality;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQoh;

    @FXML
    private TextField txtQu;

    StockModel stockModel = new StockModel();

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<StockDto> stockDtos = stockModel.getAllItems();

        ObservableList<StockTm> stockTms = FXCollections.observableArrayList();

        for (StockDto stockDto : stockDtos) {
            StockTm stockTm = new StockTm(
                    stockDto.getItemCode(),
                    stockDto.getItemPrice(),
                    stockDto.getQuantity(),
                    stockDto.getItemDescription(),
                    stockDto.getItemQuality()
            );
            stockTms.add(stockTm);
        }
        tblStock.setItems(stockTms);
    }

    public void loadNextItemId() throws SQLException, ClassNotFoundException {

        String nextEmployeeId = stockModel.getNextItemCode();;
        txtCode.setText(nextEmployeeId);
    }

    @FXML
    void clearONAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();

    }

    @FXML
    void deleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String itemCode = txtCode.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION , "Are you sure you want to delete this item?" , ButtonType.YES , ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = stockModel.deleteItem(itemCode);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION , "item deleted").show();

            }else{
                new Alert(Alert.AlertType.ERROR , "item not deleted").show();
            }
        }
    }

    @FXML
    void reportOnAction(ActionEvent event) {

    }

    @FXML
    void tblOnMouseClick(MouseEvent event) throws SQLException, ClassNotFoundException {
        StockTm stockTm = tblStock.getSelectionModel().getSelectedItem();
        if (stockTm != null) {
            txtCode.setText(stockTm.getItemCode());
            txtPrice.setText(String.valueOf(stockTm.getItemPrice()));
            txtQoh.setText(String.valueOf(stockTm.getQuantity()));
            txtDesc.setText(stockTm.getItemDescription());
            txtQu.setText(stockTm.getItemQuality());


            buttSave.setDisable(true);

            buttDelete.setDisable(false);
            buttUpdate.setDisable(false);
            loadTableData();
        }
    }


    @FXML
    void saveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String itemCode = txtCode.getText();
        double itemPrice =Double.parseDouble(txtPrice.getText());
        int qoh = Integer.parseInt(txtQoh.getText());
        String itemDescription = txtDesc.getText();
        String itemQuality = txtQu.getText();

        StockDto stockDto = new StockDto(itemCode, itemPrice,qoh, itemDescription, itemQuality);

        boolean isSaved = stockModel.saveItem(stockDto);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Successfully saved the Employee").show();
//            loadTableData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to save the Employee").show();
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String itemCode = txtCode.getText();
        double itemPrice =Double.parseDouble(txtPrice.getText());
        int qoh = Integer.parseInt(txtQoh.getText());
        String itemDescription = txtDesc.getText();
        String itemQuality = txtQu.getText();

        StockDto stockDto = new StockDto(itemCode, itemPrice,qoh, itemDescription, itemQuality);

        boolean isSaved = stockModel.updateItem(stockDto);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Successfully update the Item").show();
//            loadTableData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to update the item").show();
        }
    }
}
