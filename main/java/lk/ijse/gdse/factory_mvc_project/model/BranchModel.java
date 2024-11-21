package lk.ijse.gdse.factory_mvc_project.model;

import lk.ijse.gdse.factory_mvc_project.db.DBConnection;
import lk.ijse.gdse.factory_mvc_project.dto.BranchDto;
import lk.ijse.gdse.factory_mvc_project.dto.EmployeeDto;
import lk.ijse.gdse.factory_mvc_project.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BranchModel{
    public String getNextBranchId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute( "select branch_id from branch order by branch_id desc limit 1");
        if (resultSet.next()) {
            String branchId = resultSet.getString(1);
            String subString = branchId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i+1;
            return String.format("B%03d", newIndex);
        }
        return "B001";
    }

    public ArrayList<BranchDto> getAllBranches() throws SQLException, ClassNotFoundException {
        ResultSet rst =  CrudUtil.execute("select * from branch");
        ArrayList<BranchDto> branchDtos = new ArrayList<>();

        while (rst.next()){
            BranchDto branchDto = new BranchDto();
            branchDto.setBranchId(rst.getString("branch_id"));
            branchDto.setBranchName(rst.getString("branch_name"));
            branchDto.setBranchLocation(rst.getString("branch_location"));
            branchDto.setNumberOfEmployee(rst.getInt("number_of_employee"));
            branchDto.setItemCode(rst.getString("item_id"));

            branchDtos.add(branchDto);
        }
        return branchDtos;
    }

    public boolean saveBranch(BranchDto branchDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "insert into branch values(?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, branchDto.getBranchId());
        statement.setString(2, branchDto.getBranchName());
        statement.setString(3,branchDto.getBranchLocation());
        statement.setInt(4, branchDto.getNumberOfEmployee());
        statement.setString(5,branchDto.getItemCode());

        int isSaved = statement.executeUpdate();
        return isSaved > 0;
    }

    public boolean updateBranch(BranchDto branchDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "update branch set branch_name=?, branch_location=?, number_of_employee=?, item_id=? where branch_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, branchDto.getBranchName());
        statement.setString(2,branchDto.getBranchLocation());
        statement.setInt(3, branchDto.getNumberOfEmployee());
        statement.setString(4,branchDto.getItemCode());
        statement.setString(5, branchDto.getBranchId());


        int isUpdate = statement.executeUpdate();
        return isUpdate > 0;
    }

    public boolean deleteBranch(String branchId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from branch where branch_id=?", branchId);
    }
}
