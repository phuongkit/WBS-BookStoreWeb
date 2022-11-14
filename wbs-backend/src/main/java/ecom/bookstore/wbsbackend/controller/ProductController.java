package ecom.bookstore.wbsbackend.controller;

import ecom.bookstore.wbsbackend.dto.request.ProductCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.ProductGalleryDTO;
import ecom.bookstore.wbsbackend.dto.response.ProductResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import ecom.bookstore.wbsbackend.entities.Product;
import ecom.bookstore.wbsbackend.models.enums.EHomeOption;
import ecom.bookstore.wbsbackend.services.ProductService;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

import static ecom.bookstore.wbsbackend.utils.Utils.*;

/**
 * @author minh phuong
 * @created 11/10/2022 - 9:41 PM
 */
@RestController
@RequestMapping(value = "/api/v1/products")
@CrossOrigin(origins = "*")
public class ProductController {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  public static final String branchName = Product.class.getSimpleName();
  private ProductService productService;

  @Autowired
  public void ProductService(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("")
  public ResponseObject<Page<ProductResponseDTO>> getAllProduc(
      @RequestParam(name = "page", required = false, defaultValue = DEFAULT_PAGE) Integer page,
      @RequestParam(name = "limit", required = false, defaultValue = PRODUCT_PER_PAGE) Integer size,
      @RequestParam(name = "sortField", required = false, defaultValue = "id") String sortField,
      @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir) {
    Sort sort = Sort.by(sortField);
    sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
    Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, size, sort);
    return new ResponseObject<>(
        HttpStatus.OK, "", this.productService.getAllProduct(pageable));
  }

  @GetMapping("/filter")
  public ResponseObject<Page<ProductResponseDTO>> getAllProduct(
      @RequestParam(name = "page", required = false, defaultValue = DEFAULT_PAGE) Integer page,
      @RequestParam(name = "limit", required = false, defaultValue = PRODUCT_PER_PAGE) Integer size,
      @RequestParam(name = "sortField", required = false, defaultValue = "id") String sortField,
      @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir,
      @RequestParam(name = "brand", required = false, defaultValue = "") String brandName,
      @RequestParam(name = "category", required = false, defaultValue = "") String categoryName,
      @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
      @RequestParam(name = "location", required = false, defaultValue = "") String locationString,
      @RequestParam(name = "minPrice", required = false, defaultValue = "0") BigDecimal minPrice,
      @RequestParam(name = "maxPrice", required = false, defaultValue = "100000000")
      BigDecimal maxPrice) {
    Sort sort = Sort.by(sortField);
    sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
    Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, size, sort);
    return new ResponseObject<>(
        HttpStatus.OK, "", this.productService.filterProduct(
        keyword, categoryName, locationString, minPrice, maxPrice, pageable));
  }

  @GetMapping("/categoryId/{categoryId}")
  public ResponseObject<Page<ProductResponseDTO>> getAllProductByCategory(
      @PathVariable(name = "categoryId") Integer categoryId,
      @RequestParam(name = "page", required = false, defaultValue = DEFAULT_PAGE) Integer page,
      @RequestParam(name = "limit", required = false, defaultValue = PRODUCT_PER_PAGE) Integer size,
      @RequestParam(name = "location", required = false, defaultValue = "") String locationString,
      @RequestParam(name = "sortField", required = false, defaultValue = "id") String sortField,
      @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir,
      @RequestParam(name = "sortOption", required = false, defaultValue = "0") int sortOption,
      @RequestParam(name = "minPrice", required = false, defaultValue = "0") BigDecimal minPrice,
      @RequestParam(name = "maxPrice", required = false, defaultValue = "100000000") BigDecimal maxPrice) {
    if (sortField.equals("price")) {
      sortField = "listPrice";
    }
    Sort sort = Sort.by(sortField);
    sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
    Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, size, sort);
    return new ResponseObject<>(
        HttpStatus.OK, "",
        this.productService.getAllProductCategoryId(categoryId, locationString, sortOption, minPrice,
            maxPrice, pageable));//.toList();
  }

  @GetMapping("/option/{optionId}")
  public ResponseObject<Page<ProductGalleryDTO>> getAllProductByOption(
      @PathVariable(name = "optionId") int homeOptionId,
      @RequestParam(name = "page", required = false, defaultValue = DEFAULT_PAGE) Integer page,
      @RequestParam(name = "limit", required = false, defaultValue = DEFAULT_SIZE) Integer size,
      @RequestParam(name = "location", required = false, defaultValue = "") String locationString,
      @RequestParam(name = "sortField", required = false, defaultValue = "") String sortField,
      @RequestParam(name = "sortDir", required = false, defaultValue = "") String sortDir,
      @RequestParam(name = "sortOption", required = false, defaultValue = "0") int sortOption,
      @RequestParam(name = "minPrice", required = false, defaultValue = "0") BigDecimal minPrice,
      @RequestParam(name = "maxPrice", required = false, defaultValue = "100000000") BigDecimal maxPrice) {
    Sort sort = null;
    if (!sortField.equals("")) {
      if (sortField.equals("price")) {
        sortField = "listPrice";
      }
      sort = Sort.by(sortField);
      sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
      if (homeOptionId == EHomeOption.NEW.ordinal()) {
        sort = sort.and(Sort.by("createdAt").descending());
      }
    } else {
      if (homeOptionId == EHomeOption.NEW.ordinal()) {
        sort = Sort.by("createdAt").descending();
      }
    }
    Pageable pageable = null;
    if (sort != null) {
      pageable = PageRequest.of(page > 0 ? page - 1 : 0, size, sort);
    } else {
      pageable = PageRequest.of(page > 0 ? page - 1 : 0, size);
    }
    return new ResponseObject<>(
        HttpStatus.OK, "",
        this.productService.getAllProductByHomeOption(homeOptionId, locationString, sortOption, minPrice,
            maxPrice, pageable));//.toList();
  }

  @GetMapping("/search")
  public ResponseObject<Page<ProductGalleryDTO>> getAllProductByKeyword(
      @RequestParam(name = "page", required = false, defaultValue = DEFAULT_PAGE) Integer page,
      @RequestParam(name = "limit", required = false, defaultValue = PRODUCT_PER_PAGE) Integer size,
      @RequestParam(name = "sortField", required = false, defaultValue = "id") String sortField,
      @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir,
      @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
      @RequestParam(name = "location", required = false, defaultValue = "") String locationString,
      @RequestParam(name = "sortOption", required = false, defaultValue = "0") int sortOption,
      @RequestParam(name = "minPrice", required = false, defaultValue = "0") BigDecimal minPrice,
      @RequestParam(name = "maxPrice", required = false, defaultValue = "100000000") BigDecimal maxPrice) {
    if (sortField.equals("price")) {
      sortField = "list_price";
    }
    Sort sort = Sort.by(sortField);    sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
    Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, size, sort);
    return new ResponseObject<>(
        HttpStatus.OK, "",
        this.productService.filterProductByKeyword(keyword, locationString, sortOption, minPrice, maxPrice, pageable));
  }

  @GetMapping("/trading")
  public ResponseObject<List<ProductGalleryDTO>> getAllProductOnTradingByPage(
      @RequestParam(name = "page", required = false, defaultValue = DEFAULT_PAGE) Integer page,
      @RequestParam(name = "limit", required = false, defaultValue = PRODUCT_PER_PAGE) Integer size,
      @RequestParam(name = "sortField", required = false, defaultValue = "id") String sortField,
      @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir) {
    Sort sort = Sort.by(sortField);
    sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
    Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, size, sort);
    return new ResponseObject<>(
        HttpStatus.OK, "", this.productService.getAllProductOnTradingByPage(pageable).toList());
  }

  @GetMapping("/{id}")
  public ResponseObject<ProductResponseDTO> getProductById(@PathVariable(name = "id") Long id) {
    return new ResponseObject<>(
        HttpStatus.OK, "", this.productService.getProductById(id));
  }

  @GetMapping("/slug/{slug}")
  public ResponseObject<ProductResponseDTO> getProductById(@PathVariable(name = "slug") String slug) {
    return new ResponseObject<>(
        HttpStatus.OK, "", this.productService.getProductBySlug(slug));
  }

  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseObject<ProductResponseDTO> createProduct(
      @RequestPart("data") @Valid ProductCreationDTO creationDTO,
      @RequestPart(value = "image", required = false) MultipartFile imageFile,
      @RequestPart(value = "images", required = false) MultipartFile[] imageFiles) {
    return new ResponseObject<>(HttpStatus.CREATED, String.format(Utils.CREATE_OBJECT_SUCCESSFULLY, branchName),
        this.productService.createProduct(creationDTO, imageFile, imageFiles));
  }

  @PutMapping("/{id}")
  public ResponseObject<ProductResponseDTO> updateProduct(
      @PathVariable(name = "id") Long id,
      @RequestPart("data") @Valid ProductCreationDTO creationDTO,
      @RequestPart(value = "image", required = false) MultipartFile imageFile,
      @RequestPart(value = "images", required = false) MultipartFile[] imageFiles) {
    return new ResponseObject<>(HttpStatus.OK, String.format(Utils.UPDATE_OBJECT_SUCCESSFULLY, branchName),
        this.productService.updateProduct(id, creationDTO, imageFile, imageFiles));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseObject<ProductResponseDTO> deleteProductById(@PathVariable(name = "id") Long id) {
    return new ResponseObject<>(HttpStatus.OK, String.format(Utils.DELETE_OBJECT_SUCCESSFULLY, branchName),
        this.productService.deleteProductById(id));
  }
}
