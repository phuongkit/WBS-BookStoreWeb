package ecom.bookstore.wbsbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author minh phuong
 * @created 20/09/2022 - 9:27 PM
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponseDTO {
  private Long id;
  private ProductGalleryDTO product;
  private Double sale;
  private Integer quantity;
  private BigDecimal totalPrice;
  private String note;
}
