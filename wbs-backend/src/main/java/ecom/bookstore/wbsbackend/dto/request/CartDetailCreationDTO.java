package ecom.bookstore.wbsbackend.dto.request;

import lombok.Data;

/**
 * @author minh phuong
 * @created 19/09/2022 - 5:00 PM
 * @project gt-backend
 */
@Data
public class CartDetailCreationDTO {
  private Long productId;
  private Integer quantity;
}
