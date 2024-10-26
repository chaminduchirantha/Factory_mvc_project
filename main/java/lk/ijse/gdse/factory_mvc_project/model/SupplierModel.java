package lk.ijse.gdse.factory_mvc_project.model;

import lk.ijse.gdse.factory_mvc_project.db.DBConnection;
import lk.ijse.gdse.factory_mvc_project.dto.EmployeeDto;
import lk.ijse.gdse.factory_mvc_project.dto.SupplierDto;
import lk.ijse.gdse.factory_mvc_project.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {
    public String getNextSupplierId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select supplier_id from supplier_management order by supplier_id desc limit 1");
        if (resultSet.next()) {
            String supplierId = resultSet.getString(1);
            String subString = supplierId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i + 1;
            return String.format("S%03d", newIndex);
        }
        return "S001";
    }
    public boolean saveSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "insert into supplier_management values(?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, supplierDto.getSupplierId());
        statement.setString(2, supplierDto.getSupplierName());
        statement.setString(3,supplierDto.getSupplierAddress());
        statement.setInt(4, supplierDto.getSupplierAge());
        statement.setString(5,supplierDto.getSupplierContactNumber());
        statement.setString(6,supplierDto.getSupplierNICNumber());

        int isSaved = statement.executeUpdate();
        return isSaved > 0;
    }

    public ArrayList<SupplierDto> getAllSupplier() throws SQLException, ClassNotFoundException {
        ResultSet rst =  CrudUtil.execute("select * from supplier_management");
        ArrayList<SupplierDto> supplierDtos = new ArrayList<>();

        while (rst.next()){
            SupplierDto supplierDto = new SupplierDto();
            supplierDto.setSupplierId(rst.getString("supplier_id"));
            supplierDto.setSupplierName(rst.getString("supplier_name"));
            supplierDto.setSupplierAddress(rst.getString("supplier_address"));
            supplierDto.setSupplierAge(rst.getInt("supplier_age"));
            supplierDto.setSupplierNICNumber(rst.getString("supplier_contact_number"));
            supplierDto.setSupplierContactNumber(rst.getString("supplier_Nic_no"));

            supplierDtos.add(supplierDto);
        }
        return supplierDtos;
    }

    public boolean updateSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "update supplier_management set supplier_name=?, supplier_address=?, supplier_age=?, supplier_contact_number=?, supplier_Nic_no=? where supplier_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, supplierDto.getSupplierName());
        statement.setString(2,supplierDto.getSupplierAddress());
        statement.setInt(3, supplierDto.getSupplierAge());
        statement.setString(4,supplierDto.getSupplierContactNumber());
        statement.setString(5,supplierDto.getSupplierNICNumber());
        statement.setString(6, supplierDto.getSupplierId());


        int isSaved = statement.executeUpdate();
        return isSaved > 0;
    }

    public boolean deleteSupplier(String supplierId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from supplier_management where supplier_id=?", supplierId);
    }
}
