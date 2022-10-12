package ecom.bookstore.wbsbackend.services;

import ecom.bookstore.wbsbackend.dto.request.CategoryCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.CategoryResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author minh phuong
 * @created 11/09/2022 - 10:01 AM
 */
public interface CategoryService {
  Page<CategoryResponseDTO> getAllCategories(String keyword, Pageable pageable);

  CategoryResponseDTO getCategoryById(Integer id);

  CategoryResponseDTO getCategoryBySlug(String slug);

  ResponseEntity<ResponseObject> createCategory(CategoryCreationDTO creationDTO, MultipartFile imageFile);

  ResponseEntity<ResponseObject> updateCategory(Integer id, CategoryCreationDTO creationDTO, MultipartFile imageFile);

  ResponseEntity<ResponseObject> deleteCategoryById(Integer id);
}
