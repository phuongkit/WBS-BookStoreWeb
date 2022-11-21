package ecom.bookstore.wbsbackend.services;

import ecom.bookstore.wbsbackend.dto.request.SupplierCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.SupplierResponseDTO;

import java.util.List;

/**
 * @Supplier minh phuong
 * @created 15/11/2022 - 5:52 PM
 */
public interface SupplierService {
  List<SupplierResponseDTO> getAllSupplier();
  SupplierResponseDTO getSupplierById(Integer id);
  SupplierResponseDTO createSupplier(SupplierCreationDTO creationDTO);
  SupplierResponseDTO updateSupplier(Integer id, SupplierCreationDTO creationDTO);
  SupplierResponseDTO deleteSupplierById(Integer id);
}
