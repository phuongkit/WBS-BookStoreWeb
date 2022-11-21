package ecom.bookstore.wbsbackend.services.impls;

import ecom.bookstore.wbsbackend.dto.request.PublisherCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.PublisherResponseDTO;
import ecom.bookstore.wbsbackend.entities.Publisher;
import ecom.bookstore.wbsbackend.exceptions.ResourceAlreadyExistsException;
import ecom.bookstore.wbsbackend.exceptions.ResourceNotFound;
import ecom.bookstore.wbsbackend.exceptions.ResourceNotFoundException;
import ecom.bookstore.wbsbackend.mapper.PublisherMapper;
import ecom.bookstore.wbsbackend.repositories.PublisherRepo;
import ecom.bookstore.wbsbackend.services.PublisherService;
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
 * @created 21/11/2022 - 7:44 PM
 */
@Service
@Transactional
public class PublisherServiceImpl implements PublisherService {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  public static final String branchName = Publisher.class.getSimpleName();
  private PublisherMapper publisherMapper;

  @Autowired
  public void PublisherMapper(PublisherMapper publisherMapper) {
    this.publisherMapper = publisherMapper;
  }

  private PublisherRepo publisherRepo;

  @Autowired
  public void PublisherRepo(PublisherRepo publisherRepo) {
    this.publisherRepo = publisherRepo;
  }

  @Override
  public List<PublisherResponseDTO> getAllPublisher() {
    this.LOGGER.info(String.format(Utils.LOG_GET_ALL_OBJECT, branchName));
    List<Publisher> entityList = this.publisherRepo.findAll();
    return entityList.stream()
        .map(Publisher -> this.publisherMapper.publisherToPublisherResponseDTO(Publisher))
        .collect(Collectors.toList());
  }

  @Override
  public PublisherResponseDTO getPublisherById(Integer id) {
    this.LOGGER.info(String.format(Utils.LOG_GET_ALL_OBJECT_BY_FIELD, branchName, "Id", id));
    Publisher entityFound =
        this.publisherRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFound(
                        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchName, "Id", id)));
    return this.publisherMapper.publisherToPublisherResponseDTO(entityFound);
  }

  @Override
  public PublisherResponseDTO createPublisher(PublisherCreationDTO creationDTO) {
    this.LOGGER.info(String.format(Utils.LOG_CREATE_OBJECT, branchName, "Name", creationDTO.getName()));
    Optional<Publisher> entityFound = this.publisherRepo.findByName(creationDTO.getName());
    if (entityFound.isPresent()) {
      throw new ResourceAlreadyExistsException(
          String.format(Utils.OBJECT_EXISTED_BY_FIELD, branchName, "Name", creationDTO.getName()));
    }
    Publisher newEntity = new Publisher();
    newEntity.setName(creationDTO.getName());
    newEntity.setDescription(creationDTO.getDescription());
    return this.publisherMapper.publisherToPublisherResponseDTO(this.publisherRepo.save(newEntity));
  }

  @Override
  public PublisherResponseDTO updatePublisher(Integer id, PublisherCreationDTO creationDTO) {
    this.LOGGER.info(String.format(Utils.LOG_UPDATE_OBJECT, branchName, "Id", id));
    Publisher entityFound =
        this.publisherRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFound(
                        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchName, "Id", id)));
    if (creationDTO.getName() != null
        && !Objects.equals(creationDTO.getName().trim(), "")
        && Objects.equals(creationDTO.getName(), entityFound.getName())) {
      Optional<Publisher> duplicateEntity = this.publisherRepo.findByName(creationDTO.getName());
      if (duplicateEntity.isPresent()) {
        throw new ResourceAlreadyExistsException(
            String.format(
                Utils.OBJECT_EXISTED_BY_FIELD, branchName, "Name", creationDTO.getName()));
      }
    }
    entityFound.setName(creationDTO.getName());
    entityFound.setDescription(creationDTO.getDescription());
    return this.publisherMapper.publisherToPublisherResponseDTO(this.publisherRepo.save(entityFound));
  }

  @Override
  public PublisherResponseDTO deletePublisherById(Integer id) {
    this.LOGGER.info(String.format(Utils.LOG_DELETE_OBJECT, branchName, "Id", id));
    this.publisherRepo
        .findById(id)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchName, "Id", id)));
    this.publisherRepo.deleteById(id);
    return null;
  }
}
