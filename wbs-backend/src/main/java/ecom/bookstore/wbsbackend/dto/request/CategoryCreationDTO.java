package ecom.bookstore.wbsbackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author minh phuong
 * @created 11/09/2022 - 10:19 AM
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreationDTO {
  @NotNull(message = "An name is required!")
  private String name;
  private String description;
  private Integer parentCategoryId;
}
