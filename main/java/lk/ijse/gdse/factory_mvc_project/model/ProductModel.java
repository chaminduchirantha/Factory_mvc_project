package lk.ijse.gdse.factory_mvc_project.model;

import lk.ijse.gdse.factory_mvc_project.db.DBConnection;
import lk.ijse.gdse.factory_mvc_project.dto.EmployeeDto;
import lk.ijse.gdse.factory_mvc_project.dto.ProductDto;
import lk.ijse.gdse.factory_mvc_project.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;

public class ProductModel {
    ProductDetail productDetail = new ProductDetail();
    public String getNextProductId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select product_id from product_management order by product_id desc limit 1");
        if (resultSet.next()) {
            String productId = resultSet.getString(1);
            String subString = productId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i + 1;
            return String.format("D%03d", newIndex);
        }
        return "D001";
    }

    public boolean saveProduct(ProductDto productDto) throws SQLException, ClassNotFoundException {

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isProductSave = CrudUtil.execute(
                    "insert into product_management values (?,?,?,?)",
                    productDto.getProductId(),
                    productDto.getProductName(),
                    productDto.getProductDate(),
                    productDto.getProductPrice()
            );
            if (isProductSave) {
                boolean isProductDetailListSaved = productDetail.saveProductDetailsList(productDto.getProductDetailDtos());
                if (isProductDetailListSaved) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            System.out.println(e.getMessage());
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}


