package lk.ijse.gdse.factory_mvc_project.model;

import lk.ijse.gdse.factory_mvc_project.db.DBConnection;
import lk.ijse.gdse.factory_mvc_project.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeModel {
    public static boolean saveEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "insert into employee values(?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,employeeDto.getEmployeeId());
        preparedStatement.setString(2,employeeDto.getEmployeeName());
        preparedStatement.setInt(3,employeeDto.getEmployeeAge());
        preparedStatement.setString(4,employeeDto.getEmployeeAddress());
        preparedStatement.setString(5,employeeDto.getEmployeeSection());
        preparedStatement.setString(6,employeeDto.getEmployeeTask());
        preparedStatement.setInt(7,employeeDto.getEmployeeContactNumber());

        int resp = preparedStatement.executeUpdate();
        boolean isSaved = resp > 0;
        return isSaved;

    }
}
