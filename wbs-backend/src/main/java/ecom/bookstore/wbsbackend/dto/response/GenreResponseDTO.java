package ecom.bookstore.wbsbackend.dto.response;

import ecom.bookstore.wbsbackend.entities.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author minh phuong
 * @created 08/11/2022 - 4:41 PM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenreResponseDTO {
  private Integer id;
  private String name;
  private String slug;
  private String description;
  private String thumbnail;
}
