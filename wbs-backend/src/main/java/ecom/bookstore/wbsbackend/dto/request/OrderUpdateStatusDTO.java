package ecom.bookstore.wbsbackend.dto.request;

import ecom.bookstore.wbsbackend.models.enums.EOrderStatus;
import lombok.Data;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author minh phuong
 * @created 21/11/2022 - 10:17 PM
 */
@Data
@ToString
public class OrderUpdateStatusDTO {
  private EOrderStatus status;
  private String log;
  private String shipOrderCode;
  private Date expectedDeliveryTime;
  private BigDecimal transportFee;
}
