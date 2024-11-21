package lk.ijse.gdse.factory_mvc_project.model;

import lk.ijse.gdse.factory_mvc_project.dto.ProductDetailDto;
import lk.ijse.gdse.factory_mvc_project.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDetail {
    private final StockModel stockModel = new StockModel();

    public boolean saveProductDetailsList(ArrayList<ProductDetailDto> productDetailDtos) throws SQLException, ClassNotFoundException {
        for (ProductDetailDto productDetailDto : productDetailDtos) {
            boolean isProductDetailsSaved = saveProductDetail(productDetailDto);
            if (!isProductDetailsSaved) {
                return false;
            }

            boolean isItemUpdated = stockModel.reduceQty(productDetailDto);
            if (!isItemUpdated) {
                return false;
            }
        }
        return true;
    }

    private boolean saveProductDetail(ProductDetailDto productDetailDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into product_details values (?,?,?,?,?,?)",
                productDetailDto.getItemId(),
                productDetailDto.getProductId(),
                productDetailDto.getItemName(),
                productDetailDto.getItemPrice(),
                productDetailDto.getTotalPrice(),
                productDetailDto.getItemQuantity()
        );
    }
}
