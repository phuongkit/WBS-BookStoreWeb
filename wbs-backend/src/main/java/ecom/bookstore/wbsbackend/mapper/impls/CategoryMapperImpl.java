package ecom.bookstore.wbsbackend.mapper.impls;

import ecom.bookstore.wbsbackend.dto.response.CategoryResponseDTO;
import ecom.bookstore.wbsbackend.entities.Category;
import ecom.bookstore.wbsbackend.mapper.CategoryMapper;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.springframework.stereotype.Component;

import static ecom.bookstore.wbsbackend.utils.Utils.IMAGE_DEFAULT_PATH;

/**
 * @author minh phuong
 * @created 11/09/2022 - 12:38 PM
 * @project gt-backend
 */
@Component
public class CategoryMapperImpl implements CategoryMapper {
  @Override
  public CategoryResponseDTO categoryToCategoryResponseDTO(Category entity) {
    if (entity == null) {
      return null;
    }
    CategoryResponseDTO responseDTO = new CategoryResponseDTO();
    responseDTO.setId(entity.getId());
    responseDTO.setTitle(entity.getName());
    responseDTO.setHref(entity.getSlug());
    responseDTO.setDescription(entity.getDescription());
    if (entity.getParentCategory() != null) {
      responseDTO.setParentCategoryId(entity.getParentCategory().getId());
    }
    if (entity.getThumbnail() != null) {
      responseDTO.setImg(Utils.getUrlFromPathImage(entity.getThumbnail().getPath()));
    } else {
      responseDTO.setImg(Utils.getUrlFromPathImage(IMAGE_DEFAULT_PATH));
    }
    if (entity.getIcon() != null) {
      responseDTO.setIcon(Utils.getUrlFromPathImage(entity.getIcon().getPath()));
    } else {
      responseDTO.setIcon(Utils.getUrlFromPathImage(IMAGE_DEFAULT_PATH));
    }
    return responseDTO;
  }
}
