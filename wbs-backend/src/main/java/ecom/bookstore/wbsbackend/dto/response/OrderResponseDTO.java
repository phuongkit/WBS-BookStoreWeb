package ecom.bookstore.wbsbackend.dto.response;

import ecom.bookstore.wbsbackend.models.enums.EOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author minh phuong
 * @created 20/09/2022 - 9:27 PM
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
  private Long id;
  private UserSimpleResponseDTO user;
  private int gender;
  private String fullName;
  private String email;
  private String phone;
  private AddressResponseDTO address;
  private BigDecimal totalPrice;
  private int payment;
  private int shippingMethod;
  private BigDecimal transportFee;
  private DiscountResponseDTO discount;
  private String status;
  private Date payAt;
  private String note;
  private OrderDetailResponseDTO[] orderItems;
  private String log;
  private String shipOrderCode;
  private Date expectedDeliveryTime;
  private Date createdAt;
  private Date updatedAt;
}
