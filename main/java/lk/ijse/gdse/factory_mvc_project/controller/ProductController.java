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
import lk.ijse.gdse.factory_mvc_project.dto.ProductDto;
import lk.ijse.gdse.factory_mvc_project.dto.tm.ProductTm;
import lk.ijse.gdse.factory_mvc_project.model.ProductModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColomn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        nameColomn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        dateColomn.setCellValueFactory(new PropertyValueFactory<>("productDate"));
        priceColmn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        ratingsColomn.setCellValueFactory(new PropertyValueFactory<>("productRatings"));

        try {
            loadNextProductId();
            refreshPage();

        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load Product ID");
        }
    }

    ProductModel productModel = new ProductModel();
    private void loadNextProductId() throws SQLException, ClassNotFoundException {
        String nextProductId = productModel.getNextProductId();
        txtId.setText(nextProductId);
    }


    @FXML
    private Button buttClear;

    @FXML
    private Button buttDelete;

    @FXML
    private Button buttSave;

    @FXML
    private Button buttUpdate;

    @FXML
    private TableColumn<String , ProductTm> dateColomn;

    @FXML
    private Label emLable;

    @FXML
    private TableColumn<String, ProductTm> idColomn;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblId;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblRstings;

    @FXML
    private TableColumn<String, ProductTm> nameColomn;

    @FXML
    private TableColumn<String,ProductTm> priceColmn;

    @FXML
    private AnchorPane productAnchorPane;

    @FXML
    private TableColumn<String, ProductTm> ratingsColomn;

    @FXML
    private TableView<ProductTm> tblProduct;

    @FXML
    private DatePicker txtDatePicker;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtRtings;

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<ProductDto> productDtos = productModel.getAllProduct();

        ObservableList<ProductTm> productTms = FXCollections.observableArrayList();

        for (ProductDto productDto : productDtos) {
            ProductTm productTm = new ProductTm(
                    productDto.getProductId(),
                    productDto.getProductName(),
                    productDto.getProductPrice(),
                    productDto.getProductDate(),
                    productDto.getProductRatings()

            );
            productTms.add(productTm);
        }
        tblProduct.setItems(productTms);
    }

    @FXML
    void clearONAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();

    }

    @FXML
    void deleteOnAction(ActionEvent event) {

    }

    @FXML
    void saveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String productId = txtId.getText();
        String productName = txtName.getText();
        double productPrice =Double.parseDouble(txtPrice.getText());
        LocalDate productDate = txtDatePicker.getValue();
        String productRating = txtRtings.getText();

        ProductDto productDto = new ProductDto(productId, productName,productPrice, productDate, productRating);

        boolean isSaved = productModel.saveProduct(productDto);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Successfully saved the Product").show();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Successfully saved the Product").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {

//        loadNextProductId();
        loadTableData();
        buttSave.setDisable(false);

        buttUpdate.setDisable(true);
        buttDelete.setDisable(true);

        txtId.setText("");
        txtName.setText("");
        txtDatePicker.setTooltip(new Tooltip("Date"));
        txtPrice.setText("");
        txtRtings.setText("");

    }

    @FXML
    void tblProductOnMouseClicked(MouseEvent event) {

    }

    @FXML
    void updateOnAction(ActionEvent event) {

    }

  }
