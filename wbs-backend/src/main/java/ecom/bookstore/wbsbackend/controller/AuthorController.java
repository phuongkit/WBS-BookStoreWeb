package ecom.bookstore.wbsbackend.controller;

import ecom.bookstore.wbsbackend.dto.request.AuthorCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.AuthorResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import ecom.bookstore.wbsbackend.entities.Author;
import ecom.bookstore.wbsbackend.services.AuthorService;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static ecom.bookstore.wbsbackend.utils.Utils.DEFAULT_PAGE;
import static ecom.bookstore.wbsbackend.utils.Utils.DEFAULT_SIZE;

/**
 * @author minh phuong
 * @created 21/11/2022 - 8:13 PM
 */
@RestController
@RequestMapping(value = "/api/v1/authors")
@CrossOrigin(origins = "*")
public class AuthorController {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  public static final String branchName = Author.class.getSimpleName();
  private AuthorService authorService;

  @Autowired
  public void AuthorService(AuthorService authorService) {
    this.authorService = authorService;
  }

  @GetMapping
  public ResponseObject<List<AuthorResponseDTO>> getAllAuthor() {
    return new ResponseObject<>(HttpStatus.OK, "", this.authorService.getAllAuthor());
  }

  @GetMapping("/{id}")
  public ResponseObject<AuthorResponseDTO> getAuthorById(@PathVariable(name = "id") Integer id) {
    return new ResponseObject<>(HttpStatus.OK, "", this.authorService.getAuthorById(id));
  }

  @PostMapping
  public ResponseObject<AuthorResponseDTO> createAuthor(
      @RequestBody @Valid AuthorCreationDTO authorCreationDTO) {
    return new ResponseObject<>(
        HttpStatus.CREATED,
        String.format(Utils.CREATE_OBJECT_SUCCESSFULLY, branchName),
        this.authorService.createAuthor(authorCreationDTO));
  }

  @PutMapping("/{id}")
  public ResponseObject<AuthorResponseDTO> updateAuthor(
      @PathVariable(name = "id") Integer id,
      @RequestBody @Valid AuthorCreationDTO authorCreationDTO) {
    return new ResponseObject<>(
        HttpStatus.OK,
        String.format(Utils.UPDATE_OBJECT_SUCCESSFULLY, branchName),
        this.authorService.updateAuthor(id, authorCreationDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseObject<AuthorResponseDTO> deleteAuthor(@PathVariable(name = "id") Integer id) {
    return new ResponseObject<>(
        HttpStatus.OK,
        String.format(Utils.DELETE_OBJECT_SUCCESSFULLY, branchName),
        this.authorService.deleteAuthorById(id));
  }
}
