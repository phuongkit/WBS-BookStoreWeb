package ecom.bookstore.wbsbackend.controller;

import ecom.bookstore.wbsbackend.dto.request.PublisherCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.PublisherResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import ecom.bookstore.wbsbackend.entities.Publisher;
import ecom.bookstore.wbsbackend.services.PublisherService;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Publisher minh phuong
 * @created 21/11/2022 - 8:28 PM
 */
@RestController
@RequestMapping(value = "/api/v1/publishers")
@CrossOrigin(origins = "*")
public class PublisherController {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  public static final String branchName = Publisher.class.getSimpleName();
  private PublisherService publisherService;

  @Autowired
  public void PublisherService(PublisherService publisherService) {
    this.publisherService = publisherService;
  }

  @GetMapping
  public ResponseObject<List<PublisherResponseDTO>> getAllPublisher() {
    return new ResponseObject<>(HttpStatus.OK, "", this.publisherService.getAllPublisher());
  }

  @GetMapping("/{id}")
  public ResponseObject<PublisherResponseDTO> getPublisherById(@PathVariable(name = "id") Integer id) {
    return new ResponseObject<>(HttpStatus.OK, "", this.publisherService.getPublisherById(id));
  }

  @PostMapping
  public ResponseObject<PublisherResponseDTO> createPublisher(
      @RequestBody @Valid PublisherCreationDTO publisherCreationDTO) {
    return new ResponseObject<>(
        HttpStatus.CREATED,
        String.format(Utils.CREATE_OBJECT_SUCCESSFULLY, branchName),
        this.publisherService.createPublisher(publisherCreationDTO));
  }

  @PutMapping("/{id}")
  public ResponseObject<PublisherResponseDTO> updatePublisher(
      @PathVariable(name = "id") Integer id,
      @RequestBody @Valid PublisherCreationDTO publisherCreationDTO) {
    return new ResponseObject<>(
        HttpStatus.OK,
        String.format(Utils.UPDATE_OBJECT_SUCCESSFULLY, branchName),
        this.publisherService.updatePublisher(id, publisherCreationDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseObject<PublisherResponseDTO> deletePublisher(@PathVariable(name = "id") Integer id) {
    return new ResponseObject<>(
        HttpStatus.OK,
        String.format(Utils.DELETE_OBJECT_SUCCESSFULLY, branchName),
        this.publisherService.deletePublisherById(id));
  }
}
