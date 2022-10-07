package ecom.bookstore.wbsbackend.mapper;

import ecom.bookstore.wbsbackend.dto.response.CategoryResponseDTO;
import ecom.bookstore.wbsbackend.entities.Category;

/**
 * @author minh phuong
 * @created 11/09/2022 - 12:37 PM
 * @project gt-backend
 */
public interface CategoryMapper {
  CategoryResponseDTO categoryToCategoryResponseDTO(Category entity);
}
