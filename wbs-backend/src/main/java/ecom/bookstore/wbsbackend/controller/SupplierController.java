package ecom.bookstore.wbsbackend.controller;

import ecom.bookstore.wbsbackend.dto.request.SupplierCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.SupplierResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import ecom.bookstore.wbsbackend.entities.Supplier;
import ecom.bookstore.wbsbackend.services.SupplierService;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Supplier minh phuong
 * @created 21/11/2022 - 8:27 PM
 */
@RestController
@RequestMapping(value = "/api/v1/suppliers")
@CrossOrigin(origins = "*")
public class SupplierController {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  public static final String branchName = Supplier.class.getSimpleName();
  private SupplierService supplierService;

  @Autowired
  public void SupplierService(SupplierService supplierService) {
    this.supplierService = supplierService;
  }

  @GetMapping
  public ResponseObject<List<SupplierResponseDTO>> getAllSupplier() {
    return new ResponseObject<>(HttpStatus.OK, "", this.supplierService.getAllSupplier());
  }

  @GetMapping("/{id}")
  public ResponseObject<SupplierResponseDTO> getSupplierById(@PathVariable(name = "id") Integer id) {
    return new ResponseObject<>(HttpStatus.OK, "", this.supplierService.getSupplierById(id));
  }

  @PostMapping
  public ResponseObject<SupplierResponseDTO> createSupplier(
      @RequestBody @Valid SupplierCreationDTO supplierCreationDTO) {
    return new ResponseObject<>(
        HttpStatus.CREATED,
        String.format(Utils.CREATE_OBJECT_SUCCESSFULLY, branchName),
        this.supplierService.createSupplier(supplierCreationDTO));
  }

  @PutMapping("/{id}")
  public ResponseObject<SupplierResponseDTO> updateSupplier(
      @PathVariable(name = "id") Integer id,
      @RequestBody @Valid SupplierCreationDTO SupplierCreationDTO) {
    return new ResponseObject<>(
        HttpStatus.OK,
        String.format(Utils.UPDATE_OBJECT_SUCCESSFULLY, branchName),
        this.supplierService.updateSupplier(id, SupplierCreationDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseObject<SupplierResponseDTO> deleteSupplier(@PathVariable(name = "id") Integer id) {
    return new ResponseObject<>(
        HttpStatus.OK,
        String.format(Utils.DELETE_OBJECT_SUCCESSFULLY, branchName),
        this.supplierService.deleteSupplierById(id));
  }
}
