package lk.ijse.gdse.factory_mvc_project.model;

import lk.ijse.gdse.factory_mvc_project.db.DBConnection;
import lk.ijse.gdse.factory_mvc_project.dto.EmployeeDto;
import lk.ijse.gdse.factory_mvc_project.dto.MachineDto;
import lk.ijse.gdse.factory_mvc_project.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MachineModel {
    public String getNextMachineId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute( "select machine_id from machines order by machine_id desc limit 1");
        if (resultSet.next()) {
            String machineId = resultSet.getString(1);
            String subString = machineId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i+1;
            return String.format("M%03d", newIndex);
        }
        return "M001";
    }

    public ArrayList<MachineDto> getAllMachine() throws SQLException, ClassNotFoundException {
        ResultSet rst =  CrudUtil.execute("select * from machines");
        ArrayList<MachineDto> machineDtos = new ArrayList<>();

        while (rst.next()){
            MachineDto machineDto = new MachineDto();
            machineDto.setMachineId(rst.getString("machine_id"));
            machineDto.setMachineName(rst.getString("machine_name"));
            machineDto.setMachineTask(rst.getString("machine_task"));
            machineDto.setMachineQuantity(rst.getInt("machine_quantity"));
            machineDto.setEmployeeId(rst.getString("employee_id"));

            machineDtos.add(machineDto);
        }
        return machineDtos;
    }


    public boolean saveMachine(MachineDto machineDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "insert into machines values(?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, machineDto.getMachineId());
        statement.setString(2, machineDto.getMachineName());
        statement.setString(3,machineDto.getMachineTask());
        statement.setInt(4, machineDto.getMachineQuantity());
        statement.setString(5, machineDto.getEmployeeId());

        int isSaved = statement.executeUpdate();
        return isSaved > 0;
    }

    public boolean updateMachine(MachineDto machineDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "update machines set machine_name=?, machine_task=?,machine_quantity=?, employee_id=? where machine_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, machineDto.getMachineName());
        statement.setString(2,machineDto.getMachineTask());
        statement.setInt(3, machineDto.getMachineQuantity());
        statement.setString(4, machineDto.getEmployeeId());
        statement.setString(5, machineDto.getMachineId());


        int isUpdate = statement.executeUpdate();
        return isUpdate > 0;
    }

    public boolean deleteMachine(String machineId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from machines where machine_id=?", machineId);
    }
}
