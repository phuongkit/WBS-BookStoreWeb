package ecom.bookstore.wbsbackend.dto.request;

import lombok.Data;
import lombok.ToString;

import java.math.BigInteger;

/**
 * @author minh phuong
 * @created 20/11/2022 - 7:03 PM
 */
@Data
@ToString
public class VNPayCreationDTO {
  Long orderId;
  String fullName;
  String redirectUrl;
  BigInteger totalPrice;
  String locate;
  String bankCode;
}
