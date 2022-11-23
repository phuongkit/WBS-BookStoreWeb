package ecom.bookstore.wbsbackend.dto.request;

import ecom.bookstore.wbsbackend.models.enums.EProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author minh phuong
 * @created 11/09/2022 - 10:21 AM
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreationDTO {
  private String name;
  private String description;
  private BigDecimal standCost;
  private BigDecimal listPrice;
  private Integer quantity;
  private EProductStatus status;
  private Integer[] authors;
  private Integer categoryId;
  private Integer publisherId;
  private Integer supplierId;
  private Integer[] translators;
  private String location;
}
