package lk.ijse.gdse.factory_mvc_project.model;

import lk.ijse.gdse.factory_mvc_project.dto.SupplierDetailDto;
import lk.ijse.gdse.factory_mvc_project.util.CrudUtil;

import java.sql.SQLException;

public class SupplierDetailsModel {
    private boolean saveSupplierDetails(SupplierDetailDto supplierDetailDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into supplier_details values (?,?,?)",
                supplierDetailDto.getItemId(),
                supplierDetailDto.getSupplierId(),
                supplierDetailDto.getSupplierRatings()

        );
    }
}
