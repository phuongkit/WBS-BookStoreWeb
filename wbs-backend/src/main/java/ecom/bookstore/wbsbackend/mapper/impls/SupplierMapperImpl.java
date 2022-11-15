package ecom.bookstore.wbsbackend.mapper.impls;

import ecom.bookstore.wbsbackend.dto.response.SupplierResponseDTO;
import ecom.bookstore.wbsbackend.entities.Supplier;
import ecom.bookstore.wbsbackend.mapper.SupplierMapper;
import org.springframework.stereotype.Component;

/**
 * @author minh phuong
 * @created 15/11/2022 - 5:59 PM
 */
@Component
public class SupplierMapperImpl implements SupplierMapper {
  @Override
  public SupplierResponseDTO supplierToSupplierResponseDTO(Supplier entity) {
    if (entity == null) {
      return null;
    }
    SupplierResponseDTO responseDTO = new SupplierResponseDTO();
    responseDTO.setId(entity.getId());
    responseDTO.setName(entity.getName());
    responseDTO.setDescription(entity.getDescription());
    return responseDTO;
  }
}
