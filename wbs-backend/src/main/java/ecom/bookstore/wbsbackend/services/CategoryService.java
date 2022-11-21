package ecom.bookstore.wbsbackend.services;

import ecom.bookstore.wbsbackend.dto.request.CategoryCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.CategoryResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author minh phuong
 * @created 11/09/2022 - 10:01 AM
 */
public interface CategoryService {
  List<CategoryResponseDTO> getAllCategories();

  List<CategoryResponseDTO> getAllCategoriesWithHierarchy();

  CategoryResponseDTO getCategoryById(Integer id);

  CategoryResponseDTO getCategoryBySlug(String slug);

  CategoryResponseDTO createCategory(CategoryCreationDTO creationDTO, MultipartFile imageFile);

  CategoryResponseDTO updateCategory(Integer id, CategoryCreationDTO creationDTO, MultipartFile imageFile);

  CategoryResponseDTO deleteCategoryById(Integer id);
}
