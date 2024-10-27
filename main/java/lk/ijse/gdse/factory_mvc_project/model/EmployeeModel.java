package lk.ijse.gdse.factory_mvc_project.model;

import lk.ijse.gdse.factory_mvc_project.db.DBConnection;
import lk.ijse.gdse.factory_mvc_project.dto.EmployeeDto;
import lk.ijse.gdse.factory_mvc_project.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {
    //    private CrudUtil CrudUtill;
    public String getNextEmployeeId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute( "select employee_id from employee order by employee_id desc limit 1");
        if (resultSet.next()) {
            String employeeId = resultSet.getString(1);
            String subString = employeeId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i+1;
            return String.format("E%03d", newIndex);
        }
        return "E001";
    }

    public boolean saveEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "insert into employee values(?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, employeeDto.getEmployeeId());
        statement.setString(2, employeeDto.getEmployeeName());
        statement.setInt(3,employeeDto.getEmployeeAge());
        statement.setString(4, employeeDto.getEmployeeAddress());
        statement.setString(5,employeeDto.getEmployeeSection());
        statement.setString(6,employeeDto.getEmployeeTask());
        statement.setString(7, employeeDto.getEmployeeContactNumber());

        int isSaved = statement.executeUpdate();
        return isSaved > 0;
    }

    public ArrayList<EmployeeDto> getAllEmployees() throws SQLException, ClassNotFoundException {
        ResultSet rst =  CrudUtil.execute("select * from employee");
        ArrayList<EmployeeDto> employeeDtos = new ArrayList<>();

        while (rst.next()){
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setEmployeeId(rst.getString("employee_id"));
            employeeDto.setEmployeeName(rst.getString("employee_name"));
            employeeDto.setEmployeeAge(rst.getInt("employee_age"));
            employeeDto.setEmployeeAddress(rst.getString("employee_address"));
            employeeDto.setEmployeeSection(rst.getString("employee_section"));
            employeeDto.setEmployeeTask(rst.getString("employee_task"));
            employeeDto.setEmployeeContactNumber(rst.getString("employee_contact_number"));

            employeeDtos.add(employeeDto);
        }
        return employeeDtos;
    }

    public boolean updateEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "update employee set employee_name=?, employee_age=?, employee_address=?, employee_section=?, employee_task=?, employee_contact_number=? where employee_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, employeeDto.getEmployeeName());
        statement.setInt(2, employeeDto.getEmployeeAge());
        statement.setString(3, employeeDto.getEmployeeAddress());
        statement.setString(4, employeeDto.getEmployeeSection());
        statement.setString(5, employeeDto.getEmployeeTask());
        statement.setString(6, employeeDto.getEmployeeContactNumber());
        statement.setString(7, employeeDto.getEmployeeId());

        int isUpdate  = statement.executeUpdate();
        return isUpdate > 0;
    }

    public boolean deleteEmployee(String employeeId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from employee where employee_id=?", employeeId);
    }
}