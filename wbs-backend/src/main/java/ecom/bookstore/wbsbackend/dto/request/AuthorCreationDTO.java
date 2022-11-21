package ecom.bookstore.wbsbackend.dto.request;

import ecom.bookstore.wbsbackend.models.enums.EGender;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author minh phuong
 * @created 07/10/2022 - 9:45 PM
 */
@Data
@ToString
public class AuthorCreationDTO {
  private String fullName;
  private EGender gender;
}
