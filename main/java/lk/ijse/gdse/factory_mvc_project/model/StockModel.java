package lk.ijse.gdse.factory_mvc_project.model;

import lk.ijse.gdse.factory_mvc_project.db.DBConnection;
import lk.ijse.gdse.factory_mvc_project.dto.EmployeeDto;
import lk.ijse.gdse.factory_mvc_project.dto.ProductDetailDto;
import lk.ijse.gdse.factory_mvc_project.dto.StockDto;
import lk.ijse.gdse.factory_mvc_project.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StockModel {
    //    private CrudUtil CrudUtill;
    public String getNextItemCode() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute( "select item_id from item_management order by item_id desc limit 1");
        if (resultSet.next()) {
            String itemCode = resultSet.getString(1);
            String subString = itemCode.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i+1;
            return String.format("P%03d", newIndex);
        }
        return "P001";
    }

    public boolean saveItem(StockDto stockDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "insert into item_management values(?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, stockDto.getItemCode());
        statement.setDouble(2, stockDto.getItemPrice());
        statement.setInt(3,stockDto.getQuantity());
        statement.setString(4, stockDto.getItemDescription());
        statement.setString(5,stockDto.getItemQuality());

        int isSaved = statement.executeUpdate();
        return isSaved > 0;
    }

    public ArrayList<StockDto> getAllItems() throws SQLException, ClassNotFoundException {
        ResultSet rst =  CrudUtil.execute("select * from item_management");
        ArrayList<StockDto> stockDtos = new ArrayList<>();

        while (rst.next()){
            StockDto stockDto = new StockDto();
            stockDto.setItemCode(rst.getString("item_id"));
            stockDto.setItemPrice(rst.getDouble("item_price"));
            stockDto.setQuantity(rst.getInt("item_quantity_on_hand"));
            stockDto.setItemDescription(rst.getString("item_description"));
            stockDto.setItemQuality(rst.getString("item_quality"));

            stockDtos.add(stockDto);
        }
        return stockDtos;
    }

    public boolean updateItem(StockDto stockDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "update item_management set item_price=?, item_quantity_on_hand=?, item_description=?,item_quality=? where item_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, stockDto.getItemPrice());
        statement.setInt(2,stockDto.getQuantity());
        statement.setString(3, stockDto.getItemDescription());
        statement.setString(4,stockDto.getItemQuality());
        statement.setString(5, stockDto.getItemCode());


        int isUpdate = statement.executeUpdate();
        return isUpdate > 0;
    }

    public boolean deleteItem(String itemCode) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from item_management where item_id=?", itemCode);
    }

    public ArrayList<String> getAllItemIDs() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select item_id from item_management");
        ArrayList<String> itemIds = new ArrayList<>();
        while (rst.next()) {
            itemIds.add(rst.getString(1));
        }
        return itemIds;
    }

    public StockDto findById(String selectItemId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from item_management where item_id=?", selectItemId);

        if (rst.next()) {
            return new StockDto(rst.getString(1), rst.getDouble(2), rst.getInt(3), rst.getString(4), rst.getString(5));

        }
        return null;
    }
    public boolean reduceQty(ProductDetailDto productDetailDto) throws SQLException, ClassNotFoundException {
        // Execute SQL query to update the item quantity in the database
        return CrudUtil.execute(
                "update item_management set item_quantity_on_hand = item_quantity_on_hand - ? where item_id = ?",
                productDetailDto.getItemQuantity(),   // Quantity to reduce
                productDetailDto.getItemId()      // Item ID
        );
    }

}
