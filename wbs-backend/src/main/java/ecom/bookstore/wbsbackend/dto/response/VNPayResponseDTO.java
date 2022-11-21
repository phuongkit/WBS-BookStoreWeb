package ecom.bookstore.wbsbackend.dto.response;

import lombok.Data;

import java.util.Date;

/**
 * @author minh phuong
 * @created 20/11/2022 - 8:22 PM
 */
@Data
public class VNPayResponseDTO {
  String payUrl;
  Date createDate;
  Date expireDate;
}
