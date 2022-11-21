package ecom.bookstore.wbsbackend.controller;

import ecom.bookstore.wbsbackend.dto.request.TranslatorCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import ecom.bookstore.wbsbackend.dto.response.TranslatorResponseDTO;
import ecom.bookstore.wbsbackend.entities.Translator;
import ecom.bookstore.wbsbackend.services.TranslatorService;
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
 * @Translator minh phuong
 * @created 21/11/2022 - 8:21 PM
 */
@RestController
@RequestMapping(value = "/api/v1/translators")
@CrossOrigin(origins = "*")
public class TranslatorController {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  public static final String branchName = Translator.class.getSimpleName();
  private TranslatorService translatorService;

  @Autowired
  public void TranslatorService(TranslatorService translatorService) {
    this.translatorService = translatorService;
  }

  @GetMapping
  public ResponseObject<List<TranslatorResponseDTO>> getAllTranslator() {
    return new ResponseObject<>(
        HttpStatus.OK, "", this.translatorService.getAllTranslator());
  }

  @GetMapping("/{id}")
  public ResponseObject<TranslatorResponseDTO> getTranslatorById(@PathVariable(name = "id") Integer id) {
    return new ResponseObject<>(
        HttpStatus.OK, "", this.translatorService.getTranslatorById(id));
  }

  @PostMapping
  public ResponseObject<TranslatorResponseDTO> createTranslator(
      @RequestBody @Valid TranslatorCreationDTO translatorCreationDTO) {
    return new ResponseObject<>(HttpStatus.CREATED, String.format(Utils.CREATE_OBJECT_SUCCESSFULLY, branchName),
                                this.translatorService.createTranslator(translatorCreationDTO));
  }

  @PutMapping("/{id}")
  public ResponseObject<TranslatorResponseDTO> updateTranslator(
      @PathVariable(name = "id") Integer id,
      @RequestBody  @Valid TranslatorCreationDTO translatorCreationDTO) {
    return new ResponseObject<>(HttpStatus.OK, String.format(Utils.UPDATE_OBJECT_SUCCESSFULLY, branchName),
                                this.translatorService.updateTranslator(id, translatorCreationDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseObject<TranslatorResponseDTO> deleteTranslator(@PathVariable(name = "id") Integer id) {
    return new ResponseObject<>(HttpStatus.OK, String.format(Utils.DELETE_OBJECT_SUCCESSFULLY, branchName),
                                this.translatorService.deleteTranslatorById(id));
  }
}
