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
 */
@Component
public class CategoryMapperImpl implements CategoryMapper {
  @Override
  public CategoryResponseDTO categoryToCategoryResponseDTO(Category entity, boolean... isFull) {
    if (entity == null) {
      return null;
    }
    CategoryResponseDTO responseDTO = new CategoryResponseDTO();
    responseDTO.setId(entity.getId());
    responseDTO.setName(entity.getName());
    responseDTO.setSlug(entity.getSlug());
    responseDTO.setDescription(entity.getDescription());
    if (isFull.length > 0 && isFull[0]) {
      if (entity.getCategories() != null && entity.getCategories().size() > 0) {
        CategoryResponseDTO[] categoryChildren =
            new CategoryResponseDTO[entity.getCategories().size()];
        int i = 0;
        for (Category category : entity.getCategories()) {
          categoryChildren[i] = new CategoryResponseDTO();
          categoryChildren[i] = this.categoryToCategoryResponseDTO(category);
          i++;
        }
        responseDTO.setChildCategories(categoryChildren);
      }
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
