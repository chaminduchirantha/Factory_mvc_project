package lk.ijse.gdse.factory_mvc_project.model;

import lk.ijse.gdse.factory_mvc_project.db.DBConnection;
import lk.ijse.gdse.factory_mvc_project.dto.EmployeeDto;
import lk.ijse.gdse.factory_mvc_project.dto.SalaryDto;
import lk.ijse.gdse.factory_mvc_project.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;

public class SalaryModel {
    public String getNextSalaryId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.execute( "select salary_id from salary order by salary_id desc limit 1");
        if (resultSet.next()) {
            String salaryId = resultSet.getString(1);
            String subString = salaryId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i+1;
            return String.format("S%03d", newIndex);
        }
        return "S001";
    }
    public boolean saveSalary(SalaryDto salaryDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "insert into salary values(?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, salaryDto.getSalaryId());
            statement.setString(2, salaryDto.getSalaryFees());
            statement.setDate(3, Date.valueOf(salaryDto.getSalaryReleaseDate()));
            statement.setString(4, salaryDto.getBasicSalary());
            statement.setString(5, salaryDto.getEmployeeId());

            int isSaved = statement.executeUpdate();
            return isSaved > 0;
    }

    public ArrayList<SalaryDto> getAllSalary() throws SQLException, ClassNotFoundException {
        ResultSet rst =  CrudUtil.execute("select * from salary");
        ArrayList<SalaryDto> salaryDtos = new ArrayList<>();

        while (rst.next()){
            SalaryDto salaryDto = new SalaryDto();
                salaryDto.setSalaryId(rst.getString("salary_id"));
                salaryDto.setSalaryFees(rst.getString("salary_fees"));
                salaryDto.setSalaryReleaseDate(rst.getDate("salary_release_date").toLocalDate());
                salaryDto.setBasicSalary(rst.getString("basic_salary"));
                salaryDto.setEmployeeId(rst.getString("employee_id"));

            salaryDtos.add(salaryDto);
        }
        return salaryDtos;
    }

    public boolean updateSalary(SalaryDto salaryDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "update salary set salary_fees=? , salary_release_date=?,basic_salary=?, employee_id=? where salary_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, salaryDto.getSalaryFees());
            statement.setDate(2, Date.valueOf(String.valueOf(Date.valueOf(salaryDto.getSalaryReleaseDate()))));
            statement.setString(3, salaryDto.getBasicSalary());
            statement.setString(4, salaryDto.getEmployeeId());
            statement.setString(5, salaryDto.getSalaryId());

            int isUpdate = statement.executeUpdate();
            return isUpdate > 0;
    }
    public boolean deleteSalary(String salaryId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from salary where salary_id=?", salaryId);
    }
}
