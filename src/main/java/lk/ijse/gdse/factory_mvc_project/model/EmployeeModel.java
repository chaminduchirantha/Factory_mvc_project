package lk.ijse.gdse.factory_mvc_project.model;

import lk.ijse.gdse.factory_mvc_project.db.DBConnection;
import lk.ijse.gdse.factory_mvc_project.dto.EmployeeDto;
import lk.ijse.gdse.factory_mvc_project.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeModel {
//    private CrudUtil CrudUtill;
    public String getNextEmployeeId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.execute( "select employee_id from employee order by employee_id desc limit 1");
        if (resultSet.next()) {
            String customerId = resultSet.getString(1);
            String subString = customerId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i+1;
            return String.format("C%03d", newIndex);
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
}
