package ecom.bookstore.wbsbackend.dto.response;

import ecom.bookstore.wbsbackend.models.enums.EGender;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

/**
 * @author minh phuong
 * @created 15/11/2022 - 5:46 PM
 */
@Data
public class AuthorResponseDTO {
  private Integer id;
  private String fullName;
  private int gender;
}
