package ecom.bookstore.wbsbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author minh phuong
 * @created 11/09/2022 - 10:17 AM
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO {
  private Integer id;
  private String name;
  private String slug;
  private String description;
  private CategoryResponseDTO[] childCategories;
  private String img;
  private String icon;
}
