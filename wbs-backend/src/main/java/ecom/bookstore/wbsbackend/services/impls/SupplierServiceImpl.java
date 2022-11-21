package ecom.bookstore.wbsbackend.services.impls;

import ecom.bookstore.wbsbackend.dto.request.SupplierCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.SupplierResponseDTO;
import ecom.bookstore.wbsbackend.entities.Sale;
import ecom.bookstore.wbsbackend.entities.Supplier;
import ecom.bookstore.wbsbackend.exceptions.ResourceAlreadyExistsException;
import ecom.bookstore.wbsbackend.exceptions.ResourceNotFound;
import ecom.bookstore.wbsbackend.exceptions.ResourceNotFoundException;
import ecom.bookstore.wbsbackend.mapper.SupplierMapper;
import ecom.bookstore.wbsbackend.mapper.impls.SupplierMapperImpl;
import ecom.bookstore.wbsbackend.repositories.SupplierRepo;
import ecom.bookstore.wbsbackend.services.SupplierService;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author minh phuong
 * @created 15/11/2022 - 5:54 PM
 */
@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  public static final String branchName = Supplier.class.getSimpleName();
  private SupplierMapper supplierMapper;

  @Autowired
  public void SupplierMapper(SupplierMapper supplierMapper) {
    this.supplierMapper = supplierMapper;
  }

  private SupplierRepo supplierRepo;

  @Autowired
  public void SupplierRepo(SupplierRepo supplierRepo) {
    this.supplierRepo = supplierRepo;
  }

  @Override
  public List<SupplierResponseDTO> getAllSupplier() {
    this.LOGGER.info(String.format(Utils.LOG_GET_ALL_OBJECT, branchName));
    List<Supplier> entityList = this.supplierRepo.findAll();
    return entityList.stream()
        .map(supplier -> this.supplierMapper.supplierToSupplierResponseDTO(supplier))
        .collect(Collectors.toList());
  }

  @Override
  public SupplierResponseDTO getSupplierById(Integer id) {
    this.LOGGER.info(String.format(Utils.LOG_GET_ALL_OBJECT_BY_FIELD, branchName, "Id", id));
    Supplier entityFound =
        this.supplierRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFound(
                        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchName, "Id", id)));
    return this.supplierMapper.supplierToSupplierResponseDTO(entityFound);
  }

  @Override
  public SupplierResponseDTO createSupplier(SupplierCreationDTO creationDTO) {
    this.LOGGER.info(String.format(Utils.LOG_CREATE_OBJECT, branchName, "Name", creationDTO.getName()));
    Optional<Supplier> entityFound = this.supplierRepo.findByName(creationDTO.getName());
    if (entityFound.isPresent()) {
      throw new ResourceAlreadyExistsException(
          String.format(Utils.OBJECT_EXISTED_BY_FIELD, branchName, "Name", creationDTO.getName()));
    }
    Supplier newEntity = new Supplier();
    newEntity.setName(creationDTO.getName());
    newEntity.setDescription(creationDTO.getDescription());
    return this.supplierMapper.supplierToSupplierResponseDTO(this.supplierRepo.save(newEntity));
  }

  @Override
  public SupplierResponseDTO updateSupplier(Integer id, SupplierCreationDTO creationDTO) {
    this.LOGGER.info(String.format(Utils.LOG_UPDATE_OBJECT, branchName, "Id", id));
    Supplier entityFound =
        this.supplierRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFound(
                        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchName, "Id", id)));
    if (creationDTO.getName() != null
        && !Objects.equals(creationDTO.getName().trim(), "")
        && Objects.equals(creationDTO.getName(), entityFound.getName())) {
      Optional<Supplier> duplicateEntity = this.supplierRepo.findByName(creationDTO.getName());
      if (duplicateEntity.isPresent()) {
        throw new ResourceAlreadyExistsException(
            String.format(
                Utils.OBJECT_EXISTED_BY_FIELD, branchName, "Name", creationDTO.getName()));
      }
    }
    entityFound.setName(creationDTO.getName());
    entityFound.setDescription(creationDTO.getDescription());
    return this.supplierMapper.supplierToSupplierResponseDTO(this.supplierRepo.save(entityFound));
  }

  @Override
  public SupplierResponseDTO deleteSupplierById(Integer id) {
    this.LOGGER.info(String.format(Utils.LOG_DELETE_OBJECT, branchName, "Id", id));
    this.supplierRepo
        .findById(id)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchName, "Id", id)));
    this.supplierRepo.deleteById(id);
    return null;
  }
}
