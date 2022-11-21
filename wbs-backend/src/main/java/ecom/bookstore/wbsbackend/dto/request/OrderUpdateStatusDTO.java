package ecom.bookstore.wbsbackend.dto.request;

import lombok.Data;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author minh phuong
 * @created 21/11/2022 - 10:17 PM
 */
@Data
@ToString
public class OrderUpdateStatusDTO {
  private String status;
  private String log;
  private String shipOrderCode;
  private Date expectedDeliveryTime;
}
