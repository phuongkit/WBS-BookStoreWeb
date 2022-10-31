package ecom.bookstore.wbsbackend.services;

import ecom.bookstore.wbsbackend.dto.request.ProductCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.ProductGalleryDTO;
import ecom.bookstore.wbsbackend.dto.response.ProductResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

/**
 * @author minh phuong
 * @created 11/10/2022 - 9:45 PM
 */
public interface ProductService {
  Page<ProductResponseDTO> getAllProduct(Pageable pageable);

  Page<ProductGalleryDTO> getAllProductOnTradingByPage(Pageable pageable);

  Page<ProductResponseDTO> filterProduct(
      String keyword,
      String categoryName,
      String locationString,
      BigDecimal minPrice,
      BigDecimal maxPrice,
      Pageable pageable);

  Page<ProductGalleryDTO> filterProductByKeyword(
      String keyword,
      String locationString,
      int sortOption,
      BigDecimal minPrice,
      BigDecimal maxPrice,
      Pageable pageable);

  Page<ProductResponseDTO> getAllProductCategoryId(
      Integer categoryId,
      String locationString,
      int sortOption,
      BigDecimal minPrice,
      BigDecimal maxPrice,
      Pageable pageable);

  Page<ProductGalleryDTO> getAllProductByHomeOption(
      int homeOptionId,
      String locationString,
      int sortOption,
      BigDecimal minPrice,
      BigDecimal maxPrice,
      Pageable pageable);

  ProductResponseDTO getProductById(Long id);

  ProductResponseDTO getProductBySlug(String slug);

  ProductResponseDTO createProduct(
      ProductCreationDTO creationDTO,
      MultipartFile thumbnailFile,
      MultipartFile[] imageGalleryFile);

  ProductResponseDTO updateProduct(
      Long id,
      ProductCreationDTO creationDTO,
      MultipartFile thumbnailFile,
      MultipartFile[] imageGalleryFile);

  ProductResponseDTO deleteProductById(Long id);
}
