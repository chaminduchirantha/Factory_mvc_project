package lk.ijse.gdse.factory_mvc_project.model;

import lk.ijse.gdse.factory_mvc_project.db.DBConnection;
import lk.ijse.gdse.factory_mvc_project.dto.EmployeeDto;
import lk.ijse.gdse.factory_mvc_project.dto.WorkSheetSheduleDto;
import lk.ijse.gdse.factory_mvc_project.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WorkSheetSheduleModel {
    public String getNextWorkSheetId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute( "select shedule_id from worksheet_shedule order by shedule_id desc limit 1");
        if (resultSet.next()) {
            String workSheetId = resultSet.getString(1);
            String subString = workSheetId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i+1;
            return String.format("W%03d", newIndex);
        }
        return "W001";
    }

    public boolean saveShedule(WorkSheetSheduleDto workSheetSheduleDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "insert into worksheet_shedule values(?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, workSheetSheduleDto.getWorkSheetId());
        statement.setString(2, workSheetSheduleDto.getWorkStartTime());
        statement.setString(3,workSheetSheduleDto.getWorkEndTime());
        statement.setString(4, workSheetSheduleDto.getTaskName());
        statement.setString(5,workSheetSheduleDto.getEmployeeId());

        int isSaved = statement.executeUpdate();
        return isSaved > 0;
    }

    public ArrayList<WorkSheetSheduleDto> getAllShedules() throws SQLException, ClassNotFoundException {
        ResultSet rst =  CrudUtil.execute("select * from worksheet_shedule");
        ArrayList<WorkSheetSheduleDto> workSheetSheduleDtos = new ArrayList<>();

        while (rst.next()){
            WorkSheetSheduleDto workSheetSheduleDto = new WorkSheetSheduleDto();
            workSheetSheduleDto.setWorkSheetId(rst.getString("shedule_id"));
            workSheetSheduleDto.setWorkStartTime(rst.getString("shedule_start_time"));
            workSheetSheduleDto.setWorkEndTime(rst.getString("shedule_end_time"));
            workSheetSheduleDto.setTaskName(rst.getString("task_name"));
            workSheetSheduleDto.setEmployeeId(rst.getString("employee_id"));

            workSheetSheduleDtos.add(workSheetSheduleDto);
        }
        return workSheetSheduleDtos;
    }

    public boolean updateShedule(WorkSheetSheduleDto workSheetSheduleDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "update worksheet_shedule set shedule_start_time=?, shedule_end_time=?, task_name=?, employee_id=? where shedule_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, workSheetSheduleDto.getWorkStartTime());
        statement.setString(2,workSheetSheduleDto.getWorkEndTime());
        statement.setString(3, workSheetSheduleDto.getTaskName());
        statement.setString(4,workSheetSheduleDto.getEmployeeId());
        statement.setString(5, workSheetSheduleDto.getWorkSheetId());


        int isUpdate = statement.executeUpdate();
        return isUpdate > 0;
    }
    
    public boolean deleteWorkSheetShedule(String workSheetId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from worksheet_shedule where shedule_id=?", workSheetId);
    }
}
