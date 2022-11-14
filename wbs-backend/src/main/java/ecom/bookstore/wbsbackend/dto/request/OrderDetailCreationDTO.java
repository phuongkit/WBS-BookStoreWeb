package ecom.bookstore.wbsbackend.dto.request;

import ecom.bookstore.wbsbackend.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author minh phuong
 * @created 20/09/2022 - 9:25 PM
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailCreationDTO {
  private Long productId;
  private Integer quantity;
  private String saleName;
  private String note;
  public OrderDetailCreationDTO (Product product, Integer quantity) {
    this.productId = product.getId();
    this.quantity = quantity;
  }
}
