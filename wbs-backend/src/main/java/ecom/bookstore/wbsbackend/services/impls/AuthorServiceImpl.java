package ecom.bookstore.wbsbackend.services.impls;

import ecom.bookstore.wbsbackend.dto.request.AuthorCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.AuthorResponseDTO;
import ecom.bookstore.wbsbackend.entities.Author;
import ecom.bookstore.wbsbackend.exceptions.ResourceAlreadyExistsException;
import ecom.bookstore.wbsbackend.exceptions.ResourceNotFound;
import ecom.bookstore.wbsbackend.exceptions.ResourceNotFoundException;
import ecom.bookstore.wbsbackend.mapper.AuthorMapper;
import ecom.bookstore.wbsbackend.repositories.AuthorRepo;
import ecom.bookstore.wbsbackend.services.AuthorService;
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
 * @created 15/11/2022 - 6:02 PM
 */
@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  public static final String branchFullName = Author.class.getSimpleName();
  private AuthorMapper authorMapper;

  @Autowired
  public void AuthorMapper(AuthorMapper authorMapper) {
    this.authorMapper = authorMapper;
  }

  private AuthorRepo authorRepo;

  @Autowired
  public void AuthorRepo(AuthorRepo authorRepo) {
    this.authorRepo = authorRepo;
  }

  @Override
  public List<AuthorResponseDTO> getAllAuthor() {
    this.LOGGER.info(String.format(Utils.LOG_GET_ALL_OBJECT, branchFullName));
    List<Author> entityList = this.authorRepo.findAll();
    return entityList.stream()
        .map(Author -> this.authorMapper.authorToAuthorResponseDTO(Author))
        .collect(Collectors.toList());
  }

  @Override
  public AuthorResponseDTO getAuthorById(Integer id) {
    this.LOGGER.info(String.format(Utils.LOG_GET_ALL_OBJECT_BY_FIELD, branchFullName, "Id", id));
    Author entityFound =
        this.authorRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFound(
                        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchFullName, "Id", id)));
    return this.authorMapper.authorToAuthorResponseDTO(entityFound);
  }

  @Override
  public AuthorResponseDTO createAuthor(AuthorCreationDTO creationDTO) {
    this.LOGGER.info(String.format(Utils.LOG_CREATE_OBJECT, branchFullName, "FullName", creationDTO.getFullName()));
    Optional<Author> entityFound = this.authorRepo.findByFullName(creationDTO.getFullName());
    if (entityFound.isPresent()) {
      throw new ResourceAlreadyExistsException(
          String.format(Utils.OBJECT_EXISTED_BY_FIELD, branchFullName, "FullName", creationDTO.getFullName()));
    }
    Author newEntity = new Author();
    newEntity.setFullName(creationDTO.getFullName());
    newEntity.setGender(creationDTO.getGender());
    return this.authorMapper.authorToAuthorResponseDTO(this.authorRepo.save(newEntity));
  }

  @Override
  public AuthorResponseDTO updateAuthor(Integer id, AuthorCreationDTO creationDTO) {
    this.LOGGER.info(String.format(Utils.LOG_UPDATE_OBJECT, branchFullName, "Id", id));
    Author entityFound =
        this.authorRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFound(
                        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchFullName, "Id", id)));
    if (creationDTO.getFullName() != null
        && !Objects.equals(creationDTO.getFullName().trim(), "")
        && Objects.equals(creationDTO.getFullName(), entityFound.getFullName())) {
      Optional<Author> duplicateEntity = this.authorRepo.findByFullName(creationDTO.getFullName());
      if (duplicateEntity.isPresent()) {
        throw new ResourceAlreadyExistsException(
            String.format(
                Utils.OBJECT_EXISTED_BY_FIELD, branchFullName, "FullName", creationDTO.getFullName()));
      }
    }
    entityFound.setFullName(creationDTO.getFullName());
    entityFound.setGender(creationDTO.getGender());
    return this.authorMapper.authorToAuthorResponseDTO(this.authorRepo.save(entityFound));
  }

  @Override
  public AuthorResponseDTO deleteAuthorById(Integer id) {
    this.LOGGER.info(String.format(Utils.LOG_DELETE_OBJECT, branchFullName, "Id", id));
    this.authorRepo
        .findById(id)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchFullName, "Id", id)));
    this.authorRepo.deleteById(id);
    return null;
  }
}
