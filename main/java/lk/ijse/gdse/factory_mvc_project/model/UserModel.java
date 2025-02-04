package lk.ijse.gdse.factory_mvc_project.model;

import lk.ijse.gdse.factory_mvc_project.db.DBConnection;
import lk.ijse.gdse.factory_mvc_project.dto.SalaryDto;
import lk.ijse.gdse.factory_mvc_project.dto.UserDto;
import lk.ijse.gdse.factory_mvc_project.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;

public class UserModel {
    public static String getNextUserId() throws SQLException, ClassNotFoundException {
        ResultSet rst= CrudUtil.execute("select userId from user order BY userId desc limit 1");
        if(rst.next()){
            return String.format("U%03d", Integer.parseInt(rst.getString(1).substring(1))+1);
        }
        return "U001";
    }

    public ArrayList<UserDto> getAllUser() throws SQLException, ClassNotFoundException {
        ResultSet rst =  CrudUtil.execute("select * from User");
        ArrayList<UserDto> userDtos = new ArrayList<>();

        while (rst.next()){
            UserDto userDto = new UserDto();
            userDto.setUserId(rst.getString("user_id"));
            userDto.setUserName(rst.getString("user_name"));
            userDto.setUserPassword(rst.getString("user_Password"));
            userDto.setUserEmail(rst.getString("user_Email"));
            userDto.setUserContactNumber(rst.getString("user_contact_number"));
            userDto.setBranchId(rst.getString("branch_id"));
            userDto.setEmployeeId(rst.getString("employee_id"));


            userDtos.add(userDto);
        }
        return userDtos;
    }

    public boolean saveUser(UserDto userDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "insert into user values(?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, userDto.getUserId());
        statement.setString(2, userDto.getUserName());
        statement.setString(3, userDto.getUserPassword());
        statement.setString(4, userDto.getUserEmail());
        statement.setString(5, userDto.getUserContactNumber());

        int isSaved = statement.executeUpdate();
        return isSaved > 0;
    }

    public boolean updateSUser(UserDto userDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "update user set user_name=?,user_password=?,user_email=?,use_contact_number=?,branch_id=?,employee_id=? where user_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, userDto.getUserName());
        statement.setString(2, userDto.getUserPassword());
        statement.setString(3, userDto.getUserEmail());
        statement.setString(4, userDto.getUserContactNumber());
        statement.setString(5, userDto.getUserId());
        statement.setString(6, userDto.getBranchId());
        statement.setString(7, userDto.getEmployeeId());

        int isUpdate = statement.executeUpdate();
        return isUpdate> 0;
    }

    public boolean deleteUser(String userId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from user where user_id=?", userId);
    }
}
