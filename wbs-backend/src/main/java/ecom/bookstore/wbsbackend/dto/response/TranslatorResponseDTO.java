package ecom.bookstore.wbsbackend.dto.response;

import ecom.bookstore.wbsbackend.models.enums.EGender;
import lombok.Data;

/**
 * @author minh phuong
 * @created 15/11/2022 - 5:48 PM
 */
@Data
public class TranslatorResponseDTO {
  private Integer id;
  private String fullName;
  private int gender;
}
