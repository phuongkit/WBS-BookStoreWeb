package ecom.bookstore.wbsbackend.dto.request;

import ecom.bookstore.wbsbackend.models.enums.EPayment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author minh phuong
 * @created 13/10/2022 - 4:38 PM
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdatePaymentDTO {
  private EPayment payment;
  private boolean paid;
  private String status;
  private Date expectedDeliveryTime;
  private BigDecimal transportFee;
}
