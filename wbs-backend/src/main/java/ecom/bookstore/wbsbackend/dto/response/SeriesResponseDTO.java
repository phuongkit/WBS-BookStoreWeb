package ecom.bookstore.wbsbackend.dto.response;

import ecom.bookstore.wbsbackend.entities.Publisher;
import ecom.bookstore.wbsbackend.entities.Supplier;
import ecom.bookstore.wbsbackend.models.enums.ESeries;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author minh phuong
 * @created 08/11/2022 - 4:40 PM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeriesResponseDTO {
  private Integer id;
  private String name;
  private String slug;
  private String description;
  private String supplier;
  private String publisher;
  private String[] authors;
  private ESeries type;
}
