package ecom.bookstore.wbsbackend.dto.request;

import ecom.bookstore.wbsbackend.entities.User;
import ecom.bookstore.wbsbackend.models.enums.EGender;
import ecom.bookstore.wbsbackend.models.enums.EOrderStatus;
import ecom.bookstore.wbsbackend.models.enums.EPayment;
import ecom.bookstore.wbsbackend.models.enums.EShippingMethod;
import ecom.bookstore.wbsbackend.utils.Utils;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author minh phuong
 * @created 20/09/2022 - 9:13 PM
 * @project gt-backend
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderCreationDTO {
  private Integer userId;

//  @NotBlank(message = "Gender not blank!")
//  @NotNull(message = "Gender not null!")
  private EGender gender;

  @NotBlank(message = "FullName not blank!")
  @NotNull(message = "FullNane not null!")
  private String fullName;

  @NotBlank(message = "Phone not blank!")
//  @NotBlank(message = "Email not blank!")
  private String email;

  @NotBlank(message = "Phone not blank!")
//  @NotNull(message = "Phone not null!")
  private String phone;

//  @NotBlank(message = "Address not blank!")
  @NotNull(message = "Address not null!")
  private AddressCreationDTO address;

  private EPayment payment;
  private EShippingMethod shippingMethod;
  private BigDecimal transportFee;
  private String discountCode;
  private String note;
  private EOrderStatus status;

//  @NotBlank(message = "OrderItems not blank!")
  @NotNull(message = "OrderItems not null!")
  private List<OrderDetailCreationDTO> orderItems;

  public OrderCreationDTO(User user, List<OrderDetailCreationDTO> orderItems) {
    this.userId = user.getId();
    this.gender = user.getGender();
    this.fullName = Utils.getFullNameFromLastNameAndFirstName(user.getLastName(), user.getFirstName());
    this.email = user.getEmail();
    this.phone = user.getPhone();
    this.address = new AddressCreationDTO(user.getAddresses());
    this.payment = EPayment.CASH;
    this.shippingMethod = EShippingMethod.GHN_EXPRESS;
    this.status = EOrderStatus.ORDER_PENDING;
    this.orderItems = orderItems;
  }
}
