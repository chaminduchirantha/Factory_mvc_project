package lk.ijse.gdse.factory_mvc_project.model;

import lk.ijse.gdse.factory_mvc_project.db.DBConnection;
import lk.ijse.gdse.factory_mvc_project.dto.AttendenceDto;
import lk.ijse.gdse.factory_mvc_project.dto.EmployeeDto;
import lk.ijse.gdse.factory_mvc_project.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;

public class AttendenceModel {
    public static String getNextAttendenceId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute( "select attendence_id from attendence order by attendence_id desc limit 1");
        if (resultSet.next()) {
            String attendenceId = resultSet.getString(1);
            String subString = attendenceId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i+1;
            return String.format("A%03d", newIndex);
        }
        return "A001";
    }

    public boolean saveAttendence(AttendenceDto attendenceDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "insert into attendence values(?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, attendenceDto.getAttendenceId());
        statement.setString(2, attendenceDto.getEntryTime());
        statement.setString(3,attendenceDto.getExitTime());
        statement.setDate(4, Date.valueOf(attendenceDto.getAttendenceDate()));
        statement.setString(5, attendenceDto.getShiftType());
        statement.setString(6,attendenceDto.getEmployeeId());


        int isSaved = statement.executeUpdate();
        return isSaved > 0;
    }

    public ArrayList<AttendenceDto> getAllAttendence() throws SQLException, ClassNotFoundException {
        ResultSet rst =  CrudUtil.execute("select * from attendence");
        ArrayList<AttendenceDto> attendenceDtos = new ArrayList<>();

        while (rst.next()){
            AttendenceDto attendenceDto = new AttendenceDto();
            attendenceDto.setAttendenceId(rst.getString("attendence_id"));
            attendenceDto.setEntryTime(rst.getString("entry_time"));
            attendenceDto.setExitTime(rst.getString("exite_time"));
            attendenceDto.setAttendenceDate(rst.getDate("attendence_date").toLocalDate());
            attendenceDto.setShiftType(rst.getString("shift_type"));
            attendenceDto.setEmployeeId(rst.getString("employee_id"));

            attendenceDtos.add(attendenceDto);
        }
        return attendenceDtos;
    }

    public boolean updateAttendence(AttendenceDto attendenceDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "update attendence set entry_time=?, exite_time=?, attendence_date=?, shift_type=?, employee_id=? where attendence_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, attendenceDto.getEntryTime());
        statement.setString(2,attendenceDto.getExitTime());
        statement.setDate(3, Date.valueOf(attendenceDto.getAttendenceDate()));
        statement.setString(4,attendenceDto.getShiftType());
        statement.setString(5,attendenceDto.getEmployeeId());
        statement.setString(6, attendenceDto.getAttendenceId());

        int isUpdate = statement.executeUpdate();
        return isUpdate > 0;
    }

    public boolean deleteAttendence(String AttendenceId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from attendence where attendence_id=?", AttendenceId);
    }
}
