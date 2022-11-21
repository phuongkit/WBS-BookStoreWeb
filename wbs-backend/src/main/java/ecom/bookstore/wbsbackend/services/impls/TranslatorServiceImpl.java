package ecom.bookstore.wbsbackend.services.impls;

import ecom.bookstore.wbsbackend.dto.request.TranslatorCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.TranslatorResponseDTO;
import ecom.bookstore.wbsbackend.entities.Translator;
import ecom.bookstore.wbsbackend.exceptions.ResourceAlreadyExistsException;
import ecom.bookstore.wbsbackend.exceptions.ResourceNotFound;
import ecom.bookstore.wbsbackend.exceptions.ResourceNotFoundException;
import ecom.bookstore.wbsbackend.mapper.TranslatorMapper;
import ecom.bookstore.wbsbackend.repositories.TranslatorRepo;
import ecom.bookstore.wbsbackend.services.TranslatorService;
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
 * @created 21/11/2022 - 7:42 PM
 */
@Service
@Transactional
public class TranslatorServiceImpl implements TranslatorService {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  public static final String branchFullName = Translator.class.getSimpleName();
  private TranslatorMapper translatorMapper;

  @Autowired
  public void translatorMapper(TranslatorMapper translatorMapper) {
    this.translatorMapper = translatorMapper;
  }

  private TranslatorRepo translatorRepo;

  @Autowired
  public void translatorRepo(TranslatorRepo translatorRepo) {
    this.translatorRepo = translatorRepo;
  }

  @Override
  public List<TranslatorResponseDTO> getAllTranslator() {
    this.LOGGER.info(String.format(Utils.LOG_GET_ALL_OBJECT, branchFullName));
    List<Translator> entityList = this.translatorRepo.findAll();
    return entityList.stream()
        .map(Translator -> this.translatorMapper.translatorToTranslatorResponseDTO(Translator))
        .collect(Collectors.toList());
  }

  @Override
  public TranslatorResponseDTO getTranslatorById(Integer id) {
    this.LOGGER.info(String.format(Utils.LOG_GET_ALL_OBJECT_BY_FIELD, branchFullName, "Id", id));
    Translator entityFound =
        this.translatorRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFound(
                        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchFullName, "Id", id)));
    return this.translatorMapper.translatorToTranslatorResponseDTO(entityFound);
  }

  @Override
  public TranslatorResponseDTO createTranslator(TranslatorCreationDTO creationDTO) {
    this.LOGGER.info(String.format(Utils.LOG_CREATE_OBJECT, branchFullName, "FullName", creationDTO.getFullName()));
    Optional<Translator> entityFound = this.translatorRepo.findByFullName(creationDTO.getFullName());
    if (entityFound.isPresent()) {
      throw new ResourceAlreadyExistsException(
          String.format(Utils.OBJECT_EXISTED_BY_FIELD, branchFullName, "FullName", creationDTO.getFullName()));
    }
    Translator newEntity = new Translator();
    newEntity.setFullName(creationDTO.getFullName());
    newEntity.setGender(creationDTO.getGender());
    return this.translatorMapper.translatorToTranslatorResponseDTO(this.translatorRepo.save(newEntity));
  }

  @Override
  public TranslatorResponseDTO updateTranslator(Integer id, TranslatorCreationDTO creationDTO) {
    this.LOGGER.info(String.format(Utils.LOG_UPDATE_OBJECT, branchFullName, "Id", id));
    Translator entityFound =
        this.translatorRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFound(
                        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchFullName, "Id", id)));
    if (creationDTO.getFullName() != null
        && !Objects.equals(creationDTO.getFullName().trim(), "")
        && Objects.equals(creationDTO.getFullName(), entityFound.getFullName())) {
      Optional<Translator> duplicateEntity = this.translatorRepo.findByFullName(creationDTO.getFullName());
      if (duplicateEntity.isPresent()) {
        throw new ResourceAlreadyExistsException(
            String.format(
                Utils.OBJECT_EXISTED_BY_FIELD, branchFullName, "FullName", creationDTO.getFullName()));
      }
    }
    entityFound.setFullName(creationDTO.getFullName());
    entityFound.setGender(creationDTO.getGender());
    return this.translatorMapper.translatorToTranslatorResponseDTO(this.translatorRepo.save(entityFound));
  }

  @Override
  public TranslatorResponseDTO deleteTranslatorById(Integer id) {
    this.LOGGER.info(String.format(Utils.LOG_DELETE_OBJECT, branchFullName, "Id", id));
    this.translatorRepo
        .findById(id)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchFullName, "Id", id)));
    this.translatorRepo.deleteById(id);
    return null;
  }
}
