package ecom.bookstore.wbsbackend.mapper;

import ecom.bookstore.wbsbackend.dto.response.SupplierResponseDTO;
import ecom.bookstore.wbsbackend.entities.Supplier;

/**
 * @author minh phuong
 * @created 15/11/2022 - 5:57 PM
 */
public interface SupplierMapper {
  SupplierResponseDTO supplierToSupplierResponseDTO(Supplier entity);
}
