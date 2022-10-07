package ecom.bookstore.wbsbackend.mapper;

import ecom.bookstore.wbsbackend.dto.response.SaleResponseDTO;
import ecom.bookstore.wbsbackend.entities.Sales;

/**
 * @author minh phuong
 * @created 16/09/2022 - 8:47 PM
 * @project gt-backend
 */
public interface SaleMapper {
  SaleResponseDTO saleToSaleResponseDTO(Sales entity, boolean... isFull);
}
