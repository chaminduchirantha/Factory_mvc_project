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
            String customerId = resultSet.getString(1);
            String subString = customerId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i+1;
            return String.format("E%03d", newIndex);
        }
        return "E001";
    }

    public boolean saveEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        boolean isSaved =  CrudUtil.execute("insert into employee values(?,?,?,?,?,?,?)" ,
                employeeDto.getEmployeeId() ,
                employeeDto.getEmployeeName() ,
                employeeDto.getEmployeeAge() ,
                employeeDto.getEmployeeAddress() ,
                employeeDto.getEmployeeSection(),
                employeeDto.getEmployeeTask(),
                employeeDto.getEmployeeContactNumber());

        return isSaved;
    }

    public ArrayList<EmployeeDto> getAllEmployees() throws SQLException, ClassNotFoundException {
        ResultSet rst =  CrudUtil.execute("select * from employee");

        ArrayList<EmployeeDto> employeeDtos = new ArrayList<>();

        while (rst.next()){
            EmployeeDto employeeDto = new EmployeeDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            );
            employeeDtos.add(employeeDto);
        }
        return employeeDtos;
    }

    public boolean updateEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        boolean isUpdate = CrudUtil.execute(
                "update employee set name=? , age=?, address=?, section=?, task=?, phone=? where employee_id=?",
                employeeDto.getEmployeeName(),
            employeeDto.getEmployeeAge(),
            employeeDto.getEmployeeAddress(),
            employeeDto.getEmployeeSection(),
            employeeDto.getEmployeeTask(),
            employeeDto.getEmployeeContactNumber(),
            employeeDto.getEmployeeId()

        );
        return isUpdate;
    }
    public boolean deleteEmployee(String employeeId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from employee where employee_id=?", employeeId);
    }
}
