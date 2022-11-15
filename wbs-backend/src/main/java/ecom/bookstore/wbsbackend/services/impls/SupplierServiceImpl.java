package ecom.bookstore.wbsbackend.services.impls;

import ecom.bookstore.wbsbackend.dto.response.SupplierResponseDTO;
import ecom.bookstore.wbsbackend.entities.Supplier;
import ecom.bookstore.wbsbackend.mapper.SupplierMapper;
import ecom.bookstore.wbsbackend.mapper.impls.SupplierMapperImpl;
import ecom.bookstore.wbsbackend.repositories.SupplierRepo;
import ecom.bookstore.wbsbackend.services.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author minh phuong
 * @created 15/11/2022 - 5:54 PM
 */
public class SupplierServiceImpl implements SupplierService {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  private SupplierMapper supplierMapper;
  @Autowired public void SupplierMapper(SupplierMapper supplierMapper) {
    this.supplierMapper = supplierMapper;
  }
  private SupplierRepo supplierRepo;

  @Autowired
  public void SupplierRepo(SupplierRepo supplierRepo) {
    this.supplierRepo = supplierRepo;
  }

  @Override
  public List<SupplierResponseDTO> getAllSupplier() {
    List<Supplier> suppliers = this.supplierRepo.findAll();
    return suppliers.stream().map(supplier -> this.supplierMapper.supplierToSupplierResponseDTO(supplier)).collect(
        Collectors.toList());
  }
}
