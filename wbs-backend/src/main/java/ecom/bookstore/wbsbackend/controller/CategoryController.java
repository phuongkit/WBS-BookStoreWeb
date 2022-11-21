package ecom.bookstore.wbsbackend.controller;

import ecom.bookstore.wbsbackend.dto.request.CategoryCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.CategoryResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import ecom.bookstore.wbsbackend.entities.Category;
import ecom.bookstore.wbsbackend.services.CategoryService;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static ecom.bookstore.wbsbackend.utils.Utils.DEFAULT_PAGE;
import static ecom.bookstore.wbsbackend.utils.Utils.DEFAULT_SIZE;

/**
 * @author minh phuong
 * @created 11/10/2022 - 9:42 PM
 */
@RestController
@RequestMapping(value = "/api/v1/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  public static final String branchName = Category.class.getSimpleName();
  private CategoryService categoryService;

  @Autowired
  public void categoryService(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  public ResponseObject<List<CategoryResponseDTO>> getAllCategories() {
    return new ResponseObject<>(
        HttpStatus.OK, "", this.categoryService.getAllCategories());
  }

  @GetMapping("/hierarchy")
  public ResponseObject<List<CategoryResponseDTO>> getAllCategoriesWithHierarchy(
      @RequestParam(name = "sortField", required = false, defaultValue = "id") String sortField,
      @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir,
      @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword) {
    return new ResponseObject<>(
        HttpStatus.OK, "", this.categoryService.getAllCategoriesWithHierarchy());
  }

  @GetMapping("/{id}")
  public ResponseObject<CategoryResponseDTO> getCategoryById(@PathVariable(name = "id") Integer id) {
    return new ResponseObject<>(
        HttpStatus.OK, "", this.categoryService.getCategoryById(id));
  }

  @GetMapping("/slug/{slug}")
  public ResponseObject<CategoryResponseDTO> getCategoryBySlug(@PathVariable(name = "slug") String slug) {
    return new ResponseObject<>(
        HttpStatus.OK, "", this.categoryService.getCategoryBySlug(slug));
  }

  @PostMapping
  public ResponseObject<CategoryResponseDTO> createCategory(
      @RequestPart("data")  @Valid CategoryCreationDTO categoryCreationDTO,
      @RequestPart(value = "image", required = false) MultipartFile imageFile) {
    return new ResponseObject<>(HttpStatus.CREATED, String.format(Utils.CREATE_OBJECT_SUCCESSFULLY, branchName),
        this.categoryService.createCategory(categoryCreationDTO, imageFile));
  }

  @PutMapping("/{id}")
  public ResponseObject<CategoryResponseDTO> updateCategory(
      @PathVariable(name = "id") Integer id,
      @RequestPart("data")  @Valid CategoryCreationDTO categoryCreationDTO,
      @RequestPart(value = "image", required = false) MultipartFile imageFile) {
    return new ResponseObject<>(HttpStatus.OK, String.format(Utils.UPDATE_OBJECT_SUCCESSFULLY, branchName),
        this.categoryService.updateCategory(id, categoryCreationDTO, imageFile));
  }

  @DeleteMapping("/{id}")
  public ResponseObject<CategoryResponseDTO> deleteCategory(@PathVariable(name = "id") Integer id) {
    return new ResponseObject<>(HttpStatus.OK, String.format(Utils.DELETE_OBJECT_SUCCESSFULLY, branchName),
        this.categoryService.deleteCategoryById(id));
  }
}
