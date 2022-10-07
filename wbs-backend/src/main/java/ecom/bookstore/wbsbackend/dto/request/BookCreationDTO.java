package ecom.bookstore.wbsbackend.dto.request;

import ecom.bookstore.wbsbackend.models.enums.EBookStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author minh phuong
 * @created 11/09/2022 - 10:21 AM
 * @project gt-backend
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookCreationDTO {
  private String name;
  private String description;
  private BigDecimal standCost;
  private BigDecimal listPrice;
  private Integer quantity;
  private EBookStatus status;
  private Integer categoryId;
  private String location;
}
