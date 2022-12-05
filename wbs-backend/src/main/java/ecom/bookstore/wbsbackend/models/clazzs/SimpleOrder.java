package ecom.bookstore.wbsbackend.models.clazzs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author minh phuong
 * @created 03/12/2022 - 4:25 PM
 */
@Data
@ToString
@AllArgsConstructor
public class SimpleOrder {
  private Long orderId;
  private BigDecimal totalPriceProduct;
  private BigDecimal totalFee;
  private BigDecimal totalDiscount;
  private BigDecimal totalPrice;
}
