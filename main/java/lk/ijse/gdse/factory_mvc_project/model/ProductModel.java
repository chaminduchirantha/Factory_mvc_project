package lk.ijse.gdse.factory_mvc_project.model;

import lk.ijse.gdse.factory_mvc_project.db.DBConnection;
import lk.ijse.gdse.factory_mvc_project.dto.EmployeeDto;
import lk.ijse.gdse.factory_mvc_project.dto.ProductDto;
import lk.ijse.gdse.factory_mvc_project.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;

public class ProductModel {
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
        String sql = "insert into product_management values(?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, productDto.getProductId());
        statement.setString(2, productDto.getProductName());
        statement.setDouble(3,productDto.getProductPrice());
        statement.setDate(4, Date.valueOf(productDto.getProductDate()));
        statement.setString(5,productDto.getProductRatings());

        int isSaved = statement.executeUpdate();
        return isSaved > 0;
    }

    public ArrayList<ProductDto> getAllProduct() throws SQLException, ClassNotFoundException {
        ResultSet rst =  CrudUtil.execute("select * from product_management");
        ArrayList<ProductDto> productDtos = new ArrayList<>();

        while (rst.next()){
            ProductDto productDto = new ProductDto();
            productDto.setProductId(rst.getString("product_id"));
            productDto.setProductName(rst.getString("product_name"));
            productDto.setProductPrice(rst.getDouble("product_price"));
            productDto.setProductDate(rst.getDate("product_date").toLocalDate());
            productDto.setProductRatings(rst.getString("product_ratings"));

            productDtos.add(productDto);
        }
        return productDtos;
    }

    public boolean updateProduct(ProductDto productDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "update product_management set product_name=?,product_price=?,product_date=?,product_ratings where product_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, productDto.getProductName());
        statement.setDouble(2,productDto.getProductPrice());
        statement.setDate(3, Date.valueOf(productDto.getProductDate()));
        statement.setString(4,productDto.getProductRatings());
        statement.setString(5, productDto.getProductId());


        int isUpdate = statement.executeUpdate();
        return isUpdate > 0;
    }

    public boolean deleteProduct(String productId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from product_management where product_id=?", productId);
    }
}
