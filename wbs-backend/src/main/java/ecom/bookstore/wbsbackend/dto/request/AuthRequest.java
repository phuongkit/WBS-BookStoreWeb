package ecom.bookstore.wbsbackend.dto.request;

import ecom.bookstore.wbsbackend.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author minh phuong
 * @created 08/09/2022 - 8:04 PM
 * @project gt-backend
 */
@Getter
@Setter
@AllArgsConstructor
@Data
public class AuthRequest {
  @Size(message = "Invalid phone size.", max = 13, min = 9)
//  @NotNull(message="An phone is required!")
  @Pattern(regexp = (Utils.REGEX_PHONE), message = "Invalid phone")
  private String phone;

  @Size(message = "Invalid email size.", max = 320, min = 10)
  @NotNull(message = "An email is required!")
  @Pattern(regexp = (Utils.REGEX_EMAIL), message = "Invalid email")
  private String email;

  @Length(message="Invalid password size.", min = 4, max = 32)
  @NotNull( message="An password is required!")
  private String password;
}
