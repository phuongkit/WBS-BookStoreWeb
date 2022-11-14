package ecom.bookstore.wbsbackend.mapper;

import ecom.bookstore.wbsbackend.dto.response.SaleResponseDTO;
import ecom.bookstore.wbsbackend.entities.Sale;

/**
 * @author minh phuong
 * @created 16/09/2022 - 8:47 PM
 */
public interface SaleMapper {
  SaleResponseDTO saleToSaleResponseDTO(Sale entity, boolean... isFull);
}
