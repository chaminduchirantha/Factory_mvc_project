package lk.ijse.gdse.factory_mvc_project.model;

import lk.ijse.gdse.factory_mvc_project.db.DBConnection;
import lk.ijse.gdse.factory_mvc_project.dto.PaymentDto;
import lk.ijse.gdse.factory_mvc_project.dto.StockDto;
import lk.ijse.gdse.factory_mvc_project.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;

public class PaymentModel {
    public String getNextPaymentId() throws SQLException, ClassNotFoundException{
        ResultSet resultSet = CrudUtil.execute( "select payment_id from payment_management order by payment_id desc limit 1");
        if (resultSet.next()) {
            String paymentId = resultSet.getString(1);
            String subString = paymentId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i+1;
            return String.format("M%03d", newIndex);
        }
        return "M001";
    }

    public ArrayList<PaymentDto> getAllPayment() throws SQLException, ClassNotFoundException {
        ResultSet rst =  CrudUtil.execute("select * from payment_management");
        ArrayList<PaymentDto> paymentDtos = new ArrayList<>();

        while (rst.next()){
            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setPaymentId(rst.getString("payment_id"));
            paymentDto.setPaymentDate(rst.getDate("payment_date").toLocalDate());
            paymentDto.setPaymentAmount(rst.getDouble("payment_Amount"));
            paymentDto.setDiscount(rst.getDouble("discount"));
            paymentDto.setSupplierId(rst.getString("supplier_id"));

            paymentDtos.add(paymentDto);
        }
        return paymentDtos;
    }

    public boolean savePayment(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "insert into payment_management values(?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,paymentDto .getPaymentId());
        statement.setDate(2, Date.valueOf(paymentDto.getPaymentDate()));
        statement.setDouble(3, paymentDto.getPaymentAmount());
        statement.setDouble(4, paymentDto.getDiscount());
        statement.setString(5, paymentDto.getSupplierId());


        int isSaved = statement.executeUpdate();
        return isSaved > 0;
    }

    public boolean updatePayment(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "update payment_management set payment_date=?,discount=?,payment_Amount=?,supplier_id=? where payment_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, Date.valueOf(paymentDto.getPaymentDate()));
        statement.setDouble(2, paymentDto.getPaymentAmount());
        statement.setDouble(3, paymentDto.getDiscount());
        statement.setString(4, paymentDto.getSupplierId());
        statement.setString(5,paymentDto .getPaymentId());

        int isUpdate = statement.executeUpdate();
        return isUpdate > 0;
    }

    public boolean deletePayment(String paymentId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from payment_management where payment_id=?", paymentId);
    }
}
