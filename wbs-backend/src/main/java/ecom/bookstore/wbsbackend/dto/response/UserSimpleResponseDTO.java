package ecom.bookstore.wbsbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author minh phuong
 * @created 12/09/2022 - 8:09 PM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSimpleResponseDTO {
  private Integer id;
  private String fullName;
  private String username;
  private String email;
  private String phone;
  private String avatar;
  private boolean isAdmin;
}
